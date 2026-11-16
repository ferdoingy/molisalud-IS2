package molisalud.reserva.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Reserva. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class ReservaBean {

    /**
     * Default constructor
     */
    public ReservaBean() {
    }

    /**
     * 
     */
    private int idReserva;

    /**
     * 
     */
    private int idHorario;

    /**
     * 
     */
    private int idSolicitud;

    /**
     * 
     */
    private java.time.LocalDate fechaReserva;

    /**
     * 
     */
    private java.time.LocalTime hora;

    /**
     * 
     */
    private String estado;

    /**
     * @return
     */
    public int getIdReserva() {
        return this.idReserva;
    }

    /**
     * @param idReserva
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * @return
     */
    public int getIdHorario() {
        return this.idHorario;
    }

    /**
     * @param idHorario
     */
    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

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
    public java.time.LocalDate getFechaReserva() {
        return this.fechaReserva;
    }

    /**
     * @param fechaReserva
     */
    public void setFechaReserva(java.time.LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * @return
     */
    public java.time.LocalTime getHora() {
        return this.hora;
    }

    /**
     * @param hora
     */
    public void setHora(java.time.LocalTime hora) {
        this.hora = hora;
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
     * @param idReserva 
     * @param idHorario 
     * @param idSolicitud 
     * @param fechaReserva 
     * @param hora 
     * @param estado
     */
    public ReservaBean(int idReserva, int idHorario, int idSolicitud, java.time.LocalDate fechaReserva, java.time.LocalTime hora, String estado) {
        this.idReserva = idReserva;
        this.idHorario = idHorario;
        this.idSolicitud = idSolicitud;
        this.fechaReserva = fechaReserva;
        this.hora = hora;
        this.estado = estado;
    }

}