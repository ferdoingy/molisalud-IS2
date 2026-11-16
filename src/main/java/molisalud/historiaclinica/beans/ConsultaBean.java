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
    public int getIdCita() {
        return this.idCita;
    }

    /**
     * @param idCita
     */
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    /**
     * @return
     */
    public int getIdMedico() {
        return this.idMedico;
    }

    /**
     * @param idMedico
     */
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * @return
     */
    public java.time.LocalDateTime getFecha() {
        return this.fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return
     */
    public String getMotivoConsulta() {
        return this.motivoConsulta;
    }

    /**
     * @param motivoConsulta
     */
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    /**
     * @return
     */
    public String getObservaciones() {
        return this.observaciones;
    }

    /**
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        this.idConsulta = idConsulta;
        this.idHistoria = idHistoria;
        this.idCita = idCita;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.motivoConsulta = motivoConsulta;
        this.observaciones = observaciones;
    }

}