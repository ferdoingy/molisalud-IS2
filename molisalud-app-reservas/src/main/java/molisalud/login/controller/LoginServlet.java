package molisalud.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import molisalud.login.beans.UsuarioBean;

/**
 * Controlador HTTP basado en javax.servlet 4.0.1. Al autenticar, guarda el UsuarioBean (con su rol)
 * en la sesión HTTP bajo el nombre "usuario" para que los demás servlets puedan validar permisos.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public LoginServlet() {
    }

    private molisalud.login.service.ILoginService service = new molisalud.login.service.LoginService();

    /**
     * GET /login            -> muestra el formulario.
     * GET /login?accion=salir -> cierra la sesión y vuelve al formulario.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("salir".equals(request.getParameter("accion"))) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    /**
     * POST /login: valida las credenciales con el servicio.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String clave = request.getParameter("clave");
        try {
            UsuarioBean usuario = service.iniciarSesion(nombreUsuario, clave);
            if (usuario == null) {
                request.setAttribute("error", "Usuario o contraseña incorrectos.");
                request.setAttribute("nombreUsuario", nombreUsuario);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            request.getSession().invalidate(); // evita reutilizar una sesión anterior
            request.getSession(true).setAttribute("usuario", usuario);
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        } catch (RuntimeException e) {
            log("Error en el inicio de sesión", e);
            request.setAttribute("error", "No se pudo conectar con el sistema. Intente de nuevo.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
