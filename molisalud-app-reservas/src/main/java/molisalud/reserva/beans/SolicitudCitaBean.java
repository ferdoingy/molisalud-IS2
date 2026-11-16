package molisalud.reserva.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de SolicitudCita. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class SolicitudCitaBean {

    /**
     * Default constructor
     */
    public SolicitudCitaBean() {
    }

    /**
     * 
     */
    private int idSolicitud;

    /**
     * 
     */
    private int idPaciente;

    /**
     * 
     */
    private java.time.LocalDate fechaSolicitud;

    /**
     * 
     */
    private java.time.LocalDate fechaEscogida;

    /**
     * 
     */
    private java.time.LocalTime horaEscogida;

    /**
     * 
     */
    private String motivoConsulta;

    /**
     * 
     */
    private String estado;

    /**
     * @return
     */
    public int getIdSolicitud() {
        return this.idSolicitud;
    }

    /**
     * @param idSolicitud
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
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
    public java.time.LocalDate getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    /**
     * @param fechaSolicitud
     */
    public void setFechaSolicitud(java.time.LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return
     */
    public java.time.LocalDate getFechaEscogida() {
        return this.fechaEscogida;
    }

    /**
     * @param fechaEscogida
     */
    public void setFechaEscogida(java.time.LocalDate fechaEscogida) {
        this.fechaEscogida = fechaEscogida;
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraEscogida() {
        return this.horaEscogida;
    }

    /**
     * @param horaEscogida
     */
    public void setHoraEscogida(java.time.LocalTime horaEscogida) {
        this.horaEscogida = horaEscogida;
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
    public String getEstado() {
        return this.estado;
    }

    /**
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @param idSolicitud 
     * @param idPaciente 
     * @param fechaSolicitud 
     * @param fechaEscogida 
     * @param horaEscogida 
     * @param motivoConsulta 
     * @param estado
     */
    public SolicitudCitaBean(int idSolicitud, int idPaciente, java.time.LocalDate fechaSolicitud, java.time.LocalDate fechaEscogida, java.time.LocalTime horaEscogida, String motivoConsulta, String estado) {
        this.idSolicitud = idSolicitud;
        this.idPaciente = idPaciente;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaEscogida = fechaEscogida;
        this.horaEscogida = horaEscogida;
        this.motivoConsulta = motivoConsulta;
        this.estado = estado;
    }

}