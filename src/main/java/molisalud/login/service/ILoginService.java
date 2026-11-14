package molisalud.login.service;

import java.io.*;
import java.util.*;

/**
 * Contrato del servicio Login. Operaciones públicas sin estado ni referencia al DAO. Los retornos void se representan sin parámetro de retorno por compatibilidad con el generador Java.
 */
public interface ILoginService {

    /**
     * @param nombreUsuario 
     * @param clave 
     * @return
     */
    public molisalud.login.beans.UsuarioBean iniciarSesion(String nombreUsuario, String clave);

}