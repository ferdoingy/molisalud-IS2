package molisalud.reportes.controller;

import javax.servlet.http.HttpServlet;

import java.io.*;
import java.util.*;

/**
 * Controlador HTTP basado en javax.servlet 4.0.1. Configurar la ruta con WebServlet o web.xml al implementar. Validar permisos del usuario antes de delegar al servicio.
 */
public class ReporteServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public ReporteServlet() {
    }

    /**
     * 
     */
    private molisalud.reportes.service.IReporteService service = new molisalud.reportes.service.ReporteService();

    /**
     * Procesar la solicitud HTTP y delegar al servicio. Implementar manejo de excepciones y navegación cuando se construyan las vistas.
     * @param request 
     * @param response
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        // TODO implement here
    }

    /**
     * Procesar la solicitud HTTP y delegar al servicio. Implementar manejo de excepciones y navegación cuando se construyan las vistas.
     * @param request 
     * @param response
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        // TODO implement here
    }

}