package molisalud.facturacion.dao;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class FacturacionDAO {

    /**
     * Default constructor
     */
    public FacturacionDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.facturacion.beans.TarifarioBean> getTarifario() {
        // TODO implement here
        return null;
    }

    /**
     * Facturar la cita con el servicio seleccionado del tarifario; registrar pago y comprobante en una transacción. No es integración con SUNAT ni pasarela de pagos.
     * @param idCita 
     * @param idServicio 
     * @param metodoPago
     */
    public void facturarAtencion(int idCita, int idServicio, String metodoPago) {
        // TODO implement here
    }

    /**
     * @param p
     */
    public void registrarPago(molisalud.facturacion.beans.PagoBean p) {
        // TODO implement here
    }

    /**
     * @param c
     */
    public void emitirComprobante(molisalud.facturacion.beans.FacturacionBean c) {
        // TODO implement here
    }

    /**
     * @param p
     */
    public void agregarPago(molisalud.facturacion.beans.PagoBean p) {
        // TODO implement here
    }

    /**
     * @param c
     */
    public void agregarComprobante(molisalud.facturacion.beans.FacturacionBean c) {
        // TODO implement here
    }

}