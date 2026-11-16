package molisalud.facturacion.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import molisalud.facturacion.beans.FacturacionBean;
import molisalud.facturacion.beans.PagoBean;
import molisalud.facturacion.beans.TarifarioBean;

/**
 * Implementa IFacturacionService. Valida los datos, completa fechas y delega al DAO.
 * Los errores de regla se informan con IllegalArgumentException / IllegalStateException (mensaje apto para el usuario).
 */
public class FacturacionService implements IFacturacionService {

    /**
     * Default constructor
     */
    public FacturacionService() {
    }

    private molisalud.facturacion.dao.FacturacionDAO objDAO = new molisalud.facturacion.dao.FacturacionDAO();

    private static final ZoneId ZONA = ZoneId.of("America/Lima");
    private static final List<String> METODOS = Arrays.asList("Efectivo", "Tarjeta (POS)", "Transferencia");

    private static void validarMetodo(String metodoPago) {
        if (metodoPago == null || !METODOS.contains(metodoPago)) {
            throw new IllegalArgumentException("Método de pago no válido. Use: Efectivo, Tarjeta (POS) o Transferencia.");
        }
    }

    private static void validarCitaYServicio(int idCita, int idServicio) {
        if (idCita <= 0) {
            throw new IllegalArgumentException("Indique la cita que se facturará.");
        }
        if (idServicio <= 0) {
            throw new IllegalArgumentException("Indique el servicio del tarifario.");
        }
    }

    public ArrayList<TarifarioBean> getTarifario() {
        return objDAO.getTarifario();
    }

    /** Pago + comprobante en una sola transacción, con el precio del tarifario. */
    public void facturarAtencion(int idCita, int idServicio, String metodoPago) {
        validarCitaYServicio(idCita, idServicio);
        validarMetodo(metodoPago);
        objDAO.facturarAtencion(idCita, idServicio, metodoPago);
    }

    /** Registra solo el pago (fecha y hora actuales; el monto sale del tarifario). */
    public void registrarPago(PagoBean p) {
        if (p == null) {
            throw new IllegalArgumentException("Faltan los datos del pago.");
        }
        validarCitaYServicio(p.getIdCita(), p.getIdServicio());
        validarMetodo(p.getMetodoPago());
        p.setFechaPago(LocalDateTime.now(ZONA));
        objDAO.registrarPago(p);
    }

    /** Emite el comprobante de un pago existente (fecha de hoy; importes desde el pago). */
    public void emitirComprobante(FacturacionBean c) {
        if (c == null || c.getIdPago() <= 0) {
            throw new IllegalArgumentException("Indique el pago del comprobante.");
        }
        c.setFechaEmision(LocalDate.now(ZONA));
        objDAO.emitirComprobante(c);
    }

    /** Inserta un pago con los importes recibidos (monto obligatorio y mayor que cero). */
    public void agregarPago(PagoBean p) {
        if (p == null) {
            throw new IllegalArgumentException("Faltan los datos del pago.");
        }
        validarCitaYServicio(p.getIdCita(), p.getIdServicio());
        validarMetodo(p.getMetodoPago());
        if (p.getMonto() == null || p.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        if (p.getFechaPago() == null) {
            p.setFechaPago(LocalDateTime.now(ZONA));
        }
        objDAO.agregarPago(p);
    }

    /** Inserta un comprobante con los importes recibidos (0 < subtotal <= total). */
    public void agregarComprobante(FacturacionBean c) {
        if (c == null || c.getIdPago() <= 0) {
            throw new IllegalArgumentException("Indique el pago del comprobante.");
        }
        if (c.getSubtotal() == null || c.getTotal() == null
                || c.getSubtotal().compareTo(BigDecimal.ZERO) <= 0 || c.getSubtotal().compareTo(c.getTotal()) > 0) {
            throw new IllegalArgumentException("Los importes deben cumplir: 0 < subtotal ≤ total.");
        }
        if (c.getFechaEmision() == null) {
            c.setFechaEmision(LocalDate.now(ZONA));
        }
        objDAO.agregarComprobante(c);
    }
}
