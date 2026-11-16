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
    public int getIdMedico() {
        return this.idMedico;
    }

    /**
     * @param idMedico
     */
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * @return
     */
    public String getDiaSemana() {
        return this.diaSemana;
    }

    /**
     * @param diaSemana
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    /**
     * @param horaInicio
     */
    public void setHoraInicio(java.time.LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return
     */
    public java.time.LocalTime getHoraFin() {
        return this.horaFin;
    }

    /**
     * @param horaFin
     */
    public void setHoraFin(java.time.LocalTime horaFin) {
        this.horaFin = horaFin;
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
     * @param idHorario 
     * @param idMedico 
     * @param diaSemana 
     * @param horaInicio 
     * @param horaFin 
     * @param estado
     */
    public HorarioBean(int idHorario, int idMedico, String diaSemana, java.time.LocalTime horaInicio, java.time.LocalTime horaFin, String estado) {
        this.idHorario = idHorario;
        this.idMedico = idMedico;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

}