<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private static String esc(String t) {
        if (t == null) return "";
        return t.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(""", "&quot;");
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Molisalud - Iniciar sesión</title>
    <style>
        body { font-family: Arial, sans-serif; background:#eef3f7; display:flex; align-items:center; justify-content:center; height:100vh; margin:0; }
        form { background:#fff; padding:32px; border-radius:8px; box-shadow:0 2px 10px rgba(0,0,0,.12); width:300px; }
        h1 { font-size:20px; margin:0 0 20px; color:#14506e; }
        label { display:block; font-size:13px; margin:12px 0 4px; }
        input { width:100%; padding:8px; box-sizing:border-box; }
        button { margin-top:20px; width:100%; padding:10px; background:#14506e; color:#fff; border:0; border-radius:4px; cursor:pointer; }
        .error { color:#b00020; font-size:13px; margin-top:12px; }
    </style>
</head>
<body>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <h1>Molisalud</h1>
        <label for="nombreUsuario">Usuario</label>
        <input id="nombreUsuario" name="nombreUsuario" value="<%= esc((String) request.getAttribute("nombreUsuario")) %>" required autofocus>
        <label for="clave">Contraseña</label>
        <input id="clave" name="clave" type="password" required>
        <button type="submit">Ingresar</button>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= esc((String) request.getAttribute("error")) %></div>
        <% } %>
    </form>
</body>
</html>
