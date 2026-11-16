<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, molisalud.horarios.beans.HorarioBean, molisalud.login.beans.UsuarioBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String rol = usuario.getRol();
    boolean esMedico = "Médico".equals(rol);
    boolean esRecepcionista = "Recepcionista".equals(rol);
    boolean esAdmin = "Administrador de la clínica".equals(rol);
    String ruta = request.getContextPath() + "/horarios";
    ArrayList<HorarioBean> horarios = (ArrayList<HorarioBean>) request.getAttribute("horarios");
    HorarioBean editar = (HorarioBean) request.getAttribute("editar");
    String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Horarios médicos</title>
    <style>
        body { font-family: Arial, sans-serif; margin:0; background:#eef3f7; }
        header { background:#14506e; color:#fff; padding:14px 24px; display:flex; justify-content:space-between; }
        header a { color:#fff; }
        main { padding:24px; max-width:1000px; }
        section { background:#fff; padding:16px 20px; border-radius:8px; margin-bottom:20px; box-shadow:0 1px 6px rgba(0,0,0,.1); }
        h2 { margin-top:0; font-size:18px; color:#14506e; }
        table { border-collapse:collapse; width:100%; }
        th, td { border-bottom:1px solid #dde4ea; padding:8px; text-align:left; font-size:14px; }
        label { margin-right:12px; font-size:13px; }
        input, select { padding:6px; }
        button { padding:6px 12px; background:#14506e; color:#fff; border:0; border-radius:4px; cursor:pointer; }
        .msg { padding:10px; border-radius:4px; margin-bottom:16px; }
        .ok { background:#e3f4e8; color:#1b5e20; }
        .err { background:#fdeaea; color:#b00020; }
        form.inline { display:inline; }
    </style>
</head>
<body>
<header>
    <strong>Molisalud &middot; Horarios médicos</strong>
    <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(rol) %>)
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/inicio.jsp">Inicio</a>
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
</header>
<main>
    <% if (request.getAttribute("mensaje") != null) { %><div class="msg ok"><%= esc(request.getAttribute("mensaje")) %></div><% } %>
    <% if (request.getAttribute("error") != null) { %><div class="msg err"><%= esc(request.getAttribute("error")) %></div><% } %>

    <% if (esMedico) { %>
    <section>
        <h2>Registrar disponibilidad</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrar">
            <label>Código de médico <input type="number" name="idMedico" min="1" required style="width:80px"></label>
            <label>Día <select name="diaSemana" required>
                <% for (String d : dias) { %><option><%= d %></option><% } %>
            </select></label>
            <label>Desde <input type="time" name="horaInicio" required></label>
            <label>Hasta <input type="time" name="horaFin" required></label>
            <button type="submit">Registrar</button>
        </form>
    </section>
    <% } %>

    <% if (editar != null) { %>
    <section>
        <h2>Actualizar horario Nº <%= editar.getIdHorario() %> (médico <%= editar.getIdMedico() %>)</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="actualizar">
            <input type="hidden" name="idHorario" value="<%= editar.getIdHorario() %>">
            <input type="hidden" name="idMedico" value="<%= editar.getIdMedico() %>">
            <label>Día <select name="diaSemana" required>
                <% for (String d : dias) { %><option <%= d.equals(editar.getDiaSemana()) ? "selected" : "" %>><%= d %></option><% } %>
            </select></label>
            <label>Desde <input type="time" name="horaInicio" value="<%= editar.getHoraInicio().toString().substring(0, 5) %>" required></label>
            <label>Hasta <input type="time" name="horaFin" value="<%= editar.getHoraFin().toString().substring(0, 5) %>" required></label>
            <button type="submit">Guardar cambios</button>
            &nbsp;<a href="<%= ruta %>">Cancelar</a>
        </form>
    </section>
    <% } %>

    <section>
        <h2>Horarios</h2>
        <form method="get" action="<%= ruta %>" style="margin-bottom:12px">
            <label>Filtrar por código de médico <input type="number" name="idMedico" min="1" style="width:80px"></label>
            <button type="submit">Consultar</button>
            &nbsp;<a href="<%= ruta %>">Ver todos</a>
        </form>
        <table>
            <tr><th>Nº</th><th>Médico</th><th>Día</th><th>Desde</th><th>Hasta</th><th>Estado</th><th>Acciones</th></tr>
            <% if (horarios == null || horarios.isEmpty()) { %>
            <tr><td colspan="7">No hay horarios registrados.</td></tr>
            <% } else { for (HorarioBean h : horarios) { %>
            <tr>
                <td><%= h.getIdHorario() %></td>
                <td><%= h.getIdMedico() %></td>
                <td><%= esc(h.getDiaSemana()) %></td>
                <td><%= h.getHoraInicio().toString().substring(0, 5) %></td>
                <td><%= h.getHoraFin().toString().substring(0, 5) %></td>
                <td><%= esc(h.getEstado()) %></td>
                <td>
                    <a href="<%= ruta %>?editar=<%= h.getIdHorario() %>">Editar</a>
                    <% if (esRecepcionista && "Disponible".equals(h.getEstado())) { %>
                    <form class="inline" method="post" action="<%= ruta %>">
                        <input type="hidden" name="accion" value="asignar">
                        <input type="hidden" name="idHorario" value="<%= h.getIdHorario() %>">
                        <button type="submit">Asignar turno</button>
                    </form>
                    <% } %>
                    <% if (esAdmin && "Asignado".equals(h.getEstado())) { %>
                    <form class="inline" method="post" action="<%= ruta %>">
                        <input type="hidden" name="accion" value="publicar">
                        <input type="hidden" name="idHorario" value="<%= h.getIdHorario() %>">
                        <button type="submit">Publicar</button>
                    </form>
                    <% } %>
                </td>
            </tr>
            <% } } %>
        </table>
    </section>
</main>
</body>
</html>
