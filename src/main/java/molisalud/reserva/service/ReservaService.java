package molisalud.reserva.service;

import java.io.*;
import java.util.*;

/**
 * Implementa IReservaService. Las operaciones del contrato están definidas en la interfaz y la extensión Java genera sus implementaciones aquí. Mantener objDAO solo en esta clase. Implementar validaciones y delegación al DAO.
 */
public class ReservaService implements IReservaService {

    /**
     * Default constructor
     */
    public ReservaService() {
    }

    /**
     * 
     */
    private molisalud.reserva.dao.ReservaDAO objDAO = new molisalud.reserva.dao.ReservaDAO();

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