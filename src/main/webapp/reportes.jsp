<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.math.BigDecimal, java.util.ArrayList, molisalud.facturacion.beans.FacturacionBean, molisalud.historiaclinica.beans.HistoriaClinicaBean, molisalud.login.beans.UsuarioBean, molisalud.reserva.beans.ReservaBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ruta = request.getContextPath() + "/reportes";
    boolean hayReporte = request.getAttribute("hayReporte") != null;
    ArrayList<ReservaBean> citas = (ArrayList<ReservaBean>) request.getAttribute("citas");
    ArrayList<FacturacionBean> comprobantes = (ArrayList<FacturacionBean>) request.getAttribute("comprobantes");
    ArrayList<HistoriaClinicaBean> historias = (ArrayList<HistoriaClinicaBean>) request.getAttribute("historias");
    BigDecimal ingresos = (BigDecimal) request.getAttribute("ingresos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Reportes</title>
    <style>
        body { font-family: Arial, sans-serif; margin:0; background:#eef3f7; }
        header { background:#14506e; color:#fff; padding:14px 24px; display:flex; justify-content:space-between; flex-wrap:wrap; }
        header a { color:#fff; }
        main { padding:24px; max-width:1000px; }
        section { background:#fff; padding:16px 20px; border-radius:8px; margin-bottom:20px; box-shadow:0 1px 6px rgba(0,0,0,.1); }
        h2 { margin-top:0; font-size:18px; color:#14506e; }
        table { border-collapse:collapse; width:100%; }
        th, td { border-bottom:1px solid #dde4ea; padding:8px; text-align:left; font-size:14px; }
        label { display:inline-block; margin:4px 12px 4px 0; font-size:13px; }
        input { padding:6px; }
        button { padding:6px 12px; background:#14506e; color:#fff; border:0; border-radius:4px; cursor:pointer; margin-right:6px; }
        .msg { padding:10px; border-radius:4px; margin-bottom:16px; }
        .err { background:#fdeaea; color:#b00020; }
        .resumen span { display:inline-block; margin-right:28px; font-size:15px; }
        .nota { color:#555; font-size:13px; }
    </style>
</head>
<body>
<header>
    <strong>Molisalud &middot; Reportes administrativos</strong>
    <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(usuario.getRol()) %>)
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/inicio.jsp">Inicio</a>
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
</header>
<main>
    <% if (request.getAttribute("error") != null) { %><div class="msg err"><%= esc(request.getAttribute("error")) %></div><% } %>

    <section>
        <h2>Periodo del reporte</h2>
        <form method="get" action="<%= ruta %>">
            <label>Desde <input type="date" name="fechaInicio" required value="<%= esc(request.getAttribute("fechaInicio")) %>"></label>
            <label>Hasta <input type="date" name="fechaFin" required value="<%= esc(request.getAttribute("fechaFin")) %>"></label>
            <button type="submit" name="accion" value="ver">Generar reporte</button>
            <button type="submit" name="accion" value="exportar">Exportar CSV</button>
        </form>
        <p class="nota">Citas por fecha de la cita, ingresos por fecha de emisión del comprobante e historias por fecha de creación. El periodo incluye ambas fechas.</p>
    </section>

    <% if (hayReporte) { %>
    <section class="resumen">
        <h2>Resumen</h2>
        <span><strong><%= citas.size() %></strong> citas</span>
        <span><strong>S/ <%= ingresos %></strong> de ingresos (<%= comprobantes.size() %> comprobantes)</span>
        <span><strong><%= historias.size() %></strong> historias clínicas nuevas</span>
    </section>

    <section>
        <h2>Citas</h2>
        <table>
            <tr><th>Reserva Nº</th><th>Horario</th><th>Solicitud</th><th>Fecha</th><th>Hora</th><th>Estado</th></tr>
            <% if (citas.isEmpty()) { %><tr><td colspan="6">Sin citas en el periodo.</td></tr><% } %>
            <% for (ReservaBean r : citas) { %>
            <tr><td><%= r.getIdReserva() %></td><td><%= r.getIdHorario() %></td><td><%= r.getIdSolicitud() %></td>
                <td><%= r.getFechaReserva() %></td><td><%= r.getHora().toString().substring(0, 5) %></td><td><%= esc(r.getEstado()) %></td></tr>
            <% } %>
        </table>
    </section>

    <section>
        <h2>Ingresos (comprobantes)</h2>
        <table>
            <tr><th>Comprobante Nº</th><th>Pago</th><th>Emisión</th><th>Subtotal (S/)</th><th>Total (S/)</th></tr>
            <% if (comprobantes.isEmpty()) { %><tr><td colspan="5">Sin comprobantes en el periodo.</td></tr><% } %>
            <% for (FacturacionBean c : comprobantes) { %>
            <tr><td><%= c.getIdComprobante() %></td><td><%= c.getIdPago() %></td><td><%= c.getFechaEmision() %></td>
                <td><%= c.getSubtotal() %></td><td><%= c.getTotal() %></td></tr>
            <% } %>
        </table>
    </section>

    <section>
        <h2>Historias clínicas nuevas</h2>
        <table>
            <tr><th>Historia Nº</th><th>Paciente</th><th>Fecha</th></tr>
            <% if (historias.isEmpty()) { %><tr><td colspan="3">Sin historias nuevas en el periodo.</td></tr><% } %>
            <% for (HistoriaClinicaBean h : historias) { %>
            <tr><td><%= h.getIdHistoria() %></td><td><%= h.getIdPaciente() %></td><td><%= h.getFecha() %></td></tr>
            <% } %>
        </table>
    </section>
    <% } %>
</main>
</body>
</html>
