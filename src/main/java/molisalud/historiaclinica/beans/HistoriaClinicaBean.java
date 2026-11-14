package molisalud.historiaclinica.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de HistoriaClinica. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class HistoriaClinicaBean {

    /**
     * Default constructor
     */
    public HistoriaClinicaBean() {
    }

    /**
     * 
     */
    private int idHistoria;

    /**
     * 
     */
    private int idPaciente;

    /**
     * 
     */
    private java.time.LocalDate fecha;

    /**
     * 
     */
    private String antecedentes;

    /**
     * @return
     */
    public int getIdHistoria() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idHistoria
     */
    public void setIdHistoria(int idHistoria) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdPaciente() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idPaciente
     */
    public void setIdPaciente(int idPaciente) {
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
    public String getAntecedentes() {
        // TODO implement here
        return "";
    }

    /**
     * @param antecedentes
     */
    public void setAntecedentes(String antecedentes) {
        // TODO implement here
    }

    /**
     * @param idHistoria 
     * @param idPaciente 
     * @param fecha 
     * @param antecedentes
     */
    public HistoriaClinicaBean(int idHistoria, int idPaciente, java.time.LocalDate fecha, String antecedentes) {
        // TODO implement here
    }

}