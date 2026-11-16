package molisalud.historiaclinica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.regex.Pattern;

import molisalud.historiaclinica.beans.ConsultaBean;
import molisalud.historiaclinica.beans.DiagnosticoBean;
import molisalud.historiaclinica.beans.HistoriaClinicaBean;
import molisalud.historiaclinica.beans.RecetaBean;
import molisalud.historiaclinica.beans.TriajeBean;

/**
 * Implementa IHistoriaClinicaService. Valida los datos, completa fechas y delega al DAO.
 * Los errores de regla se informan con IllegalArgumentException / IllegalStateException (mensaje apto para el usuario).
 */
public class HistoriaClinicaService implements IHistoriaClinicaService {

    /**
     * Default constructor
     */
    public HistoriaClinicaService() {
    }

    private molisalud.historiaclinica.dao.HistoriaClinicaDAO objDAO = new molisalud.historiaclinica.dao.HistoriaClinicaDAO();

    private static final ZoneId ZONA = ZoneId.of("America/Lima");
    private static final int MAX_TEXTO = 255; // largo de las columnas VARCHAR(255)
    private static final Pattern PRESION = Pattern.compile("\\d{2,3}/\\d{2,3}");

    private static String limpiar(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private static String textoObligatorio(String s, String nombre) {
        s = limpiar(s);
        if (s == null) {
            throw new IllegalArgumentException("Indique " + nombre + ".");
        }
        return textoMaximo(s, nombre);
    }

    private static String textoMaximo(String s, String nombre) {
        if (s != null && s.length() > MAX_TEXTO) {
            throw new IllegalArgumentException("El campo " + nombre + " admite como máximo " + MAX_TEXTO + " caracteres.");
        }
        return s;
    }

    private static void rango(BigDecimal v, double min, double max, String nombre) {
        if (v == null || v.doubleValue() < min || v.doubleValue() > max) {
            throw new IllegalArgumentException(nombre + " debe estar entre " + min + " y " + max + ".");
        }
    }

    /** @return la historia del paciente, o null si todavía no tiene. */
    public HistoriaClinicaBean getHistoria(int idPaciente) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("Indique un paciente válido.");
        }
        return objDAO.getHistoria(idPaciente);
    }

    /** Crea la historia con la fecha de hoy. */
    public void agregarHistoria(HistoriaClinicaBean h) {
        if (h == null || h.getIdPaciente() <= 0) {
            throw new IllegalArgumentException("Indique el paciente de la historia clínica.");
        }
        h.setAntecedentes(textoMaximo(limpiar(h.getAntecedentes()), "antecedentes"));
        h.setFecha(LocalDate.now(ZONA));
        objDAO.agregarHistoria(h);
    }

    /** Registra el triaje con la fecha y hora actuales (la historia se deduce de la cita). */
    public void registrarTriaje(TriajeBean t) {
        if (t == null || t.getIdCita() <= 0) {
            throw new IllegalArgumentException("Indique la cita del triaje.");
        }
        rango(t.getTemperatura(), 30, 45, "La temperatura (°C)");
        rango(t.getPeso(), 0.5, 500, "El peso (kg)");
        rango(t.getTalla(), 20, 250, "La talla (cm)");
        t.setPresionArterial(limpiar(t.getPresionArterial()));
        if (t.getPresionArterial() == null || !PRESION.matcher(t.getPresionArterial()).matches()) {
            throw new IllegalArgumentException("La presión arterial debe tener el formato 120/80.");
        }
        t.setFecha(LocalDateTime.now(ZONA));
        objDAO.registrarTriaje(t);
    }

    public ArrayList<TriajeBean> getTriajes(int idHistoria) {
        if (idHistoria <= 0) {
            throw new IllegalArgumentException("Indique una historia clínica válida.");
        }
        return objDAO.getTriajes(idHistoria);
    }

    /** Registra la consulta con la fecha y hora actuales; la cita pasa a "Atendida". */
    public void registrarConsulta(ConsultaBean c) {
        if (c == null || c.getIdCita() <= 0) {
            throw new IllegalArgumentException("Indique la cita de la consulta.");
        }
        if (c.getIdMedico() <= 0) {
            throw new IllegalArgumentException("Indique un médico válido.");
        }
        c.setMotivoConsulta(textoObligatorio(c.getMotivoConsulta(), "el motivo de la consulta"));
        c.setObservaciones(textoMaximo(limpiar(c.getObservaciones()), "observaciones"));
        c.setFecha(LocalDateTime.now(ZONA));
        objDAO.registrarConsulta(c);
    }

    /** Emite la receta con la fecha de hoy. */
    public void emitirReceta(RecetaBean r) {
        if (r == null || r.getIdConsulta() <= 0) {
            throw new IllegalArgumentException("Indique la consulta de la receta.");
        }
        r.setIndicaciones(textoObligatorio(r.getIndicaciones(), "las indicaciones de la receta"));
        r.setFecha(LocalDate.now(ZONA));
        objDAO.emitirReceta(r);
    }

    public void registrarDiagnostico(DiagnosticoBean diagnostico) {
        if (diagnostico == null || diagnostico.getIdConsulta() <= 0) {
            throw new IllegalArgumentException("Indique la consulta del diagnóstico.");
        }
        diagnostico.setDescripcion(textoObligatorio(diagnostico.getDescripcion(), "la descripción del diagnóstico"));
        objDAO.registrarDiagnostico(diagnostico);
    }
}
