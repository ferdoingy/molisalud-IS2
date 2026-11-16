package molisalud.historiaclinica.beans;

import java.io.*;
import java.util.*;

/**
 * Datos de Diagnostico. Tipos alineados con el modelo de datos. Los accesores y constructores generados requieren implementar sus cuerpos.
 */
public class DiagnosticoBean {

    /**
     * Default constructor
     */
    public DiagnosticoBean() {
    }

    /**
     * 
     */
    private int idDiagnostico;

    /**
     * 
     */
    private int idConsulta;

    /**
     * 
     */
    private String descripcion;

    /**
     * @return
     */
    public int getIdDiagnostico() {
        return this.idDiagnostico;
    }

    /**
     * @param idDiagnostico
     */
    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    /**
     * @return
     */
    public int getIdConsulta() {
        return this.idConsulta;
    }

    /**
     * @param idConsulta
     */
    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    /**
     * @return
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param idDiagnostico 
     * @param idConsulta 
     * @param descripcion
     */
    public DiagnosticoBean(int idDiagnostico, int idConsulta, String descripcion) {
        this.idDiagnostico = idDiagnostico;
        this.idConsulta = idConsulta;
        this.descripcion = descripcion;
    }

}