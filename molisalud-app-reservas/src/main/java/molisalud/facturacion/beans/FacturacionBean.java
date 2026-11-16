package molisalud.facturacion.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Facturacion. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class FacturacionBean {

    /**
     * Default constructor
     */
    public FacturacionBean() {
    }

    /**
     * 
     */
    private int idComprobante;

    /**
     * 
     */
    private int idPago;

    /**
     * 
     */
    private java.time.LocalDate fechaEmision;

    /**
     * 
     */
    private java.math.BigDecimal subtotal;

    /**
     * 
     */
    private java.math.BigDecimal total;

    /**
     * @return
     */
    public int getIdComprobante() {
        return this.idComprobante;
    }

    /**
     * @param idComprobante
     */
    public void setIdComprobante(int idComprobante) {
        this.idComprobante = idComprobante;
    }

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
    public java.time.LocalDate getFechaEmision() {
        return this.fechaEmision;
    }

    /**
     * @param fechaEmision
     */
    public void setFechaEmision(java.time.LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getSubtotal() {
        return this.subtotal;
    }

    /**
     * @param subtotal
     */
    public void setSubtotal(java.math.BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTotal() {
        return this.total;
    }

    /**
     * @param total
     */
    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    /**
     * @param idComprobante 
     * @param idPago 
     * @param fechaEmision 
     * @param subtotal 
     * @param total
     */
    public FacturacionBean(int idComprobante, int idPago, java.time.LocalDate fechaEmision, java.math.BigDecimal subtotal, java.math.BigDecimal total) {
        this.idComprobante = idComprobante;
        this.idPago = idPago;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.total = total;
    }

}