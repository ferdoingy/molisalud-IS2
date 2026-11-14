package molisalud.facturacion.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Pago. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class PagoBean {

    /**
     * Default constructor
     */
    public PagoBean() {
    }

    /**
     * 
     */
    private int idPago;

    /**
     * 
     */
    private int idCita;

    /**
     * 
     */
    private int idServicio;

    /**
     * 
     */
    private java.time.LocalDateTime fechaPago;

    /**
     * 
     */
    private java.math.BigDecimal monto;

    /**
     * 
     */
    private String metodoPago;

    /**
     * @return
     */
    public int getIdPago() {
        // TODO implement here
        return 0;
    }

    /**
     * @param idPago
     */
    public void setIdPago(int idPago) {
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
    public java.time.LocalDateTime getFechaPago() {
        // TODO implement here
        return null;
    }

    /**
     * @param fechaPago
     */
    public void setFechaPago(java.time.LocalDateTime fechaPago) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getMonto() {
        // TODO implement here
        return null;
    }

    /**
     * @param monto
     */
    public void setMonto(java.math.BigDecimal monto) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getMetodoPago() {
        // TODO implement here
        return "";
    }

    /**
     * @param metodoPago
     */
    public void setMetodoPago(String metodoPago) {
        // TODO implement here
    }

    /**
     * @param idPago 
     * @param idCita 
     * @param idServicio 
     * @param fechaPago 
     * @param monto 
     * @param metodoPago
     */
    public PagoBean(int idPago, int idCita, int idServicio, java.time.LocalDateTime fechaPago, java.math.BigDecimal monto, String metodoPago) {
        // TODO implement here
    }

}