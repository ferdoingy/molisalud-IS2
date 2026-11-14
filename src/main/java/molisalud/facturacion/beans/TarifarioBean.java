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
        // TODO implement here
        return 0;
    }

    /**
     * @param idServicio
     */
    public void setIdServicio(int idServicio) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getNombreServicio() {
        // TODO implement here
        return "";
    }

    /**
     * @param nombreServicio
     */
    public void setNombreServicio(String nombreServicio) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getPrecio() {
        // TODO implement here
        return null;
    }

    /**
     * @param precio
     */
    public void setPrecio(java.math.BigDecimal precio) {
        // TODO implement here
    }

    /**
     * @param idServicio 
     * @param nombreServicio 
     * @param precio
     */
    public TarifarioBean(int idServicio, String nombreServicio, java.math.BigDecimal precio) {
        // TODO implement here
    }

}