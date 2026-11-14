package molisalud.horarios.dao;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class HorarioDAO {

    /**
     * Default constructor
     */
    public HorarioDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

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