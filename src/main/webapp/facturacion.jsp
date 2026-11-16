<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, molisalud.facturacion.beans.TarifarioBean, molisalud.login.beans.UsuarioBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ruta = request.getContextPath() + "/facturacion";
    ArrayList<TarifarioBean> tarifario = (ArrayList<TarifarioBean>) request.getAttribute("tarifario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Facturación</title>
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
        input, select { padding:6px; }
        button { padding:6px 12px; background:#14506e; color:#fff; border:0; border-radius:4px; cursor:pointer; }
        .msg { padding:10px; border-radius:4px; margin-bottom:16px; }
        .ok { background:#e3f4e8; color:#1b5e20; }
        .err { background:#fdeaea; color:#b00020; }
        .nota { color:#555; font-size:13px; }
    </style>
</head>
<body>
<header>
    <strong>Molisalud &middot; Facturación</strong>
    <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(usuario.getRol()) %>)
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/inicio.jsp">Inicio</a>
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
</header>
<main>
    <% if (request.getAttribute("mensaje") != null) { %><div class="msg ok"><%= esc(request.getAttribute("mensaje")) %></div><% } %>
    <% if (request.getAttribute("error") != null) { %><div class="msg err"><%= esc(request.getAttribute("error")) %></div><% } %>

    <section>
        <h2>Tarifario de servicios</h2>
        <p class="nota">Los precios incluyen IGV (18%).</p>
        <table>
            <tr><th>Nº</th><th>Servicio</th><th>Precio (S/)</th></tr>
            <% if (tarifario == null || tarifario.isEmpty()) { %><tr><td colspan="3">No hay servicios en el tarifario.</td></tr><% }
               else { for (TarifarioBean t : tarifario) { %>
            <tr><td><%= t.getIdServicio() %></td><td><%= esc(t.getNombreServicio()) %></td><td><%= t.getPrecio() %></td></tr>
            <% } } %>
        </table>
    </section>

    <section>
        <h2>Facturar atención médica</h2>
        <p class="nota">La cita debe estar Atendida. Se registra el pago y se emite el comprobante en un solo paso.</p>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="facturar">
            <label>Cita Nº <input type="number" name="idCita" min="1" required style="width:80px"></label>
            <label>Servicio <select name="idServicio" required>
                <option value="">Seleccione...</option>
                <% if (tarifario != null) { for (TarifarioBean t : tarifario) { %>
                <option value="<%= t.getIdServicio() %>"><%= esc(t.getNombreServicio()) %> - S/ <%= t.getPrecio() %></option>
                <% } } %>
            </select></label>
            <label>Método de pago <select name="metodoPago" required>
                <option>Efectivo</option><option>Tarjeta (POS)</option><option>Transferencia</option>
            </select></label>
            <button type="submit">Facturar</button>
        </form>
    </section>
</main>
</body>
</html>
