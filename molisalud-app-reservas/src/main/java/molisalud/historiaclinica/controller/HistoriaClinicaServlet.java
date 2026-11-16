package molisalud.historiaclinica.controller;

import javax.servlet.http.HttpServlet;

import java.io.*;
import java.util.*;

/**
 * Controlador HTTP basado en javax.servlet 4.0.1. Configurar la ruta con WebServlet o web.xml al implementar. Validar permisos del usuario antes de delegar al servicio.
 */
public class HistoriaClinicaServlet extends HttpServlet {

    /**
     * Default constructor
     */
    public HistoriaClinicaServlet() {
    }

    /**
     * 
     */
    private molisalud.historiaclinica.service.IHistoriaClinicaService service = new molisalud.historiaclinica.service.HistoriaClinicaService();

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