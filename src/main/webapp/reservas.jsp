<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, molisalud.horarios.beans.HorarioBean, molisalud.login.beans.UsuarioBean, molisalud.reserva.beans.PacienteBean, molisalud.reserva.beans.ReservaBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ruta = request.getContextPath() + "/reservas";
    PacienteBean paciente = (PacienteBean) request.getAttribute("paciente");
    boolean buscado = request.getAttribute("pacienteBuscado") != null;
    String dniBuscado = (String) request.getAttribute("dniBuscado");
    ArrayList<HorarioBean> disponibilidad = (ArrayList<HorarioBean>) request.getAttribute("disponibilidad");
    ArrayList<ReservaBean> reservas = (ArrayList<ReservaBean>) request.getAttribute("reservas");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Reservas de citas</title>
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
        form.inline { display:inline; }
    </style>
</head>
<body>
<header>
    <strong>Molisalud &middot; Reservas de citas</strong>
    <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(usuario.getRol()) %>)
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/inicio.jsp">Inicio</a>
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
</header>
<main>
    <% if (request.getAttribute("mensaje") != null) { %><div class="msg ok"><%= esc(request.getAttribute("mensaje")) %></div><% } %>
    <% if (request.getAttribute("error") != null) { %><div class="msg err"><%= esc(request.getAttribute("error")) %></div><% } %>

    <section>
        <h2>1. Buscar paciente por DNI</h2>
        <form method="get" action="<%= ruta %>">
            <label>DNI <input name="dni" maxlength="8" pattern="[0-9]{8}" required value="<%= esc(dniBuscado) %>"></label>
            <button type="submit">Buscar</button>
        </form>
    </section>

    <% if (buscado && paciente == null) { %>
    <section>
        <h2>Registrar paciente nuevo</h2>
        <p>No existe un paciente con el DNI <%= esc(dniBuscado) %>.</p>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarPaciente">
            <label>DNI <input name="dni" maxlength="8" pattern="[0-9]{8}" required value="<%= esc(dniBuscado) %>"></label>
            <label>Nombres <input name="nombres" required></label>
            <label>Apellidos <input name="apellidos" required></label><br>
            <label>Teléfono <input name="telefono"></label>
            <label>Correo <input name="correo" type="email"></label>
            <label>Dirección <input name="direccion"></label><br>
            <button type="submit">Registrar paciente</button>
        </form>
    </section>
    <% } %>

    <% if (paciente != null) { %>
    <section>
        <h2>Ficha del paciente Nº <%= paciente.getIdPaciente() %></h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="actualizarPaciente">
            <input type="hidden" name="idPaciente" value="<%= paciente.getIdPaciente() %>">
            <label>DNI <input name="dni" maxlength="8" pattern="[0-9]{8}" required value="<%= esc(paciente.getDni()) %>"></label>
            <label>Nombres <input name="nombres" required value="<%= esc(paciente.getNombres()) %>"></label>
            <label>Apellidos <input name="apellidos" required value="<%= esc(paciente.getApellidos()) %>"></label><br>
            <label>Teléfono <input name="telefono" value="<%= esc(paciente.getTelefono()) %>"></label>
            <label>Correo <input name="correo" type="email" value="<%= esc(paciente.getCorreo()) %>"></label>
            <label>Dirección <input name="direccion" value="<%= esc(paciente.getDireccion()) %>"></label><br>
            <button type="submit">Guardar cambios</button>
        </form>
    </section>
    <section>
        <h2>2. Registrar solicitud de cita</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarSolicitud">
            <input type="hidden" name="idPaciente" value="<%= paciente.getIdPaciente() %>">
            <input type="hidden" name="dni" value="<%= esc(paciente.getDni()) %>">
            <label>Fecha deseada <input type="date" name="fechaEscogida" required></label>
            <label>Hora deseada <input type="time" name="horaEscogida" required></label>
            <label>Motivo <input name="motivoConsulta" required style="width:260px"></label>
            <button type="submit">Registrar solicitud</button>
        </form>
    </section>
    <% } %>

    <section>
        <h2>3. Disponibilidad de médicos (horarios publicados)</h2>
        <form method="get" action="<%= ruta %>" style="margin-bottom:12px">
            <label>Código de médico <input type="number" name="idMedico" min="1" required style="width:80px"
                value="<%= esc(request.getAttribute("idMedicoConsultado")) %>"></label>
            <% if (dniBuscado != null) { %><input type="hidden" name="dni" value="<%= esc(dniBuscado) %>"><% } %>
            <button type="submit">Consultar</button>
        </form>
        <% if (disponibilidad != null) { %>
        <table>
            <tr><th>Horario Nº</th><th>Médico</th><th>Día</th><th>Desde</th><th>Hasta</th></tr>
            <% if (disponibilidad.isEmpty()) { %><tr><td colspan="5">Ese médico no tiene horarios publicados.</td></tr><% } %>
            <% for (HorarioBean h : disponibilidad) { %>
            <tr><td><%= h.getIdHorario() %></td><td><%= h.getIdMedico() %></td><td><%= esc(h.getDiaSemana()) %></td>
                <td><%= h.getHoraInicio().toString().substring(0, 5) %></td><td><%= h.getHoraFin().toString().substring(0, 5) %></td></tr>
            <% } %>
        </table>
        <% } %>
    </section>

    <section>
        <h2>4. Registrar reserva de cita (30 min)</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarReserva">
            <label>Solicitud Nº <input type="number" name="idSolicitud" min="1" required style="width:80px"></label>
            <label>Horario Nº <input type="number" name="idHorario" min="1" required style="width:80px"></label>
            <label>Fecha <input type="date" name="fechaReserva" required></label>
            <label>Hora <input type="time" name="hora" required></label>
            <button type="submit">Registrar reserva</button>
        </form>
    </section>

    <section>
        <h2>Citas registradas</h2>
        <table>
            <tr><th>Reserva Nº</th><th>Solicitud</th><th>Horario</th><th>Fecha</th><th>Hora</th><th>Estado</th><th>Acciones</th></tr>
            <% if (reservas == null || reservas.isEmpty()) { %><tr><td colspan="7">No hay citas registradas.</td></tr><% }
               else { for (ReservaBean r : reservas) { %>
            <tr>
                <td><%= r.getIdReserva() %></td><td><%= r.getIdSolicitud() %></td><td><%= r.getIdHorario() %></td>
                <td><%= r.getFechaReserva() %></td><td><%= r.getHora().toString().substring(0, 5) %></td><td><%= esc(r.getEstado()) %></td>
                <td>
                    <% if ("Pendiente".equals(r.getEstado())) { %>
                    <form class="inline" method="post" action="<%= ruta %>">
                        <input type="hidden" name="accion" value="confirmar"><input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                        <button type="submit">Confirmar</button></form>
                    <% } %>
                    <% if ("Pendiente".equals(r.getEstado()) || "Confirmada".equals(r.getEstado())) { %>
                    <form class="inline" method="post" action="<%= ruta %>">
                        <input type="hidden" name="accion" value="cancelar"><input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                        <button type="submit" style="background:#b00020">Cancelar</button></form>
                    <% } %>
                </td>
            </tr>
            <% } } %>
        </table>
    </section>
</main>
</body>
</html>
