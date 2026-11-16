package molisalud.reportes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import molisalud.facturacion.beans.FacturacionBean;
import molisalud.historiaclinica.beans.HistoriaClinicaBean;
import molisalud.reserva.beans.ReservaBean;
import molisalud.util.ConexionBD;

/**
 * Consulta de datos para los reportes administrativos. Solo lectura: no hay tabla de reportes.
 * El periodo es inclusivo y se aplica a: citas (fechaReserva), comprobantes (fechaEmision) e historias (fecha).
 */
public class ReporteDAO {

    /**
     * Default constructor
     */
    public ReporteDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /**
     * Consolida (cuenta) citas, comprobantes e historias del periodo y exige que exista al menos un registro.
     * @param fechaInicio
     * @param fechaFin
     */
    public void generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = "SELECT (SELECT count(*) FROM CitaMedica WHERE fechaReserva BETWEEN ? AND ?) "
                   + "     + (SELECT count(*) FROM ComprobantePago WHERE fechaEmision BETWEEN ? AND ?) "
                   + "     + (SELECT count(*) FROM HistoriaClinica WHERE fecha BETWEEN ? AND ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < 3; i++) {
                ps.setDate(2 * i + 1, Date.valueOf(fechaInicio));
                ps.setDate(2 * i + 2, Date.valueOf(fechaFin));
            }
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getLong(1) == 0) {
                    throw new IllegalStateException("No hay citas, ingresos ni historias clínicas en el periodo indicado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar el reporte", e);
        }
    }

    /**
     * @return primero los ReservaBean (citas), luego los FacturacionBean (comprobantes) y al final los HistoriaClinicaBean
     *         del periodo, cada grupo ordenado por fecha.
     */
    public ArrayList<Object> consultarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        ArrayList<Object> datos = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT idReserva, idHorario, idSolicitud, fechaReserva, hora, estado FROM CitaMedica "
                  + "WHERE fechaReserva BETWEEN ? AND ? ORDER BY fechaReserva, hora, idReserva")) {
                ps.setDate(1, Date.valueOf(fechaInicio));
                ps.setDate(2, Date.valueOf(fechaFin));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ReservaBean r = new ReservaBean();
                        r.setIdReserva(rs.getInt("idReserva"));
                        r.setIdHorario(rs.getInt("idHorario"));
                        r.setIdSolicitud(rs.getInt("idSolicitud"));
                        r.setFechaReserva(rs.getDate("fechaReserva").toLocalDate());
                        r.setHora(rs.getTime("hora").toLocalTime());
                        r.setEstado(rs.getString("estado"));
                        datos.add(r);
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT idComprobante, idPago, fechaEmision, subtotal, total FROM ComprobantePago "
                  + "WHERE fechaEmision BETWEEN ? AND ? ORDER BY fechaEmision, idComprobante")) {
                ps.setDate(1, Date.valueOf(fechaInicio));
                ps.setDate(2, Date.valueOf(fechaFin));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        FacturacionBean c = new FacturacionBean();
                        c.setIdComprobante(rs.getInt("idComprobante"));
                        c.setIdPago(rs.getInt("idPago"));
                        c.setFechaEmision(rs.getDate("fechaEmision").toLocalDate());
                        c.setSubtotal(rs.getBigDecimal("subtotal"));
                        c.setTotal(rs.getBigDecimal("total"));
                        datos.add(c);
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT idHistoria, idPaciente, fecha, antecedentes FROM HistoriaClinica "
                  + "WHERE fecha BETWEEN ? AND ? ORDER BY fecha, idHistoria")) {
                ps.setDate(1, Date.valueOf(fechaInicio));
                ps.setDate(2, Date.valueOf(fechaFin));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HistoriaClinicaBean h = new HistoriaClinicaBean();
                        h.setIdHistoria(rs.getInt("idHistoria"));
                        h.setIdPaciente(rs.getInt("idPaciente"));
                        h.setFecha(rs.getDate("fecha").toLocalDate());
                        h.setAntecedentes(rs.getString("antecedentes"));
                        datos.add(h);
                    }
                }
            }
            return datos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el reporte", e);
        }
    }

    /**
     * Comprueba que los datos a exportar sean los que consolida consultarReporte
     * (cada elemento debe ser un ReservaBean, FacturacionBean o HistoriaClinicaBean).
     * @param datos
     */
    public void exportarReporte(ArrayList<Object> datos) {
        for (Object o : datos) {
            if (!(o instanceof ReservaBean || o instanceof FacturacionBean || o instanceof HistoriaClinicaBean)) {
                throw new IllegalArgumentException("Los datos a exportar no corresponden a un reporte válido.");
            }
        }
    }
}
