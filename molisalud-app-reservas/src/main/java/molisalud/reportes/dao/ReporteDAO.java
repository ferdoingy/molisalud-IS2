package molisalud.reportes.dao;

import java.io.*;
import java.util.*;

/**
 * Persistencia/consulta de Reportes administrativos
 */
public class ReporteDAO {

    /**
     * Default constructor
     */
    public ReporteDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /**
     * Consolida citas, ingresos e historias clínicas del periodo para generar el reporte administrativo.
     * @param fechaInicio 
     * @param fechaFin
     */
    public void generarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
        // TODO implement here
    }

    /**
     * Cada elemento del resultado es un ReservaBean, FacturacionBean o HistoriaClinicaBean, según el módulo consolidado en el reporte.
     * @param fechaInicio 
     * @param fechaFin 
     * @return
     */
    public java.util.ArrayList<Object> consultarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
        // TODO implement here
        return null;
    }

    /**
     * Exporta los datos previamente consolidados por consultarReporte.
     * @param datos
     */
    public void exportarReporte(java.util.ArrayList<Object> datos) {
        // TODO implement here
    }

}