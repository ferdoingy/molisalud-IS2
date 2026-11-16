package molisalud.reportes.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio Reporte. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface IReporteService {

    /**
     * @param fechaInicio 
     * @param fechaFin
     */
    public void generarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin);

    /**
     * @param fechaInicio 
     * @param fechaFin 
     * @return
     */
    public java.util.ArrayList<Object> consultarReporte(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin);

    /**
     * @param datos
     */
    public void exportarReporte(java.util.ArrayList<Object> datos);

}