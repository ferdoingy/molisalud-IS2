package molisalud.historiaclinica.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Triaje. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class TriajeBean {

    /**
     * Default constructor
     */
    public TriajeBean() {
    }

    /**
     * 
     */
    private int idTriaje;

    /**
     * 
     */
    private int idHistoria;

    /**
     * 
     */
    private int idCita;

    /**
     * 
     */
    private java.time.LocalDateTime fecha;

    /**
     * 
     */
    private java.math.BigDecimal temperatura;

    /**
     * 
     */
    private java.math.BigDecimal peso;

    /**
     * 
     */
    private java.math.BigDecimal talla;

    /**
     * 
     */
    private String presionArterial;

    /**
     * @return
     */
    public int getIdTriaje() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idTriaje
     */
    public void setIdTriaje(int idTriaje) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdHistoria() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idHistoria
     */
    public void setIdHistoria(int idHistoria) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getIdCita() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idCita
     */
    public void setIdCita(int idCita) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.time.LocalDateTime getFecha() {
        // TODO implement here
        return null;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDateTime fecha) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTemperatura() {
        // TODO implement here
        return null;
    }

    /**
     * @param temperatura
     */
    public void setTemperatura(java.math.BigDecimal temperatura) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getPeso() {
        // TODO implement here
        return null;
    }

    /**
     * @param peso
     */
    public void setPeso(java.math.BigDecimal peso) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTalla() {
        // TODO implement here
        return null;
    }

    /**
     * @param talla
     */
    public void setTalla(java.math.BigDecimal talla) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getPresionArterial() {
        // TODO implement here
        return "";
    }

    /**
     * @param presionArterial
     */
    public void setPresionArterial(String presionArterial) {
        // TODO implement here
    }

    /**
     * @param idTriaje 
     * @param idHistoria 
     * @param idCita 
     * @param fecha 
     * @param temperatura 
     * @param peso 
     * @param talla 
     * @param presionArterial
     */
    public TriajeBean(int idTriaje, int idHistoria, int idCita, java.time.LocalDateTime fecha, java.math.BigDecimal temperatura, java.math.BigDecimal peso, java.math.BigDecimal talla, String presionArterial) {
        // TODO implement here
    }

}