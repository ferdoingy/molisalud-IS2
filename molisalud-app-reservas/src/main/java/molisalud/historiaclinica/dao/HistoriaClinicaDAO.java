package molisalud.historiaclinica.dao;

import java.io.*;
import java.util.*;

/**
 * 
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

    /**
     * @param idPaciente 
     * @return
     */
    public molisalud.historiaclinica.beans.HistoriaClinicaBean getHistoria(int idPaciente) {
        // TODO implement here
        return null;
    }

    /**
     * @param h
     */
    public void agregarHistoria(molisalud.historiaclinica.beans.HistoriaClinicaBean h) {
        // TODO implement here
    }

    /**
     * @param t
     */
    public void registrarTriaje(molisalud.historiaclinica.beans.TriajeBean t) {
        // TODO implement here
    }

    /**
     * @param idHistoria 
     * @return
     */
    public java.util.ArrayList<molisalud.historiaclinica.beans.TriajeBean> getTriajes(int idHistoria) {
        // TODO implement here
        return null;
    }

    /**
     * @param c
     */
    public void registrarConsulta(molisalud.historiaclinica.beans.ConsultaBean c) {
        // TODO implement here
    }

    /**
     * @param r
     */
    public void emitirReceta(molisalud.historiaclinica.beans.RecetaBean r) {
        // TODO implement here
    }

    /**
     * @param diagnostico
     */
    public void registrarDiagnostico(molisalud.historiaclinica.beans.DiagnosticoBean diagnostico) {
        // TODO implement here
    }

}