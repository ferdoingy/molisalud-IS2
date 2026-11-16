package molisalud.horarios.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio Horario. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface IHorarioService {

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.horarios.beans.HorarioBean> getHorarios();

    /**
     * @param idMedico 
     * @return
     */
    public java.util.ArrayList<molisalud.horarios.beans.HorarioBean> consultarDisponibilidad(int idMedico);

    /**
     * @param h
     */
    public void registrarDisponibilidad(molisalud.horarios.beans.HorarioBean h);

    /**
     * @param h
     */
    public void asignarTurno(molisalud.horarios.beans.HorarioBean h);

    /**
     * @param h
     */
    public void actualizarHorario(molisalud.horarios.beans.HorarioBean h);

    /**
     * @param h
     */
    public void publicarHorario(molisalud.horarios.beans.HorarioBean h);

    /**
     * @param h 
     * @return
     */
    public boolean validarSuperposicion(molisalud.horarios.beans.HorarioBean h);

}