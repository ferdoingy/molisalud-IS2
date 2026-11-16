package molisalud.historiaclinica.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio HistoriaClinica. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface IHistoriaClinicaService {

    /**
     * @param idPaciente 
     * @return
     */
    public molisalud.historiaclinica.beans.HistoriaClinicaBean getHistoria(int idPaciente);

    /**
     * @param h
     */
    public void agregarHistoria(molisalud.historiaclinica.beans.HistoriaClinicaBean h);

    /**
     * @param t
     */
    public void registrarTriaje(molisalud.historiaclinica.beans.TriajeBean t);

    /**
     * @param idHistoria 
     * @return
     */
    public java.util.ArrayList<molisalud.historiaclinica.beans.TriajeBean> getTriajes(int idHistoria);

    /**
     * @param c
     */
    public void registrarConsulta(molisalud.historiaclinica.beans.ConsultaBean c);

    /**
     * @param r
     */
    public void emitirReceta(molisalud.historiaclinica.beans.RecetaBean r);

    /**
     * @param diagnostico
     */
    public void registrarDiagnostico(molisalud.historiaclinica.beans.DiagnosticoBean diagnostico);

}