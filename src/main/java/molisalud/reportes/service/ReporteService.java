package molisalud.reportes.service;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementa IReporteService. Valida el periodo y delega al DAO.
 * Los errores de regla se informan con IllegalArgumentException / IllegalStateException (mensaje apto para el usuario).
 */
public class ReporteService implements IReporteService {

    /**
     * Default constructor
     */
    public ReporteService() {
    }

    private molisalud.reportes.dao.ReporteDAO objDAO = new molisalud.reportes.dao.ReporteDAO();

    private static void validarPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Indique la fecha de inicio y la fecha de fin del periodo.");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
    }

    /** Valida el periodo y comprueba que haya información que reportar. */
    public void generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        validarPeriodo(fechaInicio, fechaFin);
        objDAO.generarReporte(fechaInicio, fechaFin);
    }

    /** @return citas, comprobantes e historias clínicas del periodo (en ese orden). */
    public ArrayList<Object> consultarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        validarPeriodo(fechaInicio, fechaFin);
        return objDAO.consultarReporte(fechaInicio, fechaFin);
    }

    /** Verifica que haya datos válidos que exportar. */
    public void exportarReporte(ArrayList<Object> datos) {
        if (datos == null || datos.isEmpty()) {
            throw new IllegalArgumentException("No hay datos para exportar. Genere primero el reporte de un periodo con información.");
        }
        objDAO.exportarReporte(datos);
    }
}
