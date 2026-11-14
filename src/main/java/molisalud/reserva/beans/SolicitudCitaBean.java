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
        // TODO implement here
        return 0;
    }

    /**
     * @param idSolicitud
     */
    public void setIdSolicitud(int idSolicitud) {
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
    public java.time.LocalDate getFechaSolicitud() {
        // TODO implement here
        return null;
    }

    /**
     * @param fechaSolicitud
     */
    public void setFechaSolicitud(java.time.LocalDate fechaSolicitud) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalDate getFechaEscogida() {
        // TODO implement here
        return null;
    }

    /**
     * @param fechaEscogida
     */
    public void setFechaEscogida(java.time.LocalDate fechaEscogida) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraEscogida() {
        // TODO implement here
        return null;
    }

    /**
     * @param horaEscogida
     */
    public void setHoraEscogida(java.time.LocalTime horaEscogida) {
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
    public String getEstado() {
        // TODO implement here
        return "";
    }

    /**
     * @param estado
     */
    public void setEstado(String estado) {
        // TODO implement here
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
        // TODO implement here
    }

}