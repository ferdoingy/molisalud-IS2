package molisalud.historiaclinica.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Consulta. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class ConsultaBean {

    /**
     * Default constructor
     */
    public ConsultaBean() {
    }

    /**
     * 
     */
    private int idConsulta;

    /**
     * 
     */
    private int idHistoria;

    /**
     * 
     */
    private int idCita;

    /**
     * 
     */
    private int idMedico;

    /**
     * 
     */
    private java.time.LocalDateTime fecha;

    /**
     * 
     */
    private String motivoConsulta;

    /**
     * 
     */
    private String observaciones;

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
    public int getIdCita() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idCita
     */
    public void setIdCita(int idCita) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdMedico() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idMedico
     */
    public void setIdMedico(int idMedico) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalDateTime getFecha() {
        // TODO implement here
        return null;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDateTime fecha) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getMotivoConsulta() {
        // TODO implement here
        return "";
    }

    /**
     * @param motivoConsulta
     */
    public void setMotivoConsulta(String motivoConsulta) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getObservaciones() {
        // TODO implement here
        return "";
    }

    /**
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        // TODO implement here
    }

    /**
     * @param idConsulta 
     * @param idHistoria 
     * @param idCita 
     * @param idMedico 
     * @param fecha 
     * @param motivoConsulta 
     * @param observaciones
     */
    public ConsultaBean(int idConsulta, int idHistoria, int idCita, int idMedico, java.time.LocalDateTime fecha, String motivoConsulta, String observaciones) {
        // TODO implement here
    }

}