package molisalud.horarios.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import molisalud.horarios.beans.HorarioBean;

/**
 * Implementa IHorarioService. Valida los datos, aplica las reglas del módulo y delega al DAO.
 * Los errores de regla se informan con IllegalArgumentException / IllegalStateException (mensaje apto para el usuario).
 */
public class HorarioService implements IHorarioService {

    /**
     * Default constructor
     */
    public HorarioService() {
    }

    private molisalud.horarios.dao.HorarioDAO objDAO = new molisalud.horarios.dao.HorarioDAO();

    private static final List<String> DIAS = Arrays.asList(
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");

    private void validarDatos(HorarioBean h) {
        if (h == null) {
            throw new IllegalArgumentException("Faltan los datos del horario.");
        }
        if (h.getIdMedico() <= 0) {
            throw new IllegalArgumentException("Indique un médico válido.");
        }
        if (h.getDiaSemana() == null || !DIAS.contains(h.getDiaSemana())) {
            throw new IllegalArgumentException("Indique un día de la semana válido.");
        }
        if (h.getHoraInicio() == null || h.getHoraFin() == null) {
            throw new IllegalArgumentException("Indique la hora de inicio y la hora de fin.");
        }
        if (!h.getHoraInicio().isBefore(h.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
        }
    }

    public ArrayList<HorarioBean> getHorarios() {
        return objDAO.getHorarios();
    }

    public ArrayList<HorarioBean> consultarDisponibilidad(int idMedico) {
        if (idMedico <= 0) {
            throw new IllegalArgumentException("Indique un médico válido.");
        }
        return objDAO.consultarDisponibilidad(idMedico);
    }

    /** Registra un horario nuevo en estado "Disponible". */
    public void registrarDisponibilidad(HorarioBean h) {
        validarDatos(h);
        h.setIdHorario(0);
        if (validarSuperposicion(h)) {
            throw new IllegalStateException("El médico ya tiene un horario que se cruza con ese día y rango de horas.");
        }
        h.setEstado("Disponible");
        objDAO.registrarDisponibilidad(h);
    }

    /** Disponible -> Asignado. */
    public void asignarTurno(HorarioBean h) {
        if (h == null || h.getIdHorario() <= 0) {
            throw new IllegalArgumentException("Indique el horario al que se asignará el turno.");
        }
        objDAO.asignarTurno(h);
    }

    /** Cambia día y horas, verificando que no se cruce con otro horario del mismo médico. */
    public void actualizarHorario(HorarioBean h) {
        validarDatos(h);
        if (h.getIdHorario() <= 0) {
            throw new IllegalArgumentException("Indique el horario que se actualizará.");
        }
        if (validarSuperposicion(h)) {
            throw new IllegalStateException("El cambio se cruza con otro horario del mismo médico.");
        }
        objDAO.actualizarHorario(h);
    }

    /** Asignado -> Publicado. */
    public void publicarHorario(HorarioBean h) {
        if (h == null || h.getIdHorario() <= 0) {
            throw new IllegalArgumentException("Indique el horario que se publicará.");
        }
        objDAO.publicarHorario(h);
    }

    /** @return true si ya existe un horario del mismo médico que se cruza con h. */
    public boolean validarSuperposicion(HorarioBean h) {
        validarDatos(h);
        return objDAO.validarSuperposicion(h);
    }
}
