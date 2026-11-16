package molisalud.horarios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import molisalud.horarios.beans.HorarioBean;
import molisalud.util.ConexionBD;

/**
 * Persistencia de HorarioMedico.
 */
public class HorarioDAO {

    /**
     * Default constructor
     */
    public HorarioDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    private static final String COLUMNAS = "idHorario, idMedico, diaSemana, horaInicio, horaFin, estado";

    private HorarioBean mapear(ResultSet rs) throws SQLException {
        HorarioBean h = new HorarioBean();
        h.setIdHorario(rs.getInt("idHorario"));
        h.setIdMedico(rs.getInt("idMedico"));
        h.setDiaSemana(rs.getString("diaSemana"));
        h.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
        h.setHoraFin(rs.getTime("horaFin").toLocalTime());
        h.setEstado(rs.getString("estado"));
        return h;
    }

    /**
     * @return todos los horarios, ordenados por médico
     */
    public ArrayList<HorarioBean> getHorarios() {
        String sql = "SELECT " + COLUMNAS + " FROM HorarioMedico ORDER BY idMedico, idHorario";
        ArrayList<HorarioBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los horarios", e);
        }
    }

    /**
     * @param idMedico
     * @return los horarios de un médico
     */
    public ArrayList<HorarioBean> consultarDisponibilidad(int idMedico) {
        String sql = "SELECT " + COLUMNAS + " FROM HorarioMedico WHERE idMedico = ? ORDER BY idHorario";
        ArrayList<HorarioBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la disponibilidad", e);
        }
    }

    /**
     * Inserta un horario nuevo con el estado que trae el bean.
     * @param h
     */
    public void registrarDisponibilidad(HorarioBean h) {
        String sql = "INSERT INTO HorarioMedico (idMedico, diaSemana, horaInicio, horaFin, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, h.getIdMedico());
            ps.setString(2, h.getDiaSemana());
            ps.setTime(3, Time.valueOf(h.getHoraInicio()));
            ps.setTime(4, Time.valueOf(h.getHoraFin()));
            ps.setString(5, h.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) { // violación de clave foránea
                throw new IllegalArgumentException("El médico indicado no existe.");
            }
            throw new RuntimeException("Error al registrar la disponibilidad", e);
        }
    }

    /**
     * Pasa un horario de "Disponible" a "Asignado".
     * @param h
     */
    public void asignarTurno(HorarioBean h) {
        cambiarEstado(h.getIdHorario(), "Disponible", "Asignado",
                "Solo se puede asignar turno a un horario en estado Disponible.");
    }

    /**
     * Pasa un horario de "Asignado" a "Publicado".
     * @param h
     */
    public void publicarHorario(HorarioBean h) {
        cambiarEstado(h.getIdHorario(), "Asignado", "Publicado",
                "Solo se puede publicar un horario en estado Asignado.");
    }

    private void cambiarEstado(int idHorario, String estadoActual, String estadoNuevo, String mensajeSiNoAplica) {
        String sql = "UPDATE HorarioMedico SET estado = ? WHERE idHorario = ? AND estado = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estadoNuevo);
            ps.setInt(2, idHorario);
            ps.setString(3, estadoActual);
            if (ps.executeUpdate() == 0) {
                throw new IllegalStateException(mensajeSiNoAplica);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar el estado del horario", e);
        }
    }

    /**
     * Cambia día y horas de un horario existente (el estado no se modifica).
     * @param h
     */
    public void actualizarHorario(HorarioBean h) {
        String sql = "UPDATE HorarioMedico SET diaSemana = ?, horaInicio = ?, horaFin = ? WHERE idHorario = ? AND idMedico = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getDiaSemana());
            ps.setTime(2, Time.valueOf(h.getHoraInicio()));
            ps.setTime(3, Time.valueOf(h.getHoraFin()));
            ps.setInt(4, h.getIdHorario());
            ps.setInt(5, h.getIdMedico());
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("El horario indicado no existe.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el horario", e);
        }
    }

    /**
     * @param h
     * @return true si el médico YA tiene otro horario que se cruza con este (mismo día, rangos que se solapan)
     */
    public boolean validarSuperposicion(HorarioBean h) {
        String sql = "SELECT 1 FROM HorarioMedico WHERE idMedico = ? AND diaSemana = ? AND idHorario <> ? "
                   + "AND horaInicio < ? AND horaFin > ? LIMIT 1";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, h.getIdMedico());
            ps.setString(2, h.getDiaSemana());
            ps.setInt(3, h.getIdHorario());
            ps.setTime(4, Time.valueOf(h.getHoraFin()));
            ps.setTime(5, Time.valueOf(h.getHoraInicio()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al validar la superposición de horarios", e);
        }
    }
}
