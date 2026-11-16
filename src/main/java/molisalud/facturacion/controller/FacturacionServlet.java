package molisalud.facturacion.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.facturacion.beans.TarifarioBean;
import molisalud.login.beans.UsuarioBean;

/**
 * Controlador HTTP de Facturación (ruta /facturacion). Solo el Cajero factura, según el modelo.
 * La pantalla usa facturarAtencion (pago y comprobante en una transacción).
 */
@WebServlet("/facturacion")
public class FacturacionServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public FacturacionServlet() {
    }

    private molisalud.facturacion.service.IFacturacionService service = new molisalud.facturacion.service.FacturacionService();

    private static final String CAJERO = "Cajero";

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
        if (!CAJERO.equals(u.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo el Cajero puede facturar.");
            return;
        }
        HttpSession sesion = request.getSession();
        for (String clave : new String[] {"mensaje", "error"}) {
            request.setAttribute(clave, sesion.getAttribute(clave));
            sesion.removeAttribute(clave);
        }
        try {
            request.setAttribute("tarifario", service.getTarifario());
        } catch (RuntimeException e) {
            log("Error al consultar el tarifario", e);
            request.setAttribute("error", "No se pudo consultar el tarifario. Intente de nuevo.");
            request.setAttribute("tarifario", new ArrayList<TarifarioBean>());
        }
        request.getRequestDispatcher("/facturacion.jsp").forward(request, response);
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
        try {
            if (!CAJERO.equals(u.getRol())) {
                sesion.setAttribute("error", "Solo el Cajero puede facturar.");
            } else if ("facturar".equals(request.getParameter("accion"))) {
                int idCita = entero(request.getParameter("idCita"));
                int idServicio = entero(request.getParameter("idServicio"));
                service.facturarAtencion(idCita, idServicio, request.getParameter("metodoPago"));
                String detalle = "";
                for (TarifarioBean t : service.getTarifario()) {
                    if (t.getIdServicio() == idServicio) {
                        detalle = " Servicio: " + t.getNombreServicio() + " - Total cobrado: S/ " + t.getPrecio() + ".";
                    }
                }
                sesion.setAttribute("mensaje", "Atención de la cita Nº " + idCita
                        + " facturada: pago registrado y comprobante emitido." + detalle);
            } else {
                sesion.setAttribute("error", "Acción no reconocida.");
            }
        } catch (NumberFormatException e) {
            sesion.setAttribute("error", "Datos inválidos: revise los números ingresados.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            sesion.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error al facturar", e);
            sesion.setAttribute("error", "No se pudo completar la operación. Intente de nuevo.");
        }
        response.sendRedirect(request.getContextPath() + "/facturacion");
    }

    private static int entero(String s) {
        return s == null || s.trim().isEmpty() ? 0 : Integer.parseInt(s.trim());
    }
}
