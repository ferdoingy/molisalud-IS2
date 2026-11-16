<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="molisalud.login.beans.UsuarioBean" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Molisalud - Inicio</title>
    <style>
        body { font-family: Arial, sans-serif; margin:0; background:#eef3f7; }
        header { background:#14506e; color:#fff; padding:14px 24px; display:flex; justify-content:space-between; }
        header a { color:#fff; }
        main { padding:24px; }
    </style>
</head>
<body>
    <header>
        <strong>Molisalud</strong>
        <span><%= usuario.getNombreUsuario().replace("<", "&lt;") %> (<%= usuario.getRol().replace("<", "&lt;") %>)
            &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
    </header>
    <main>
        <h2>Bienvenido</h2>
        <p>Sesión iniciada correctamente.</p>
        <ul>
            <% String rolActual = usuario.getRol();
               if ("Recepcionista".equals(rolActual) || "Médico".equals(rolActual) || "Administrador de la clínica".equals(rolActual)) { %>
            <li><a href="<%= request.getContextPath() %>/horarios">Horarios médicos</a></li>
            <% } %>
            <% if ("Recepcionista".equals(rolActual)) { %>
            <li><a href="<%= request.getContextPath() %>/reservas">Reservas de citas</a></li>
            <% } %>
            <% if ("Recepcionista".equals(rolActual) || "Médico".equals(rolActual) || "Enfermero/a".equals(rolActual)) { %>
            <li><a href="<%= request.getContextPath() %>/historiaclinica">Historia clínica</a></li>
            <% } %>
            <% if ("Cajero".equals(rolActual)) { %>
            <li><a href="<%= request.getContextPath() %>/facturacion">Facturación</a></li>
            <% } %>
        </ul>
    </main>
</body>
</html>
