package molisalud.facturacion.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio Facturacion. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface IFacturacionService {

    /**
     * @return
     */
    public java.util.ArrayList<molisalud.facturacion.beans.TarifarioBean> getTarifario();

    /**
     * Facturar la cita con el servicio seleccionado del tarifario; registrar pago y comprobante en una transacción. No es integración con SUNAT ni pasarela de pagos.
     * @param idCita 
     * @param idServicio 
     * @param metodoPago
     */
    public void facturarAtencion(int idCita, int idServicio, String metodoPago);

    /**
     * @param p
     */
    public void registrarPago(molisalud.facturacion.beans.PagoBean p);

    /**
     * @param c
     */
    public void emitirComprobante(molisalud.facturacion.beans.FacturacionBean c);

    /**
     * @param p
     */
    public void agregarPago(molisalud.facturacion.beans.PagoBean p);

    /**
     * @param c
     */
    public void agregarComprobante(molisalud.facturacion.beans.FacturacionBean c);

}