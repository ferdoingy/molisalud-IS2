package molisalud.historiaclinica.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Receta. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class RecetaBean {

    /**
     * Default constructor
     */
    public RecetaBean() {
    }

    /**
     * 
     */
    private int idReceta;

    /**
     * 
     */
    private int idConsulta;

    /**
     * 
     */
    private java.time.LocalDate fecha;

    /**
     * 
     */
    private String indicaciones;

    /**
     * @return
     */
    public int getIdReceta() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idReceta
     */
    public void setIdReceta(int idReceta) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdConsulta() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idConsulta
     */
    public void setIdConsulta(int idConsulta) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalDate getFecha() {
        // TODO implement here
        return null;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDate fecha) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getIndicaciones() {
        // TODO implement here
        return "";
    }

    /**
     * @param indicaciones
     */
    public void setIndicaciones(String indicaciones) {
        // TODO implement here
    }

    /**
     * @param idReceta 
     * @param idConsulta 
     * @param fecha 
     * @param indicaciones
     */
    public RecetaBean(int idReceta, int idConsulta, java.time.LocalDate fecha, String indicaciones) {
        // TODO implement here
    }

}