package molisalud.historiaclinica.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.historiaclinica.beans.ConsultaBean;
import molisalud.historiaclinica.beans.DiagnosticoBean;
import molisalud.historiaclinica.beans.HistoriaClinicaBean;
import molisalud.historiaclinica.beans.RecetaBean;
import molisalud.historiaclinica.beans.TriajeBean;
import molisalud.login.beans.UsuarioBean;
import molisalud.reserva.beans.PacienteBean;

/**
 * Controlador HTTP de Historia clínica (ruta /historiaclinica). Valida sesión y rol antes de delegar al servicio.
 * Permisos:
 *  - Ver historia y triajes: Médico, Enfermero/a (la Recepcionista solo ve si existe la historia)
 *  - Crear historia clínica: Recepcionista, Médico
 *  - Registrar triaje: Enfermero/a
 *  - Registrar consulta, diagnóstico y emitir receta: Médico
 * El paciente se busca por DNI con el servicio de Reserva (consultarPaciente).
 */
@WebServlet("/historiaclinica")
public class HistoriaClinicaServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public HistoriaClinicaServlet() {
    }

    private molisalud.historiaclinica.service.IHistoriaClinicaService service =
            new molisalud.historiaclinica.service.HistoriaClinicaService();

    private molisalud.reserva.service.IReservaService pacientes = new molisalud.reserva.service.ReservaService();

    private static final String MEDICO = "Médico";
    private static final String ENFERMERO = "Enfermero/a";
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
        if (!esRol(u, MEDICO, ENFERMERO, RECEPCIONISTA)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Su rol no tiene acceso a la historia clínica.");
            return;
        }
        HttpSession sesion = request.getSession();
        for (String clave : new String[] {"mensaje", "error"}) {
            request.setAttribute(clave, sesion.getAttribute(clave));
            sesion.removeAttribute(clave);
        }
        String dni = request.getParameter("dni");
        if (dni != null && !dni.trim().isEmpty()) {
            request.setAttribute("dniBuscado", dni.trim());
            try {
                PacienteBean p = pacientes.consultarPaciente(dni);
                request.setAttribute("pacienteBuscado", Boolean.TRUE);
                request.setAttribute("paciente", p); // null si no existe
                if (p != null) {
                    HistoriaClinicaBean h = service.getHistoria(p.getIdPaciente());
                    request.setAttribute("historia", h); // null si todavía no tiene
                    if (h != null && esRol(u, MEDICO, ENFERMERO)) {
                        request.setAttribute("triajes", service.getTriajes(h.getIdHistoria()));
                    }
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
            } catch (RuntimeException e) {
                log("Error al consultar la historia clínica", e);
                request.setAttribute("error", "No se pudo consultar la historia clínica. Intente de nuevo.");
            }
        }
        request.getRequestDispatcher("/historiaclinica.jsp").forward(request, response);
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
        String volverADni = request.getParameter("dni");
        try {
            if ("crearHistoria".equals(accion) && esRol(u, RECEPCIONISTA, MEDICO)) {
                HistoriaClinicaBean h = new HistoriaClinicaBean();
                h.setIdPaciente(entero(request.getParameter("idPaciente")));
                h.setAntecedentes(request.getParameter("antecedentes"));
                service.agregarHistoria(h);
                sesion.setAttribute("mensaje", "Historia clínica Nº " + h.getIdHistoria() + " creada.");
            } else if ("registrarTriaje".equals(accion) && esRol(u, ENFERMERO)) {
                TriajeBean t = new TriajeBean();
                t.setIdCita(entero(request.getParameter("idCita")));
                t.setTemperatura(decimal(request.getParameter("temperatura")));
                t.setPeso(decimal(request.getParameter("peso")));
                t.setTalla(decimal(request.getParameter("talla")));
                t.setPresionArterial(request.getParameter("presionArterial"));
                service.registrarTriaje(t);
                sesion.setAttribute("mensaje", "Triaje Nº " + t.getIdTriaje() + " registrado.");
            } else if ("registrarConsulta".equals(accion) && esRol(u, MEDICO)) {
                ConsultaBean c = new ConsultaBean();
                c.setIdCita(entero(request.getParameter("idCita")));
                c.setIdMedico(entero(request.getParameter("idMedico")));
                c.setMotivoConsulta(request.getParameter("motivoConsulta"));
                c.setObservaciones(request.getParameter("observaciones"));
                service.registrarConsulta(c);
                sesion.setAttribute("mensaje", "Consulta Nº " + c.getIdConsulta()
                        + " registrada y cita marcada como Atendida. Use ese número para el diagnóstico y la receta.");
            } else if ("registrarDiagnostico".equals(accion) && esRol(u, MEDICO)) {
                DiagnosticoBean d = new DiagnosticoBean();
                d.setIdConsulta(entero(request.getParameter("idConsulta")));
                d.setDescripcion(request.getParameter("descripcion"));
                service.registrarDiagnostico(d);
                sesion.setAttribute("mensaje", "Diagnóstico Nº " + d.getIdDiagnostico() + " registrado.");
            } else if ("emitirReceta".equals(accion) && esRol(u, MEDICO)) {
                RecetaBean r = new RecetaBean();
                r.setIdConsulta(entero(request.getParameter("idConsulta")));
                r.setIndicaciones(request.getParameter("indicaciones"));
                service.emitirReceta(r);
                sesion.setAttribute("mensaje", "Receta Nº " + r.getIdReceta() + " emitida.");
            } else {
                sesion.setAttribute("error", "Su rol no puede realizar esta acción.");
            }
        } catch (NumberFormatException e) {
            sesion.setAttribute("error", "Datos inválidos: revise los números ingresados.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            sesion.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error en la operación de historia clínica", e);
            sesion.setAttribute("error", "No se pudo completar la operación. Intente de nuevo.");
        }
        String destino = request.getContextPath() + "/historiaclinica";
        if (volverADni != null && !volverADni.trim().isEmpty()) {
            destino += "?dni=" + URLEncoder.encode(volverADni.trim(), "UTF-8");
        }
        response.sendRedirect(destino);
    }

    private static int entero(String s) {
        return s == null || s.trim().isEmpty() ? 0 : Integer.parseInt(s.trim());
    }

    private static BigDecimal decimal(String s) {
        return s == null || s.trim().isEmpty() ? null : new BigDecimal(s.trim().replace(',', '.'));
    }
}
