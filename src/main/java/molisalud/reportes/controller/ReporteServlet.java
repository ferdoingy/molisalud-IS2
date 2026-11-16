package molisalud.reportes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.facturacion.beans.FacturacionBean;
import molisalud.historiaclinica.beans.HistoriaClinicaBean;
import molisalud.login.beans.UsuarioBean;
import molisalud.reserva.beans.ReservaBean;

/**
 * Controlador HTTP de Reportes (ruta /reportes). Solo el Administrador de la clínica, según el modelo.
 *  - GET con fechaInicio y fechaFin: genera y muestra el reporte (accion=ver, por defecto).
 *  - GET con accion=exportar: descarga el reporte en CSV.
 */
@WebServlet("/reportes")
public class ReporteServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public ReporteServlet() {
    }

    private molisalud.reportes.service.IReporteService service = new molisalud.reportes.service.ReporteService();

    private static final String ADMIN = "Administrador de la clínica";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        UsuarioBean u = sesion == null ? null : (UsuarioBean) sesion.getAttribute("usuario");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!ADMIN.equals(u.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo el Administrador de la clínica puede ver los reportes.");
            return;
        }
        String ini = request.getParameter("fechaInicio");
        String fin = request.getParameter("fechaFin");
        if (ini == null || ini.trim().isEmpty() || fin == null || fin.trim().isEmpty()) {
            request.getRequestDispatcher("/reportes.jsp").forward(request, response); // formulario vacío
            return;
        }
        request.setAttribute("fechaInicio", ini.trim());
        request.setAttribute("fechaFin", fin.trim());
        try {
            LocalDate fechaInicio = LocalDate.parse(ini.trim());
            LocalDate fechaFin = LocalDate.parse(fin.trim());
            boolean exportar = "exportar".equals(request.getParameter("accion"));

            if (exportar) {
                ArrayList<Object> datos = service.consultarReporte(fechaInicio, fechaFin);
                service.exportarReporte(datos);
                String nombre = "reporte_" + fechaInicio + "_" + fechaFin + ".csv";
                response.setContentType("text/csv; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
                PrintWriter out = response.getWriter();
                out.print(construirCsv(fechaInicio, fechaFin, datos));
                out.flush();
                return;
            }

            service.generarReporte(fechaInicio, fechaFin); // valida y comprueba que haya datos
            ArrayList<Object> datos = service.consultarReporte(fechaInicio, fechaFin);
            ArrayList<ReservaBean> citas = new ArrayList<>();
            ArrayList<FacturacionBean> comprobantes = new ArrayList<>();
            ArrayList<HistoriaClinicaBean> historias = new ArrayList<>();
            BigDecimal ingresos = BigDecimal.ZERO;
            for (Object o : datos) {
                if (o instanceof ReservaBean) {
                    citas.add((ReservaBean) o);
                } else if (o instanceof FacturacionBean) {
                    comprobantes.add((FacturacionBean) o);
                    ingresos = ingresos.add(((FacturacionBean) o).getTotal());
                } else if (o instanceof HistoriaClinicaBean) {
                    historias.add((HistoriaClinicaBean) o);
                }
            }
            request.setAttribute("citas", citas);
            request.setAttribute("comprobantes", comprobantes);
            request.setAttribute("historias", historias);
            request.setAttribute("ingresos", ingresos);
            request.setAttribute("hayReporte", Boolean.TRUE);
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Las fechas no son válidas.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            log("Error al generar el reporte", e);
            request.setAttribute("error", "No se pudo generar el reporte. Intente de nuevo.");
        }
        request.getRequestDispatcher("/reportes.jsp").forward(request, response);
    }

    // ------------------------------------------------------------------ CSV

    /** Escapa un valor para CSV y neutraliza fórmulas (=, +, -, @) que Excel ejecutaría. */
    static String csv(Object valor) {
        String s = valor == null ? "" : valor.toString();
        if (!s.isEmpty() && "=+-@\t\r".indexOf(s.charAt(0)) >= 0) {
            s = "'" + s;
        }
        if (s.indexOf(',') >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0) {
            s = "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static void fila(StringBuilder sb, Object... valores) {
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(csv(valores[i]));
        }
        sb.append("\r\n");
    }

    /** Arma el CSV con tres bloques: citas, comprobantes (ingresos) e historias clínicas. Empieza con BOM para Excel. */
    static String construirCsv(LocalDate inicio, LocalDate fin, ArrayList<Object> datos) {
        StringBuilder sb = new StringBuilder("﻿");
        fila(sb, "Reporte administrativo", inicio, fin);
        sb.append("\r\n");
        fila(sb, "CITAS");
        fila(sb, "idReserva", "idHorario", "idSolicitud", "fechaReserva", "hora", "estado");
        for (Object o : datos) {
            if (o instanceof ReservaBean) {
                ReservaBean r = (ReservaBean) o;
                fila(sb, r.getIdReserva(), r.getIdHorario(), r.getIdSolicitud(), r.getFechaReserva(), r.getHora(), r.getEstado());
            }
        }
        sb.append("\r\n");
        fila(sb, "COMPROBANTES (INGRESOS)");
        fila(sb, "idComprobante", "idPago", "fechaEmision", "subtotal", "total");
        BigDecimal ingresos = BigDecimal.ZERO;
        for (Object o : datos) {
            if (o instanceof FacturacionBean) {
                FacturacionBean c = (FacturacionBean) o;
                fila(sb, c.getIdComprobante(), c.getIdPago(), c.getFechaEmision(), c.getSubtotal(), c.getTotal());
                ingresos = ingresos.add(c.getTotal());
            }
        }
        fila(sb, "Total ingresos", "", "", "", ingresos);
        sb.append("\r\n");
        fila(sb, "HISTORIAS CLINICAS");
        fila(sb, "idHistoria", "idPaciente", "fecha", "antecedentes");
        for (Object o : datos) {
            if (o instanceof HistoriaClinicaBean) {
                HistoriaClinicaBean h = (HistoriaClinicaBean) o;
                fila(sb, h.getIdHistoria(), h.getIdPaciente(), h.getFecha(), h.getAntecedentes());
            }
        }
        return sb.toString();
    }
}
