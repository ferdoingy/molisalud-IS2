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
        return this.idReceta;
    }

    /**
     * @param idReceta
     */
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * @return
     */
    public int getIdConsulta() {
        return this.idConsulta;
    }

    /**
     * @param idConsulta
     */
    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    /**
     * @return
     */
    public java.time.LocalDate getFecha() {
        return this.fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return
     */
    public String getIndicaciones() {
        return this.indicaciones;
    }

    /**
     * @param indicaciones
     */
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    /**
     * @param idReceta 
     * @param idConsulta 
     * @param fecha 
     * @param indicaciones
     */
    public RecetaBean(int idReceta, int idConsulta, java.time.LocalDate fecha, String indicaciones) {
        this.idReceta = idReceta;
        this.idConsulta = idConsulta;
        this.fecha = fecha;
        this.indicaciones = indicaciones;
    }

}