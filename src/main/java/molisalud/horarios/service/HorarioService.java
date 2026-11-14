package molisalud.horarios.service;

import java.io.*;
import java.util.*;

/**
 * Implementa IHorarioService. Las operaciones del contrato están definidas en la interfaz y la extensión Java genera sus implementaciones aquí. Mantener objDAO solo en esta clase. Implementar validaciones y delegación al DAO.
 */
public class HorarioService implements IHorarioService {

    /**
     * Default constructor
     */
    public HorarioService() {
    }

    /**
     * 
     */
    private molisalud.horarios.dao.HorarioDAO objDAO = new molisalud.horarios.dao.HorarioDAO();

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.horarios.beans.HorarioBean> getHorarios() {
        // TODO implement here
        return null;
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
     * @param h
     */
    public void registrarDisponibilidad(molisalud.horarios.beans.HorarioBean h) {
        // TODO implement here
    }

    /**
     * @param h
     */
    public void asignarTurno(molisalud.horarios.beans.HorarioBean h) {
        // TODO implement here
    }

    /**
     * @param h
     */
    public void actualizarHorario(molisalud.horarios.beans.HorarioBean h) {
        // TODO implement here
    }

    /**
     * @param h
     */
    public void publicarHorario(molisalud.horarios.beans.HorarioBean h) {
        // TODO implement here
    }

    /**
     * @param h 
     * @return
     */
    public boolean validarSuperposicion(molisalud.horarios.beans.HorarioBean h) {
        // TODO implement here
        return false;
    }








}