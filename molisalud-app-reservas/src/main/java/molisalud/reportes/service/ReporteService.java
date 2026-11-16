package molisalud.reportes.service;

import java.io.*;
import java.util.*;

/**
 * Implementa IReporteService. Las operaciones del contrato están definidas en la interfaz y la extensión Java genera sus implementaciones aquí. Mantener objDAO solo en esta clase. Implementar validaciones y delegación al DAO.
 */
public class ReporteService implements IReporteService {

    /**
     * Default constructor
     */
    public ReporteService() {
    }

    /**
     * 
     */
    private molisalud.reportes.dao.ReporteDAO objDAO = new molisalud.reportes.dao.ReporteDAO();

    /**
     * @param fechaInicio 
     * @param fechaFin
     */
    public void generarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
        // TODO implement here
    }

    /**
     * @param fechaInicio 
     * @param fechaFin 
     * @return
     */
    public java.util.ArrayList<Object> consultarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
        // TODO implement here
        return null;
    }

    /**
     * @param datos
     */
    public void exportarReporte(java.util.ArrayList<Object> datos) {
        // TODO implement here
    }




}