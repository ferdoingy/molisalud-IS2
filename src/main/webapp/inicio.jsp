<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, molisalud.login.beans.UsuarioBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String rol = usuario.getRol();
    String base = request.getContextPath();
    // Módulos según el rol: {texto del botón, ruta}
    ArrayList<String[]> modulos = new ArrayList<>();
    if ("Recepcionista".equals(rol) || "Médico".equals(rol) || "Administrador de la clínica".equals(rol)) {
        modulos.add(new String[] {"Horarios médicos", base + "/horarios"});
    }
    if ("Recepcionista".equals(rol)) {
        modulos.add(new String[] {"Reservas de citas", base + "/reservas"});
    }
    if ("Recepcionista".equals(rol) || "Médico".equals(rol) || "Enfermero/a".equals(rol)) {
        modulos.add(new String[] {"Historia clínica", base + "/historiaclinica"});
    }
    if ("Cajero".equals(rol)) {
        modulos.add(new String[] {"Facturación", base + "/facturacion"});
    }
    if ("Administrador de la clínica".equals(rol)) {
        modulos.add(new String[] {"Reportes administrativos", base + "/reportes"});
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Inicio</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: Arial, sans-serif; margin:0; background:#eef3f7; min-height:100vh; display:flex; flex-direction:column; }
        header { background:#14506e; color:#fff; padding:14px 24px; display:flex; justify-content:space-between; flex-wrap:wrap; }
        header a { color:#fff; }
        main { flex:1; display:flex; flex-direction:column; padding:24px; }
        h2 { margin:0 0 16px; color:#14506e; }
        .menu { flex:1; display:grid; gap:20px; grid-template-columns:repeat(auto-fit, minmax(240px, 1fr)); grid-auto-rows:minmax(140px, 1fr); }
        .menu a { display:flex; align-items:center; justify-content:center; text-align:center; padding:20px;
                  background:#14506e; color:#fff; text-decoration:none; font-size:24px; font-weight:bold;
                  border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,.18); }
        .menu a:hover { background:#1d6d96; }
    </style>
</head>
<body>
    <header>
        <strong>Molisalud</strong>
        <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(rol) %>)
            &nbsp;|&nbsp; <a href="<%= base %>/login?accion=salir">Cerrar sesión</a></span>
    </header>
    <main>
        <h2>Bienvenido</h2>
        <% if (modulos.isEmpty()) { %>
        <p>Su rol no tiene módulos habilitados.</p>
        <% } else { %>
        <div class="menu">
            <% for (String[] m : modulos) { %>
            <a href="<%= m[1] %>"><%= m[0] %></a>
            <% } %>
        </div>
        <% } %>
    </main>
</body>
</html>
