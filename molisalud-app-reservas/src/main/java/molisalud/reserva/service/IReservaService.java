package molisalud.reserva.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio Reserva. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface IReservaService {

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.reserva.beans.ReservaBean> getReservas();

    /**
     * @param p
     */
    public void registrarPaciente(molisalud.reserva.beans.PacienteBean p);

    /**
     * @param p
     */
    public void actualizarPaciente(molisalud.reserva.beans.PacienteBean p);

    /**
     * @param idMedico 
     * @return
     */
    public java.util.ArrayList<molisalud.horarios.beans.HorarioBean> consultarDisponibilidad(int idMedico);

    /**
     * @param r
     */
    public void registrarReserva(molisalud.reserva.beans.ReservaBean r);

    /**
     * @param idReserva
     */
    public void confirmarReserva(int idReserva);

    /**
     * @param idReserva 
     * @param estado
     */
    public void actualizarEstadoReserva(int idReserva, String estado);

    /**
     * @param dni 
     * @return
     */
    public molisalud.reserva.beans.PacienteBean consultarPaciente(String dni);

    /**
     * @param solicitud
     */
    public void registrarSolicitud(molisalud.reserva.beans.SolicitudCitaBean solicitud);

}