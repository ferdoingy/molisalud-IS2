package molisalud.reserva.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import molisalud.horarios.beans.HorarioBean;
import molisalud.reserva.beans.PacienteBean;
import molisalud.reserva.beans.ReservaBean;
import molisalud.reserva.beans.SolicitudCitaBean;

/**
 * Implementa IReservaService. Valida los datos, aplica las reglas del módulo y delega al DAO.
 * Los errores de regla se informan con IllegalArgumentException / IllegalStateException (mensaje apto para el usuario).
 */
public class ReservaService implements IReservaService {

    /**
     * Default constructor
     */
    public ReservaService() {
    }

    private molisalud.reserva.dao.ReservaDAO objDAO = new molisalud.reserva.dao.ReservaDAO();

    private static final ZoneId ZONA = ZoneId.of("America/Lima");
    private static final Pattern DNI = Pattern.compile("\\d{8}");
    private static final Pattern CORREO = Pattern.compile("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    private static final Pattern TELEFONO = Pattern.compile("[0-9+\\-() ]{6,20}");
    private static final List<String> ESTADOS_CITA = Arrays.asList("Pendiente", "Confirmada", "Cancelada", "Atendida");

    private static String limpiar(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private void validarPaciente(PacienteBean p) {
        if (p == null) {
            throw new IllegalArgumentException("Faltan los datos del paciente.");
        }
        p.setDni(limpiar(p.getDni()));
        p.setNombres(limpiar(p.getNombres()));
        p.setApellidos(limpiar(p.getApellidos()));
        p.setTelefono(limpiar(p.getTelefono()));
        p.setCorreo(limpiar(p.getCorreo()));
        p.setDireccion(limpiar(p.getDireccion()));
        if (p.getDni() == null || !DNI.matcher(p.getDni()).matches()) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos.");
        }
        if (p.getNombres() == null || p.getApellidos() == null) {
            throw new IllegalArgumentException("Los nombres y apellidos son obligatorios.");
        }
        if (p.getCorreo() != null && !CORREO.matcher(p.getCorreo()).matches()) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
        if (p.getTelefono() != null && !TELEFONO.matcher(p.getTelefono()).matches()) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
    }

    public ArrayList<ReservaBean> getReservas() {
        return objDAO.getReservas();
    }

    public void registrarPaciente(PacienteBean p) {
        validarPaciente(p);
        objDAO.registrarPaciente(p);
    }

    public void actualizarPaciente(PacienteBean p) {
        validarPaciente(p);
        if (p.getIdPaciente() <= 0) {
            throw new IllegalArgumentException("Indique el paciente que se actualizará.");
        }
        objDAO.actualizarPaciente(p);
    }

    /** Solo horarios Publicados del médico. */
    public ArrayList<HorarioBean> consultarDisponibilidad(int idMedico) {
        if (idMedico <= 0) {
            throw new IllegalArgumentException("Indique un médico válido.");
        }
        return objDAO.consultarDisponibilidad(idMedico);
    }

    /** Crea la cita en estado "Pendiente" (el DAO verifica horario, hora y cruces en una transacción). */
    public void registrarReserva(ReservaBean r) {
        if (r == null || r.getIdHorario() <= 0) {
            throw new IllegalArgumentException("Indique el horario del médico.");
        }
        if (r.getIdSolicitud() <= 0) {
            throw new IllegalArgumentException("Indique la solicitud de cita.");
        }
        if (r.getFechaReserva() == null || r.getHora() == null) {
            throw new IllegalArgumentException("Indique la fecha y la hora de la cita.");
        }
        if (r.getFechaReserva().isBefore(LocalDate.now(ZONA))) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser anterior a hoy.");
        }
        r.setEstado("Pendiente");
        objDAO.registrarReserva(r);
    }

    public void confirmarReserva(int idReserva) {
        if (idReserva <= 0) {
            throw new IllegalArgumentException("Indique la cita que se confirmará.");
        }
        objDAO.confirmarReserva(idReserva);
    }

    public void actualizarEstadoReserva(int idReserva, String estado) {
        if (idReserva <= 0) {
            throw new IllegalArgumentException("Indique la cita.");
        }
        if (estado == null || !ESTADOS_CITA.contains(estado)) {
            throw new IllegalArgumentException("Estado no válido. Use: Pendiente, Confirmada, Cancelada o Atendida.");
        }
        objDAO.actualizarEstadoReserva(idReserva, estado);
    }

    /** @return la ficha del paciente o null si no existe. */
    public PacienteBean consultarPaciente(String dni) {
        dni = limpiar(dni);
        if (dni == null || !DNI.matcher(dni).matches()) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos.");
        }
        return objDAO.consultarPaciente(dni);
    }

    /** Crea la solicitud en estado "Pendiente" con la fecha de hoy como fecha de solicitud. */
    public void registrarSolicitud(SolicitudCitaBean solicitud) {
        if (solicitud == null || solicitud.getIdPaciente() <= 0) {
            throw new IllegalArgumentException("Indique el paciente de la solicitud.");
        }
        if (solicitud.getFechaEscogida() == null || solicitud.getHoraEscogida() == null) {
            throw new IllegalArgumentException("Indique la fecha y la hora que desea el paciente.");
        }
        LocalDate hoy = LocalDate.now(ZONA);
        if (solicitud.getFechaEscogida().isBefore(hoy)) {
            throw new IllegalArgumentException("La fecha escogida no puede ser anterior a hoy.");
        }
        solicitud.setMotivoConsulta(limpiar(solicitud.getMotivoConsulta()));
        if (solicitud.getMotivoConsulta() == null) {
            throw new IllegalArgumentException("Indique el motivo de la consulta.");
        }
        solicitud.setFechaSolicitud(hoy);
        solicitud.setEstado("Pendiente");
        objDAO.registrarSolicitud(solicitud);
    }
}
