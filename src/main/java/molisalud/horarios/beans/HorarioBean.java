package molisalud.horarios.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Horario. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class HorarioBean {

    /**
     * Default constructor
     */
    public HorarioBean() {
    }

    /**
     * 
     */
    private int idHorario;

    /**
     * 
     */
    private int idMedico;

    /**
     * 
     */
    private String diaSemana;

    /**
     * 
     */
    private java.time.LocalTime horaInicio;

    /**
     * 
     */
    private java.time.LocalTime horaFin;

    /**
     * 
     */
    private String estado;

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
    public int getIdMedico() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idMedico
     */
    public void setIdMedico(int idMedico) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getDiaSemana() {
        // TODO implement here
        return "";
    }

    /**
     * @param diaSemana
     */
    public void setDiaSemana(String diaSemana) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraInicio() {
        // TODO implement here
        return null;
    }

    /**
     * @param horaInicio
     */
    public void setHoraInicio(java.time.LocalTime horaInicio) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraFin() {
        // TODO implement here
        return null;
    }

    /**
     * @param horaFin
     */
    public void setHoraFin(java.time.LocalTime horaFin) {
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
     * @param idHorario 
     * @param idMedico 
     * @param diaSemana 
     * @param horaInicio 
     * @param horaFin 
     * @param estado
     */
    public HorarioBean(int idHorario, int idMedico, String diaSemana, java.time.LocalTime horaInicio, java.time.LocalTime horaFin, String estado) {
        // TODO implement here
    }

}