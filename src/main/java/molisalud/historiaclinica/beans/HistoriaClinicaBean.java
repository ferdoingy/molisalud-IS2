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
        return this.idHistoria;
    }

    /**
     * @param idHistoria
     */
    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }

    /**
     * @return
     */
    public int getIdPaciente() {
        return this.idPaciente;
    }

    /**
     * @param idPaciente
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
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
    public String getAntecedentes() {
        return this.antecedentes;
    }

    /**
     * @param antecedentes
     */
    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    /**
     * @param idHistoria 
     * @param idPaciente 
     * @param fecha 
     * @param antecedentes
     */
    public HistoriaClinicaBean(int idHistoria, int idPaciente, java.time.LocalDate fecha, String antecedentes) {
        this.idHistoria = idHistoria;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.antecedentes = antecedentes;
    }

}