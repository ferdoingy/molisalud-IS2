package molisalud.reserva.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Paciente. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class PacienteBean {

    /**
     * Default constructor
     */
    public PacienteBean() {
    }

    /**
     * 
     */
    private int idPaciente;

    /**
     * 
     */
    private String dni;

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
    private String telefono;

    /**
     * 
     */
    private String correo;

    /**
     * 
     */
    private String direccion;

    /**
     * @return
     */
    public int getIdPaciente() {
        return this.idPaciente;
    }

    /**
     * @param idPaciente
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * @return
     */
    public String getDni() {
        return this.dni;
    }

    /**
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
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
    public String getTelefono() {
        return this.telefono;
    }

    /**
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return
     */
    public String getCorreo() {
        return this.correo;
    }

    /**
     * @param correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return
     */
    public String getDireccion() {
        return this.direccion;
    }

    /**
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param idPaciente 
     * @param dni 
     * @param nombres 
     * @param apellidos 
     * @param telefono 
     * @param correo 
     * @param direccion
     */
    public PacienteBean(int idPaciente, String dni, String nombres, String apellidos, String telefono, String correo, String direccion) {
        this.idPaciente = idPaciente;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

}