package molisalud.facturacion.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import molisalud.facturacion.beans.FacturacionBean;
import molisalud.facturacion.beans.PagoBean;
import molisalud.facturacion.beans.TarifarioBean;
import molisalud.util.ConexionBD;

/**
 * Persistencia de TarifarioServicio, Pago y ComprobantePago.
 * El precio del tarifario ya incluye IGV: total = precio y subtotal = precio / 1.18 (base sin IGV).
 * No hay integración con SUNAT ni con pasarelas de pago.
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

    private static final ZoneId ZONA = ZoneId.of("America/Lima");
    private static final BigDecimal FACTOR_IGV = new BigDecimal("1.18");

    /** @return {subtotal, total} a partir del monto que paga el paciente (IGV incluido). */
    private static BigDecimal[] desglose(BigDecimal monto) {
        BigDecimal total = monto.setScale(2, RoundingMode.HALF_UP);
        BigDecimal subtotal = total.divide(FACTOR_IGV, 2, RoundingMode.HALF_UP);
        return new BigDecimal[] {subtotal, total};
    }

    // ------------------------------------------------------------------ tarifario

    public ArrayList<TarifarioBean> getTarifario() {
        String sql = "SELECT idServicio, nombreServicio, precio FROM TarifarioServicio ORDER BY idServicio";
        ArrayList<TarifarioBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TarifarioBean t = new TarifarioBean();
                t.setIdServicio(rs.getInt("idServicio"));
                t.setNombreServicio(rs.getString("nombreServicio"));
                t.setPrecio(rs.getBigDecimal("precio"));
                lista.add(t);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el tarifario", e);
        }
    }

    // ------------------------------------------------------------------ apoyo (misma conexión/transacción)

    /** Bloquea la cita y exige que exista y esté Atendida. */
    private void exigirCitaAtendida(Connection con, int idCita) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT estado FROM CitaMedica WHERE idReserva = ? FOR UPDATE")) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("La cita indicada no existe.");
                }
                String estado = rs.getString("estado");
                if (!"Atendida".equals(estado)) {
                    throw new IllegalStateException("La cita debe estar Atendida para facturarla (estado actual: " + estado + ").");
                }
            }
        }
    }

    private BigDecimal precioServicio(Connection con, int idServicio) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT precio FROM TarifarioServicio WHERE idServicio = ?")) {
            ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("El servicio indicado no existe en el tarifario.");
                }
                return rs.getBigDecimal(1);
            }
        }
    }

    private void insertarPago(Connection con, PagoBean p) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Pago (idCita, idServicio, fechaPago, monto, metodoPago) VALUES (?, ?, ?, ?, ?) RETURNING idPago")) {
            ps.setInt(1, p.getIdCita());
            ps.setInt(2, p.getIdServicio());
            ps.setTimestamp(3, Timestamp.valueOf(p.getFechaPago()));
            ps.setBigDecimal(4, p.getMonto());
            ps.setString(5, p.getMetodoPago());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setIdPago(rs.getInt(1));
                }
            }
        }
    }

    private void insertarComprobante(Connection con, FacturacionBean c) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ComprobantePago (idPago, fechaEmision, subtotal, total) VALUES (?, ?, ?, ?) RETURNING idComprobante")) {
            ps.setInt(1, c.getIdPago());
            ps.setDate(2, Date.valueOf(c.getFechaEmision()));
            ps.setBigDecimal(3, c.getSubtotal());
            ps.setBigDecimal(4, c.getTotal());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setIdComprobante(rs.getInt(1));
                }
            }
        }
    }

    private RuntimeException errorPago(SQLException e, String operacion) {
        if ("23505".equals(e.getSQLState())) { // UNIQUE idCita
            return new IllegalStateException("Esa cita ya tiene un pago registrado.");
        }
        if ("23503".equals(e.getSQLState())) { // FK cita / servicio
            return new IllegalArgumentException("La cita o el servicio indicado no existe.");
        }
        return new RuntimeException("Error al " + operacion, e);
    }

    private RuntimeException errorComprobante(SQLException e, String operacion) {
        if ("23505".equals(e.getSQLState())) { // UNIQUE idPago
            return new IllegalStateException("Ese pago ya tiene un comprobante emitido.");
        }
        if ("23503".equals(e.getSQLState())) { // FK pago
            return new IllegalArgumentException("El pago indicado no existe.");
        }
        return new RuntimeException("Error al " + operacion, e);
    }

    // ------------------------------------------------------------------ operaciones

    /**
     * Factura la atención en UNA transacción: valida la cita (Atendida), toma el precio del tarifario,
     * registra el pago y emite el comprobante. Si algo falla, no queda nada guardado.
     */
    public void facturarAtencion(int idCita, int idServicio, String metodoPago) {
        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false);
            try {
                exigirCitaAtendida(con, idCita);
                BigDecimal precio = precioServicio(con, idServicio);

                PagoBean p = new PagoBean();
                p.setIdCita(idCita);
                p.setIdServicio(idServicio);
                p.setFechaPago(LocalDateTime.now(ZONA));
                p.setMonto(precio);
                p.setMetodoPago(metodoPago);
                insertarPago(con, p);

                BigDecimal[] d = desglose(precio);
                FacturacionBean c = new FacturacionBean();
                c.setIdPago(p.getIdPago());
                c.setFechaEmision(LocalDate.now(ZONA));
                c.setSubtotal(d[0]);
                c.setTotal(d[1]);
                insertarComprobante(con, c);
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw errorPago(e, "facturar la atención");
        }
    }

    /**
     * Registra solo el pago de una cita Atendida; el monto se toma del tarifario (se ignora el del bean).
     * Deja idPago y monto en el bean.
     */
    public void registrarPago(PagoBean p) {
        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false);
            try {
                exigirCitaAtendida(con, p.getIdCita());
                p.setMonto(precioServicio(con, p.getIdServicio()));
                insertarPago(con, p);
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw errorPago(e, "registrar el pago");
        }
    }

    /**
     * Emite el comprobante de un pago existente; subtotal y total se calculan desde el monto del pago.
     * Deja idComprobante, subtotal y total en el bean.
     */
    public void emitirComprobante(FacturacionBean c) {
        try (Connection con = ConexionBD.getConnection()) {
            BigDecimal monto;
            try (PreparedStatement ps = con.prepareStatement("SELECT monto FROM Pago WHERE idPago = ?")) {
                ps.setInt(1, c.getIdPago());
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalArgumentException("El pago indicado no existe.");
                    }
                    monto = rs.getBigDecimal(1);
                }
            }
            BigDecimal[] d = desglose(monto);
            c.setSubtotal(d[0]);
            c.setTotal(d[1]);
            insertarComprobante(con, c);
        } catch (SQLException e) {
            throw errorComprobante(e, "emitir el comprobante");
        }
    }

    /** Inserta el pago tal como viene en el bean (sin consultar el tarifario ni el estado de la cita). */
    public void agregarPago(PagoBean p) {
        try (Connection con = ConexionBD.getConnection()) {
            insertarPago(con, p);
        } catch (SQLException e) {
            throw errorPago(e, "agregar el pago");
        }
    }

    /** Inserta el comprobante tal como viene en el bean (sin recalcular importes). */
    public void agregarComprobante(FacturacionBean c) {
        try (Connection con = ConexionBD.getConnection()) {
            insertarComprobante(con, c);
        } catch (SQLException e) {
            throw errorComprobante(e, "agregar el comprobante");
        }
    }
}
