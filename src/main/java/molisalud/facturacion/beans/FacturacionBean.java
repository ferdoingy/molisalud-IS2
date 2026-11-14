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
        // TODO implement here
        return 0;
    }

    /**
     * @param idComprobante
     */
    public void setIdComprobante(int idComprobante) {
        // TODO implement here
    }

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
    public java.time.LocalDate getFechaEmision() {
        // TODO implement here
        return null;
    }

    /**
     * @param fechaEmision
     */
    public void setFechaEmision(java.time.LocalDate fechaEmision) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getSubtotal() {
        // TODO implement here
        return null;
    }

    /**
     * @param subtotal
     */
    public void setSubtotal(java.math.BigDecimal subtotal) {
        // TODO implement here
    }

    /**
     * @return
     */
    public java.math.BigDecimal getTotal() {
        // TODO implement here
        return null;
    }

    /**
     * @param total
     */
    public void setTotal(java.math.BigDecimal total) {
        // TODO implement here
    }

    /**
     * @param idComprobante 
     * @param idPago 
     * @param fechaEmision 
     * @param subtotal 
     * @param total
     */
    public FacturacionBean(int idComprobante, int idPago, java.time.LocalDate fechaEmision, java.math.BigDecimal subtotal, java.math.BigDecimal total) {
        // TODO implement here
    }

}