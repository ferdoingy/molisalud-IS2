package molisalud.historiaclinica.service;

import java.io.*;
import java.util.*;

/**
 * Implementa IHistoriaClinicaService. Las operaciones del contrato están definidas en la interfaz y la extensión Java genera sus implementaciones aquí. Mantener objDAO solo en esta clase. Implementar validaciones y delegación al DAO.
 */
public class HistoriaClinicaService implements IHistoriaClinicaService {

    /**
     * Default constructor
     */
    public HistoriaClinicaService() {
    }

    /**
     * 
     */
    private molisalud.historiaclinica.dao.HistoriaClinicaDAO objDAO = new molisalud.historiaclinica.dao.HistoriaClinicaDAO();

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