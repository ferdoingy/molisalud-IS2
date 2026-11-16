package molisalud.horarios.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Medico. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class MedicoBean {

    /**
     * Default constructor
     */
    public MedicoBean() {
    }

    /**
     * 
     */
    private int idMedico;

    /**
     * 
     */
    private String nombres;

    /**
     * 
     */
    private String apellidos;

    /**
     * 
     */
    private String especialidad;

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
    public String getNombres() {
        return this.nombres;
    }

    /**
     * @param nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return
     */
    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return
     */
    public String getEspecialidad() {
        return this.especialidad;
    }

    /**
     * @param especialidad
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @param idMedico 
     * @param nombres 
     * @param apellidos 
     * @param especialidad
     */
    public MedicoBean(int idMedico, String nombres, String apellidos, String especialidad) {
        this.idMedico = idMedico;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

}