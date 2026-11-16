package molisalud.facturacion.service;

import java.io.*;
import java.util.*;

/**
 * Implementa IFacturacionService. Las operaciones del contrato están definidas en la interfaz y la extensión Java genera sus implementaciones aquí. Mantener objDAO solo en esta clase. Implementar validaciones y delegación al DAO.
 */
public class FacturacionService implements IFacturacionService {

    /**
     * Default constructor
     */
    public FacturacionService() {
    }

    /**
     * 
     */
    private molisalud.facturacion.dao.FacturacionDAO objDAO = new molisalud.facturacion.dao.FacturacionDAO();

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