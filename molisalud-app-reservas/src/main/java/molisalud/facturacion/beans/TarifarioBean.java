package molisalud.facturacion.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Tarifario. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class TarifarioBean {

    /**
     * Default constructor
     */
    public TarifarioBean() {
    }

    /**
     * 
     */
    private int idServicio;

    /**
     * 
     */
    private String nombreServicio;

    /**
     * 
     */
    private java.math.BigDecimal precio;

    /**
     * @return
     */
    public int getIdServicio() {
        return this.idServicio;
    }

    /**
     * @param idServicio
     */
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    /**
     * @return
     */
    public String getNombreServicio() {
        return this.nombreServicio;
    }

    /**
     * @param nombreServicio
     */
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getPrecio() {
        return this.precio;
    }

    /**
     * @param precio
     */
    public void setPrecio(java.math.BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @param idServicio 
     * @param nombreServicio 
     * @param precio
     */
    public TarifarioBean(int idServicio, String nombreServicio, java.math.BigDecimal precio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precio = precio;
    }

}