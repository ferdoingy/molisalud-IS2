package molisalud.historiaclinica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import molisalud.historiaclinica.beans.ConsultaBean;
import molisalud.historiaclinica.beans.DiagnosticoBean;
import molisalud.historiaclinica.beans.HistoriaClinicaBean;
import molisalud.historiaclinica.beans.RecetaBean;
import molisalud.historiaclinica.beans.TriajeBean;
import molisalud.util.ConexionBD;

/**
 * Persistencia de HistoriaClinica, FichaTriaje, RegistroConsultaMedica, Diagnostico y Receta.
 */
public class HistoriaClinicaDAO {

    /**
     * Default constructor
     */
    public HistoriaClinicaDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    // ------------------------------------------------------------------ historia clínica

    /** @return la historia del paciente, o null si todavía no tiene. */
    public HistoriaClinicaBean getHistoria(int idPaciente) {
        String sql = "SELECT idHistoria, idPaciente, fecha, antecedentes FROM HistoriaClinica WHERE idPaciente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                HistoriaClinicaBean h = new HistoriaClinicaBean();
                h.setIdHistoria(rs.getInt("idHistoria"));
                h.setIdPaciente(rs.getInt("idPaciente"));
                h.setFecha(rs.getDate("fecha").toLocalDate());
                h.setAntecedentes(rs.getString("antecedentes"));
                return h;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la historia clínica", e);
        }
    }

    /** Inserta la historia y deja el idHistoria generado en el bean. */
    public void agregarHistoria(HistoriaClinicaBean h) {
        String sql = "INSERT INTO HistoriaClinica (idPaciente, fecha, antecedentes) VALUES (?, ?, ?) RETURNING idHistoria";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, h.getIdPaciente());
            ps.setDate(2, Date.valueOf(h.getFecha()));
            ps.setString(3, h.getAntecedentes());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    h.setIdHistoria(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // UNIQUE idPaciente
                throw new IllegalStateException("El paciente ya tiene historia clínica.");
            }
            if ("23503".equals(e.getSQLState())) { // FK idPaciente
                throw new IllegalArgumentException("El paciente indicado no existe.");
            }
            throw new RuntimeException("Error al crear la historia clínica", e);
        }
    }

    // ------------------------------------------------------------------ citas (apoyo a triaje y consulta)

    private static final class CitaInfo {
        int idPaciente;
        String estado;
    }

    /** Lee y bloquea la cita; exige que exista y esté Confirmada. */
    private CitaInfo citaConfirmada(Connection con, int idCita, String accion) throws SQLException {
        String sql = "SELECT c.estado, s.idPaciente FROM CitaMedica c JOIN SolicitudCita s ON s.idSolicitud = c.idSolicitud "
                   + "WHERE c.idReserva = ? FOR UPDATE OF c";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("La cita indicada no existe.");
                }
                CitaInfo info = new CitaInfo();
                info.estado = rs.getString("estado");
                info.idPaciente = rs.getInt("idPaciente");
                if (!"Confirmada".equals(info.estado)) {
                    throw new IllegalStateException("La cita debe estar Confirmada para " + accion
                            + " (estado actual: " + info.estado + ").");
                }
                return info;
            }
        }
    }

    private int historiaDelPaciente(Connection con, int idPaciente) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT idHistoria FROM HistoriaClinica WHERE idPaciente = ?")) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("El paciente de esa cita no tiene historia clínica. Créela primero.");
                }
                return rs.getInt(1);
            }
        }
    }

    // ------------------------------------------------------------------ triaje

    /**
     * Registra el triaje de una cita Confirmada; la historia se obtiene del paciente de la cita.
     * Deja idTriaje e idHistoria en el bean.
     */
    public void registrarTriaje(TriajeBean t) {
        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false);
            try {
                CitaInfo cita = citaConfirmada(con, t.getIdCita(), "registrar el triaje");
                t.setIdHistoria(historiaDelPaciente(con, cita.idPaciente));
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO FichaTriaje (idHistoria, idCita, fecha, temperatura, peso, talla, presionArterial) "
                      + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING idTriaje")) {
                    ps.setInt(1, t.getIdHistoria());
                    ps.setInt(2, t.getIdCita());
                    ps.setTimestamp(3, Timestamp.valueOf(t.getFecha()));
                    ps.setBigDecimal(4, t.getTemperatura());
                    ps.setBigDecimal(5, t.getPeso());
                    ps.setBigDecimal(6, t.getTalla());
                    ps.setString(7, t.getPresionArterial());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            t.setIdTriaje(rs.getInt(1));
                        }
                    }
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // UNIQUE idCita
                throw new IllegalStateException("Esa cita ya tiene un triaje registrado.");
            }
            throw new RuntimeException("Error al registrar el triaje", e);
        }
    }

    public ArrayList<TriajeBean> getTriajes(int idHistoria) {
        String sql = "SELECT idTriaje, idHistoria, idCita, fecha, temperatura, peso, talla, presionArterial "
                   + "FROM FichaTriaje WHERE idHistoria = ? ORDER BY fecha DESC, idTriaje DESC";
        ArrayList<TriajeBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHistoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TriajeBean t = new TriajeBean();
                    t.setIdTriaje(rs.getInt("idTriaje"));
                    t.setIdHistoria(rs.getInt("idHistoria"));
                    t.setIdCita(rs.getInt("idCita"));
                    t.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    t.setTemperatura(rs.getBigDecimal("temperatura"));
                    t.setPeso(rs.getBigDecimal("peso"));
                    t.setTalla(rs.getBigDecimal("talla"));
                    t.setPresionArterial(rs.getString("presionArterial"));
                    lista.add(t);
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los triajes", e);
        }
    }

    // ------------------------------------------------------------------ consulta

    /**
     * Registra la consulta en una transacción: exige cita Confirmada y con triaje, obtiene la historia del paciente,
     * inserta la consulta y pasa la cita a "Atendida". Deja idConsulta e idHistoria en el bean.
     */
    public void registrarConsulta(ConsultaBean c) {
        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false);
            try {
                CitaInfo cita = citaConfirmada(con, c.getIdCita(), "registrar la consulta");
                c.setIdHistoria(historiaDelPaciente(con, cita.idPaciente));
                try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM FichaTriaje WHERE idCita = ?")) {
                    ps.setInt(1, c.getIdCita());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalStateException("La cita todavía no tiene triaje registrado.");
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO RegistroConsultaMedica (idHistoria, idCita, idMedico, fecha, motivoConsulta, observaciones) "
                      + "VALUES (?, ?, ?, ?, ?, ?) RETURNING idConsulta")) {
                    ps.setInt(1, c.getIdHistoria());
                    ps.setInt(2, c.getIdCita());
                    ps.setInt(3, c.getIdMedico());
                    ps.setTimestamp(4, Timestamp.valueOf(c.getFecha()));
                    ps.setString(5, c.getMotivoConsulta());
                    ps.setString(6, c.getObservaciones());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            c.setIdConsulta(rs.getInt(1));
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE CitaMedica SET estado = 'Atendida' WHERE idReserva = ?")) {
                    ps.setInt(1, c.getIdCita());
                    ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // UNIQUE idCita
                throw new IllegalStateException("Esa cita ya tiene una consulta registrada.");
            }
            if ("23503".equals(e.getSQLState())) { // FK idMedico
                throw new IllegalArgumentException("El médico indicado no existe.");
            }
            throw new RuntimeException("Error al registrar la consulta", e);
        }
    }

    // ------------------------------------------------------------------ receta y diagnóstico

    /** Inserta la receta y deja el idReceta generado en el bean. */
    public void emitirReceta(RecetaBean r) {
        String sql = "INSERT INTO Receta (idConsulta, fecha, indicaciones) VALUES (?, ?, ?) RETURNING idReceta";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdConsulta());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setString(3, r.getIndicaciones());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r.setIdReceta(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) {
                throw new IllegalArgumentException("La consulta indicada no existe.");
            }
            throw new RuntimeException("Error al emitir la receta", e);
        }
    }

    /** Inserta el diagnóstico y deja el idDiagnostico generado en el bean. */
    public void registrarDiagnostico(DiagnosticoBean diagnostico) {
        String sql = "INSERT INTO Diagnostico (idConsulta, descripcion) VALUES (?, ?) RETURNING idDiagnostico";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, diagnostico.getIdConsulta());
            ps.setString(2, diagnostico.getDescripcion());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    diagnostico.setIdDiagnostico(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) {
                throw new IllegalArgumentException("La consulta indicada no existe.");
            }
            throw new RuntimeException("Error al registrar el diagnóstico", e);
        }
    }
}
