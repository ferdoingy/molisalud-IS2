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
        return this.idPago;
    }

    /**
     * @param idPago
     */
    public void setIdPago(int idPago) {
        this.idPago = idPago;
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
    public java.time.LocalDateTime getFechaPago() {
        return this.fechaPago;
    }

    /**
     * @param fechaPago
     */
    public void setFechaPago(java.time.LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getMonto() {
        return this.monto;
    }

    /**
     * @param monto
     */
    public void setMonto(java.math.BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return
     */
    public String getMetodoPago() {
        return this.metodoPago;
    }

    /**
     * @param metodoPago
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
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
        this.idPago = idPago;
        this.idCita = idCita;
        this.idServicio = idServicio;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }

}