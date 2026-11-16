package molisalud.reserva.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.login.beans.UsuarioBean;
import molisalud.reserva.beans.PacienteBean;
import molisalud.reserva.beans.ReservaBean;
import molisalud.reserva.beans.SolicitudCitaBean;

/**
 * Controlador HTTP de Reserva (ruta /reservas). Los pacientes no inician sesión: la Recepcionista
 * realiza todas las operaciones del módulo en su nombre. Valida sesión y rol antes de delegar al servicio.
 */
@WebServlet("/reservas")
public class ReservaServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public ReservaServlet() {
    }

    private molisalud.reserva.service.IReservaService service = new molisalud.reserva.service.ReservaService();

    private static final String RECEPCIONISTA = "Recepcionista";

    private UsuarioBean usuarioEnSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession(false);
        UsuarioBean u = sesion == null ? null : (UsuarioBean) sesion.getAttribute("usuario");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return u;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioBean u = usuarioEnSesion(request, response);
        if (u == null) {
            return;
        }
        if (!RECEPCIONISTA.equals(u.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo la Recepcionista puede gestionar reservas.");
            return;
        }
        HttpSession sesion = request.getSession();
        for (String clave : new String[] {"mensaje", "error"}) {
            request.setAttribute(clave, sesion.getAttribute(clave));
            sesion.removeAttribute(clave);
        }

        // Búsqueda de paciente por DNI
        String dni = request.getParameter("dni");
        if (dni != null && !dni.trim().isEmpty()) {
            request.setAttribute("dniBuscado", dni.trim());
            try {
                PacienteBean p = service.consultarPaciente(dni);
                request.setAttribute("paciente", p); // null si no existe
                request.setAttribute("pacienteBuscado", Boolean.TRUE);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
            }
        }
        // Disponibilidad de un médico (horarios publicados)
        String medico = request.getParameter("idMedico");
        if (medico != null && !medico.trim().isEmpty()) {
            try {
                request.setAttribute("idMedicoConsultado", medico.trim());
                request.setAttribute("disponibilidad", service.consultarDisponibilidad(Integer.parseInt(medico.trim())));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El código de médico debe ser un número.");
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
            }
        }
        try {
            request.setAttribute("reservas", service.getReservas());
        } catch (RuntimeException e) {
            log("Error al listar reservas", e);
            request.setAttribute("error", "No se pudo consultar las reservas. Intente de nuevo.");
            request.setAttribute("reservas", new ArrayList<ReservaBean>());
        }
        request.getRequestDispatcher("/reservas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioBean u = usuarioEnSesion(request, response);
        if (u == null) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = request.getSession();
        String accion = request.getParameter("accion");
        String volverADni = null; // si la operación es sobre un paciente, volvemos a su ficha
        try {
            if (!RECEPCIONISTA.equals(u.getRol())) {
                sesion.setAttribute("error", "Solo la Recepcionista puede gestionar reservas.");
            } else if ("registrarPaciente".equals(accion)) {
                PacienteBean p = leerPaciente(request);
                service.registrarPaciente(p);
                volverADni = p.getDni();
                sesion.setAttribute("mensaje", "Paciente registrado (Nº " + p.getIdPaciente() + ").");
            } else if ("actualizarPaciente".equals(accion)) {
                PacienteBean p = leerPaciente(request);
                service.actualizarPaciente(p);
                volverADni = p.getDni();
                sesion.setAttribute("mensaje", "Ficha del paciente actualizada.");
            } else if ("registrarSolicitud".equals(accion)) {
                SolicitudCitaBean s = new SolicitudCitaBean();
                s.setIdPaciente(entero(request.getParameter("idPaciente")));
                s.setFechaEscogida(fecha(request.getParameter("fechaEscogida")));
                s.setHoraEscogida(hora(request.getParameter("horaEscogida")));
                s.setMotivoConsulta(request.getParameter("motivoConsulta"));
                service.registrarSolicitud(s);
                volverADni = request.getParameter("dni");
                sesion.setAttribute("mensaje", "Solicitud Nº " + s.getIdSolicitud()
                        + " registrada. Use ese número para registrar la reserva.");
            } else if ("registrarReserva".equals(accion)) {
                ReservaBean r = new ReservaBean();
                r.setIdHorario(entero(request.getParameter("idHorario")));
                r.setIdSolicitud(entero(request.getParameter("idSolicitud")));
                r.setFechaReserva(fecha(request.getParameter("fechaReserva")));
                r.setHora(hora(request.getParameter("hora")));
                service.registrarReserva(r);
                sesion.setAttribute("mensaje", "Reserva Nº " + r.getIdReserva() + " registrada (estado: Pendiente).");
            } else if ("confirmar".equals(accion)) {
                service.confirmarReserva(entero(request.getParameter("idReserva")));
                sesion.setAttribute("mensaje", "Reserva confirmada.");
            } else if ("cancelar".equals(accion)) {
                service.actualizarEstadoReserva(entero(request.getParameter("idReserva")), "Cancelada");
                sesion.setAttribute("mensaje", "Reserva cancelada.");
            } else {
                sesion.setAttribute("error", "Acción no reconocida.");
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            sesion.setAttribute("error", "Datos inválidos: revise los números, la fecha y la hora.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            sesion.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error en la operación de reservas", e);
            sesion.setAttribute("error", "No se pudo completar la operación. Intente de nuevo.");
        }
        String destino = request.getContextPath() + "/reservas";
        if (volverADni != null && !volverADni.trim().isEmpty()) {
            destino += "?dni=" + URLEncoder.encode(volverADni.trim(), "UTF-8");
        }
        response.sendRedirect(destino);
    }

    private static int entero(String s) {
        return s == null || s.trim().isEmpty() ? 0 : Integer.parseInt(s.trim());
    }

    private static LocalDate fecha(String s) {
        return s == null || s.trim().isEmpty() ? null : LocalDate.parse(s.trim());
    }

    private static LocalTime hora(String s) {
        return s == null || s.trim().isEmpty() ? null : LocalTime.parse(s.trim());
    }

    private PacienteBean leerPaciente(HttpServletRequest request) {
        PacienteBean p = new PacienteBean();
        p.setIdPaciente(entero(request.getParameter("idPaciente")));
        p.setDni(request.getParameter("dni"));
        p.setNombres(request.getParameter("nombres"));
        p.setApellidos(request.getParameter("apellidos"));
        p.setTelefono(request.getParameter("telefono"));
        p.setCorreo(request.getParameter("correo"));
        p.setDireccion(request.getParameter("direccion"));
        return p;
    }
}
