package molisalud.reserva.dao;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ReservaDAO {

    /**
     * Default constructor
     */
    public ReservaDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.reserva.beans.ReservaBean> getReservas() {
        // TODO implement here
        return null;
    }

    /**
     * @param p
     */
    public void registrarPaciente(molisalud.reserva.beans.PacienteBean p) {
        // TODO implement here
    }

    /**
     * @param p
     */
    public void actualizarPaciente(molisalud.reserva.beans.PacienteBean p) {
        // TODO implement here
    }

    /**
     * @param idMedico 
     * @return
     */
    public java.util.ArrayList<molisalud.horarios.beans.HorarioBean> consultarDisponibilidad(int idMedico) {
        // TODO implement here
        return null;
    }

    /**
     * @param r
     */
    public void registrarReserva(molisalud.reserva.beans.ReservaBean r) {
        // TODO implement here
    }

    /**
     * @param idReserva
     */
    public void confirmarReserva(int idReserva) {
        // TODO implement here
    }

    /**
     * @param idReserva 
     * @param estado
     */
    public void actualizarEstadoReserva(int idReserva, String estado) {
        // TODO implement here
    }

    /**
     * @param dni 
     * @return
     */
    public molisalud.reserva.beans.PacienteBean consultarPaciente(String dni) {
        // TODO implement here
        return null;
    }

    /**
     * @param solicitud
     */
    public void registrarSolicitud(molisalud.reserva.beans.SolicitudCitaBean solicitud) {
        // TODO implement here
    }

}