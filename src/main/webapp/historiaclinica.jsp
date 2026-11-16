<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, molisalud.historiaclinica.beans.HistoriaClinicaBean, molisalud.historiaclinica.beans.TriajeBean, molisalud.login.beans.UsuarioBean, molisalud.reserva.beans.PacienteBean" %>
<%@ include file="/WEB-INF/jspf/util.jspf" %>
<%
    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String rol = usuario.getRol();
    boolean esMedico = "Médico".equals(rol);
    boolean esEnfermero = "Enfermero/a".equals(rol);
    boolean esRecepcionista = "Recepcionista".equals(rol);
    boolean veClinico = esMedico || esEnfermero;
    String ruta = request.getContextPath() + "/historiaclinica";
    PacienteBean paciente = (PacienteBean) request.getAttribute("paciente");
    boolean buscado = request.getAttribute("pacienteBuscado") != null;
    String dniBuscado = (String) request.getAttribute("dniBuscado");
    HistoriaClinicaBean historia = (HistoriaClinicaBean) request.getAttribute("historia");
    ArrayList<TriajeBean> triajes = (ArrayList<TriajeBean>) request.getAttribute("triajes");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Historia clínica</title>
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
        input, select, textarea { padding:6px; }
        button { padding:6px 12px; background:#14506e; color:#fff; border:0; border-radius:4px; cursor:pointer; }
        .msg { padding:10px; border-radius:4px; margin-bottom:16px; }
        .ok { background:#e3f4e8; color:#1b5e20; }
        .err { background:#fdeaea; color:#b00020; }
        .nota { color:#555; font-size:13px; }
    </style>
</head>
<body>
<header>
    <strong>Molisalud &middot; Historia clínica</strong>
    <span><%= esc(usuario.getNombreUsuario()) %> (<%= esc(rol) %>)
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/inicio.jsp">Inicio</a>
        &nbsp;|&nbsp; <a href="<%= request.getContextPath() %>/login?accion=salir">Cerrar sesión</a></span>
</header>
<main>
    <% if (request.getAttribute("mensaje") != null) { %><div class="msg ok"><%= esc(request.getAttribute("mensaje")) %></div><% } %>
    <% if (request.getAttribute("error") != null) { %><div class="msg err"><%= esc(request.getAttribute("error")) %></div><% } %>

    <section>
        <h2>Buscar paciente por DNI</h2>
        <form method="get" action="<%= ruta %>">
            <label>DNI <input name="dni" maxlength="8" pattern="[0-9]{8}" required value="<%= esc(dniBuscado) %>"></label>
            <button type="submit">Buscar</button>
        </form>
    </section>

    <% if (buscado && paciente == null) { %>
    <section><p>No existe un paciente con el DNI <%= esc(dniBuscado) %>. Debe registrarse primero en Reservas de citas.</p></section>
    <% } %>

    <% if (paciente != null) { %>
    <section>
        <h2>Paciente: <%= esc(paciente.getNombres()) %> <%= esc(paciente.getApellidos()) %> (DNI <%= esc(paciente.getDni()) %>)</h2>
        <% if (historia == null) { %>
            <p>Este paciente todavía no tiene historia clínica.</p>
            <% if (esRecepcionista || esMedico) { %>
            <form method="post" action="<%= ruta %>">
                <input type="hidden" name="accion" value="crearHistoria">
                <input type="hidden" name="idPaciente" value="<%= paciente.getIdPaciente() %>">
                <input type="hidden" name="dni" value="<%= esc(paciente.getDni()) %>">
                <label>Antecedentes (opcional) <input name="antecedentes" maxlength="255" style="width:380px"></label>
                <button type="submit">Crear historia clínica</button>
            </form>
            <% } %>
        <% } else { %>
            <p><strong>Historia clínica Nº <%= historia.getIdHistoria() %></strong> &middot; creada el <%= historia.getFecha() %></p>
            <% if (veClinico) { %>
            <p><strong>Antecedentes:</strong> <%= historia.getAntecedentes() == null ? "(sin registrar)" : esc(historia.getAntecedentes()) %></p>
            <% } %>
        <% } %>
    </section>

    <% if (historia != null && veClinico) { %>
    <section>
        <h2>Triajes</h2>
        <table>
            <tr><th>Nº</th><th>Cita</th><th>Fecha y hora</th><th>Temp. (°C)</th><th>Peso (kg)</th><th>Talla (cm)</th><th>Presión</th></tr>
            <% if (triajes == null || triajes.isEmpty()) { %><tr><td colspan="7">Sin triajes registrados.</td></tr><% }
               else { for (TriajeBean t : triajes) { %>
            <tr><td><%= t.getIdTriaje() %></td><td><%= t.getIdCita() %></td>
                <td><%= t.getFecha().toLocalDate() %> <%= t.getFecha().toLocalTime().toString().substring(0, 5) %></td>
                <td><%= t.getTemperatura() %></td><td><%= t.getPeso() %></td><td><%= t.getTalla() %></td><td><%= esc(t.getPresionArterial()) %></td></tr>
            <% } } %>
        </table>
    </section>
    <% } %>
    <% } %>

    <% if (esEnfermero) { %>
    <section>
        <h2>Registrar triaje</h2>
        <p class="nota">La cita debe estar Confirmada y el paciente debe tener historia clínica.</p>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarTriaje">
            <% if (dniBuscado != null) { %><input type="hidden" name="dni" value="<%= esc(dniBuscado) %>"><% } %>
            <label>Cita Nº <input type="number" name="idCita" min="1" required style="width:80px"></label>
            <label>Temperatura (°C) <input name="temperatura" required style="width:70px" placeholder="36.5"></label>
            <label>Peso (kg) <input name="peso" required style="width:70px" placeholder="70"></label>
            <label>Talla (cm) <input name="talla" required style="width:70px" placeholder="170"></label>
            <label>Presión <input name="presionArterial" required style="width:80px" placeholder="120/80" pattern="[0-9]{2,3}/[0-9]{2,3}"></label>
            <button type="submit">Registrar triaje</button>
        </form>
    </section>
    <% } %>

    <% if (esMedico) { %>
    <section>
        <h2>Registrar consulta médica</h2>
        <p class="nota">La cita debe estar Confirmada y tener triaje. Al registrarla pasa a Atendida.</p>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarConsulta">
            <% if (dniBuscado != null) { %><input type="hidden" name="dni" value="<%= esc(dniBuscado) %>"><% } %>
            <label>Cita Nº <input type="number" name="idCita" min="1" required style="width:80px"></label>
            <label>Código de médico <input type="number" name="idMedico" min="1" required style="width:80px"></label><br>
            <label>Motivo <input name="motivoConsulta" required maxlength="255" style="width:300px"></label>
            <label>Observaciones <input name="observaciones" maxlength="255" style="width:300px"></label>
            <button type="submit">Registrar consulta</button>
        </form>
    </section>
    <section>
        <h2>Registrar diagnóstico</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="registrarDiagnostico">
            <% if (dniBuscado != null) { %><input type="hidden" name="dni" value="<%= esc(dniBuscado) %>"><% } %>
            <label>Consulta Nº <input type="number" name="idConsulta" min="1" required style="width:80px"></label>
            <label>Descripción <input name="descripcion" required maxlength="255" style="width:420px"></label>
            <button type="submit">Registrar diagnóstico</button>
        </form>
    </section>
    <section>
        <h2>Emitir receta</h2>
        <form method="post" action="<%= ruta %>">
            <input type="hidden" name="accion" value="emitirReceta">
            <% if (dniBuscado != null) { %><input type="hidden" name="dni" value="<%= esc(dniBuscado) %>"><% } %>
            <label>Consulta Nº <input type="number" name="idConsulta" min="1" required style="width:80px"></label>
            <label>Indicaciones <input name="indicaciones" required maxlength="255" style="width:420px"></label>
            <button type="submit">Emitir receta</button>
        </form>
    </section>
    <% } %>
</main>
</body>
</html>
