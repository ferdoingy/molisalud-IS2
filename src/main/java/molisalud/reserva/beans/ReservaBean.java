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
        // TODO implement here
        return 0;
    }

    /**
     * @param idReserva
     */
    public void setIdReserva(int idReserva) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdHorario() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idHorario
     */
    public void setIdHorario(int idHorario) {
        // TODO implement here
    }

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
    public java.time.LocalDate getFechaReserva() {
        // TODO implement here
        return null;
    }

    /**
     * @param fechaReserva
     */
    public void setFechaReserva(java.time.LocalDate fechaReserva) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalTime getHora() {
        // TODO implement here
        return null;
    }

    /**
     * @param hora
     */
    public void setHora(java.time.LocalTime hora) {
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
     * @param idReserva 
     * @param idHorario 
     * @param idSolicitud 
     * @param fechaReserva 
     * @param hora 
     * @param estado
     */
    public ReservaBean(int idReserva, int idHorario, int idSolicitud, java.time.LocalDate fechaReserva, java.time.LocalTime hora, String estado) {
        // TODO implement here
    }

}