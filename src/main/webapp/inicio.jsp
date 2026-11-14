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
        <p>Sesión iniciada correctamente. Los módulos se irán habilitando aquí.</p>
    </main>
</body>
</html>
