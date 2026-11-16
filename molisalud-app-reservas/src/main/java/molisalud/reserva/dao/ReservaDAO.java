package molisalud.reserva.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import molisalud.horarios.beans.HorarioBean;
import molisalud.reserva.beans.PacienteBean;
import molisalud.reserva.beans.ReservaBean;
import molisalud.reserva.beans.SolicitudCitaBean;
import molisalud.util.ConexionBD;

/**
 * Persistencia del módulo de reserva: FichaPaciente, SolicitudCita y CitaMedica.
 */
public class ReservaDAO {

    /**
     * Default constructor
     */
    public ReservaDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /** Duración fija de cada cita, en minutos. */
    public static final int DURACION_CITA_MIN = 30;

    private static final String[] DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    private static String nombreDia(LocalDate fecha) {
        DayOfWeek d = fecha.getDayOfWeek(); // MONDAY=1 ... SUNDAY=7
        return DIAS[d.getValue() - 1];
    }

    // ------------------------------------------------------------------ pacientes

    private PacienteBean mapearPaciente(ResultSet rs) throws SQLException {
        PacienteBean p = new PacienteBean();
        p.setIdPaciente(rs.getInt("idPaciente"));
        p.setDni(rs.getString("dni"));
        p.setNombres(rs.getString("nombres"));
        p.setApellidos(rs.getString("apellidos"));
        p.setTelefono(rs.getString("telefono"));
        p.setCorreo(rs.getString("correo"));
        p.setDireccion(rs.getString("direccion"));
        return p;
    }

    /** Inserta la ficha y deja el idPaciente generado en el bean. */
    public void registrarPaciente(PacienteBean p) {
        String sql = "INSERT INTO FichaPaciente (dni, nombres, apellidos, telefono, correo, direccion) "
                   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING idPaciente";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombres());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getDireccion());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setIdPaciente(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // violación de UNIQUE (dni)
                throw new IllegalStateException("Ya existe un paciente con ese DNI.");
            }
            throw new RuntimeException("Error al registrar el paciente", e);
        }
    }

    public void actualizarPaciente(PacienteBean p) {
        String sql = "UPDATE FichaPaciente SET dni = ?, nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ? "
                   + "WHERE idPaciente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombres());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getDireccion());
            ps.setInt(7, p.getIdPaciente());
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("El paciente indicado no existe.");
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new IllegalStateException("Ya existe otro paciente con ese DNI.");
            }
            throw new RuntimeException("Error al actualizar el paciente", e);
        }
    }

    /** @return la ficha del paciente con ese DNI, o null si no existe. */
    public PacienteBean consultarPaciente(String dni) {
        String sql = "SELECT idPaciente, dni, nombres, apellidos, telefono, correo, direccion FROM FichaPaciente WHERE dni = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearPaciente(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el paciente", e);
        }
    }

    // ------------------------------------------------------------------ solicitudes

    /** Inserta la solicitud y deja el idSolicitud generado en el bean. */
    public void registrarSolicitud(SolicitudCitaBean s) {
        String sql = "INSERT INTO SolicitudCita (idPaciente, fechaSolicitud, fechaEscogida, horaEscogida, motivoConsulta, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING idSolicitud";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdPaciente());
            ps.setDate(2, Date.valueOf(s.getFechaSolicitud()));
            ps.setDate(3, Date.valueOf(s.getFechaEscogida()));
            ps.setTime(4, Time.valueOf(s.getHoraEscogida()));
            ps.setString(5, s.getMotivoConsulta());
            ps.setString(6, s.getEstado());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s.setIdSolicitud(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) { // violación de clave foránea (idPaciente)
                throw new IllegalArgumentException("El paciente indicado no existe.");
            }
            throw new RuntimeException("Error al registrar la solicitud", e);
        }
    }

    // ------------------------------------------------------------------ disponibilidad

    /** @return los horarios PUBLICADOS del médico. */
    public ArrayList<HorarioBean> consultarDisponibilidad(int idMedico) {
        String sql = "SELECT idHorario, idMedico, diaSemana, horaInicio, horaFin, estado FROM HorarioMedico "
                   + "WHERE idMedico = ? AND estado = 'Publicado' ORDER BY idHorario";
        ArrayList<HorarioBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HorarioBean h = new HorarioBean();
                    h.setIdHorario(rs.getInt("idHorario"));
                    h.setIdMedico(rs.getInt("idMedico"));
                    h.setDiaSemana(rs.getString("diaSemana"));
                    h.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
                    h.setHoraFin(rs.getTime("horaFin").toLocalTime());
                    h.setEstado(rs.getString("estado"));
                    lista.add(h);
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la disponibilidad", e);
        }
    }

    // ------------------------------------------------------------------ reservas

    private ReservaBean mapearReserva(ResultSet rs) throws SQLException {
        ReservaBean r = new ReservaBean();
        r.setIdReserva(rs.getInt("idReserva"));
        r.setIdHorario(rs.getInt("idHorario"));
        r.setIdSolicitud(rs.getInt("idSolicitud"));
        r.setFechaReserva(rs.getDate("fechaReserva").toLocalDate());
        r.setHora(rs.getTime("hora").toLocalTime());
        r.setEstado(rs.getString("estado"));
        return r;
    }

    public ArrayList<ReservaBean> getReservas() {
        String sql = "SELECT idReserva, idHorario, idSolicitud, fechaReserva, hora, estado FROM CitaMedica "
                   + "ORDER BY fechaReserva, hora, idReserva";
        ArrayList<ReservaBean> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las reservas", e);
        }
    }

    /**
     * Registra la cita en una sola transacción: valida horario publicado, día y rango de horas, solicitud pendiente
     * y que no se cruce con otra cita activa del mismo horario; luego marca la solicitud como "Reservada".
     * Deja el idReserva generado en el bean.
     */
    public void registrarReserva(ReservaBean r) {
        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false);
            try {
                // 1) horario (bloqueado para que dos reservas simultáneas no se pisen)
                String diaHorario;
                LocalTime inicio, fin;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT diaSemana, horaInicio, horaFin, estado FROM HorarioMedico WHERE idHorario = ? FOR UPDATE")) {
                    ps.setInt(1, r.getIdHorario());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException("El horario indicado no existe.");
                        }
                        if (!"Publicado".equals(rs.getString("estado"))) {
                            throw new IllegalStateException("El horario indicado no está publicado.");
                        }
                        diaHorario = rs.getString("diaSemana");
                        inicio = rs.getTime("horaInicio").toLocalTime();
                        fin = rs.getTime("horaFin").toLocalTime();
                    }
                }
                if (!nombreDia(r.getFechaReserva()).equals(diaHorario)) {
                    throw new IllegalArgumentException("La fecha elegida cae " + nombreDia(r.getFechaReserva())
                            + " y ese horario es de " + diaHorario + ".");
                }
                LocalTime horaFinCita = r.getHora().plusMinutes(DURACION_CITA_MIN);
                if (r.getHora().isBefore(inicio) || !horaFinCita.isAfter(r.getHora()) || horaFinCita.isAfter(fin)) {
                    throw new IllegalArgumentException("La cita (" + DURACION_CITA_MIN + " min) debe quedar dentro del horario del médico ("
                            + inicio + " a " + fin + ").");
                }

                // 2) solicitud pendiente
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT estado FROM SolicitudCita WHERE idSolicitud = ? FOR UPDATE")) {
                    ps.setInt(1, r.getIdSolicitud());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException("La solicitud indicada no existe.");
                        }
                        if (!"Pendiente".equals(rs.getString("estado"))) {
                            throw new IllegalStateException("La solicitud ya tiene una cita registrada.");
                        }
                    }
                }

                // 3) sin cruce con otra cita activa del mismo horario y fecha
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT 1 FROM CitaMedica WHERE idHorario = ? AND fechaReserva = ? AND estado <> 'Cancelada' "
                      + "AND hora < ? AND hora + (? * INTERVAL '1 minute') > ? LIMIT 1")) {
                    ps.setInt(1, r.getIdHorario());
                    ps.setDate(2, Date.valueOf(r.getFechaReserva()));
                    ps.setTime(3, Time.valueOf(horaFinCita));
                    ps.setInt(4, DURACION_CITA_MIN);
                    ps.setTime(5, Time.valueOf(r.getHora()));
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            throw new IllegalStateException("Ese médico ya tiene una cita que se cruza con esa hora.");
                        }
                    }
                }

                // 4) insertar la cita y marcar la solicitud
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO CitaMedica (idHorario, idSolicitud, fechaReserva, hora, estado) "
                      + "VALUES (?, ?, ?, ?, ?) RETURNING idReserva")) {
                    ps.setInt(1, r.getIdHorario());
                    ps.setInt(2, r.getIdSolicitud());
                    ps.setDate(3, Date.valueOf(r.getFechaReserva()));
                    ps.setTime(4, Time.valueOf(r.getHora()));
                    ps.setString(5, r.getEstado());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            r.setIdReserva(rs.getInt(1));
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE SolicitudCita SET estado = 'Reservada' WHERE idSolicitud = ?")) {
                    ps.setInt(1, r.getIdSolicitud());
                    ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new IllegalStateException("Esa solicitud ya tiene una cita registrada.");
            }
            throw new RuntimeException("Error al registrar la reserva", e);
        }
    }

    /** Pendiente -> Confirmada. */
    public void confirmarReserva(int idReserva) {
        String sql = "UPDATE CitaMedica SET estado = 'Confirmada' WHERE idReserva = ? AND estado = 'Pendiente'";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            if (ps.executeUpdate() == 0) {
                throw new IllegalStateException("Solo se puede confirmar una cita que exista y esté Pendiente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al confirmar la reserva", e);
        }
    }

    /** Cambia el estado salvo que la cita ya esté Cancelada o Atendida (estados finales). */
    public void actualizarEstadoReserva(int idReserva, String estado) {
        String sql = "UPDATE CitaMedica SET estado = ? WHERE idReserva = ? AND estado NOT IN ('Cancelada', 'Atendida')";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idReserva);
            if (ps.executeUpdate() == 0) {
                throw new IllegalStateException("La cita no existe o ya está Cancelada/Atendida y no se puede modificar.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado de la reserva", e);
        }
    }
}
