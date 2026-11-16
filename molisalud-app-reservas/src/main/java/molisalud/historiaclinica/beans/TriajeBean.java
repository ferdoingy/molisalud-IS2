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
        return this.idTriaje;
    }

    /**
     * @param idTriaje
     */
    public void setIdTriaje(int idTriaje) {
        this.idTriaje = idTriaje;
    }

    /**
     * @return
     */
    public int getIdHistoria() {
        return this.idHistoria;
    }

    /**
     * @param idHistoria
     */
    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }

    /**
     * @return
     */
    public int getIdCita() {
        return this.idCita;
    }

    /**
     * @param idCita
     */
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    /**
     * @return
     */
    public java.time.LocalDateTime getFecha() {
        return this.fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(java.time.LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTemperatura() {
        return this.temperatura;
    }

    /**
     * @param temperatura
     */
    public void setTemperatura(java.math.BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getPeso() {
        return this.peso;
    }

    /**
     * @param peso
     */
    public void setPeso(java.math.BigDecimal peso) {
        this.peso = peso;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTalla() {
        return this.talla;
    }

    /**
     * @param talla
     */
    public void setTalla(java.math.BigDecimal talla) {
        this.talla = talla;
    }

    /**
     * @return
     */
    public String getPresionArterial() {
        return this.presionArterial;
    }

    /**
     * @param presionArterial
     */
    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
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
        this.idTriaje = idTriaje;
        this.idHistoria = idHistoria;
        this.idCita = idCita;
        this.fecha = fecha;
        this.temperatura = temperatura;
        this.peso = peso;
        this.talla = talla;
        this.presionArterial = presionArterial;
    }

}