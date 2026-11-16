package molisalud.login.beans;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class UsuarioBean {

    /**
     * Default constructor
     */
    public UsuarioBean() {
    }

    /**
     * 
     */
    private int idUsuario;

    /**
     * 
     */
    private String nombreUsuario;

    /**
     * 
     */
    private String clave;

    /**
     * 
     */
    private String rol;

    /**
     * @return
     */
    public int getIdUsuario() {
        return this.idUsuario;
    }

    /**
     * @param idUsuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return
     */
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    /**
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return
     */
    public String getClave() {
        return this.clave;
    }

    /**
     * @param clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return
     */
    public String getRol() {
        return this.rol;
    }

    /**
     * @param rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @param idUsuario 
     * @param nombreUsuario 
     * @param clave 
     * @param rol
     */
    public UsuarioBean(int idUsuario, String nombreUsuario, String clave, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
    }

}