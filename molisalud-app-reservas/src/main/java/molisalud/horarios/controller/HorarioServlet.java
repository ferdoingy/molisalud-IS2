package molisalud.horarios.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.horarios.beans.HorarioBean;
import molisalud.login.beans.UsuarioBean;

/**
 * Controlador HTTP de Horarios (ruta /horarios). Valida sesión y rol antes de delegar al servicio.
 * Permisos según los casos de uso del modelo:
 *  - Consultar horarios: Recepcionista, Médico, Administrador de la clínica
 *  - Registrar disponibilidad: Médico
 *  - Asignar turno: Recepcionista
 *  - Actualizar horario: Administrador de la clínica, Médico, Recepcionista
 *  - Publicar horarios: Administrador de la clínica
 */
@WebServlet("/horarios")
public class HorarioServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public HorarioServlet() {
    }

    private molisalud.horarios.service.IHorarioService service = new molisalud.horarios.service.HorarioService();

    private static final String ADMIN = "Administrador de la clínica";
    private static final String MEDICO = "Médico";
    private static final String RECEPCIONISTA = "Recepcionista";

    private static boolean esRol(UsuarioBean u, String... roles) {
        for (String r : roles) {
            if (r.equals(u.getRol())) {
                return true;
            }
        }
        return false;
    }

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
        if (!esRol(u, RECEPCIONISTA, MEDICO, ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Su rol no tiene acceso a los horarios.");
            return;
        }
        HttpSession sesion = request.getSession();
        for (String clave : new String[] {"mensaje", "error"}) { // mensajes de la última operación
            request.setAttribute(clave, sesion.getAttribute(clave));
            sesion.removeAttribute(clave);
        }
        try {
            String filtro = request.getParameter("idMedico");
            ArrayList<HorarioBean> lista;
            if (filtro != null && !filtro.trim().isEmpty()) {
                try {
                    lista = service.consultarDisponibilidad(Integer.parseInt(filtro.trim()));
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "El código de médico debe ser un número.");
                    lista = service.getHorarios();
                }
            } else {
                lista = service.getHorarios();
            }
            request.setAttribute("horarios", lista);

            String idEditar = request.getParameter("editar");
            if (idEditar != null && esRol(u, ADMIN, MEDICO, RECEPCIONISTA)) {
                for (HorarioBean h : service.getHorarios()) {
                    if (String.valueOf(h.getIdHorario()).equals(idEditar)) {
                        request.setAttribute("editar", h);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error al consultar horarios", e);
            request.setAttribute("error", "No se pudo consultar los horarios. Intente de nuevo.");
        }
        request.getRequestDispatcher("/horarios.jsp").forward(request, response);
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
        try {
            if ("registrar".equals(accion) && esRol(u, MEDICO)) {
                service.registrarDisponibilidad(leerHorario(request));
                sesion.setAttribute("mensaje", "Disponibilidad registrada (estado: Disponible).");
            } else if ("asignar".equals(accion) && esRol(u, RECEPCIONISTA)) {
                service.asignarTurno(leerHorario(request));
                sesion.setAttribute("mensaje", "Turno asignado (estado: Asignado).");
            } else if ("actualizar".equals(accion) && esRol(u, ADMIN, MEDICO, RECEPCIONISTA)) {
                service.actualizarHorario(leerHorario(request));
                sesion.setAttribute("mensaje", "Horario actualizado.");
            } else if ("publicar".equals(accion) && esRol(u, ADMIN)) {
                service.publicarHorario(leerHorario(request));
                sesion.setAttribute("mensaje", "Horario publicado (estado: Publicado).");
            } else {
                sesion.setAttribute("error", "Su rol no puede realizar esta acción.");
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            sesion.setAttribute("error", "Datos inválidos: revise el código de médico y las horas.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            sesion.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error en la operación de horarios", e);
            sesion.setAttribute("error", "No se pudo completar la operación. Intente de nuevo.");
        }
        response.sendRedirect(request.getContextPath() + "/horarios");
    }

    private HorarioBean leerHorario(HttpServletRequest request) {
        HorarioBean h = new HorarioBean();
        String id = request.getParameter("idHorario");
        h.setIdHorario(id == null || id.trim().isEmpty() ? 0 : Integer.parseInt(id.trim()));
        String medico = request.getParameter("idMedico");
        h.setIdMedico(medico == null || medico.trim().isEmpty() ? 0 : Integer.parseInt(medico.trim()));
        h.setDiaSemana(request.getParameter("diaSemana"));
        String inicio = request.getParameter("horaInicio");
        String fin = request.getParameter("horaFin");
        h.setHoraInicio(inicio == null || inicio.isEmpty() ? null : LocalTime.parse(inicio));
        h.setHoraFin(fin == null || fin.isEmpty() ? null : LocalTime.parse(fin));
        return h;
    }
}
