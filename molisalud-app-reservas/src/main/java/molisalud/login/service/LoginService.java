package molisalud.login.service;

import molisalud.login.beans.UsuarioBean;

/**
 * Implementa ILoginService. Valida los datos recibidos y delega al DAO.
 */
public class LoginService implements ILoginService {

    /**
     * Default constructor
     */
    public LoginService() {
    }

    private molisalud.login.dao.LoginDAO objDAO = new molisalud.login.dao.LoginDAO();

    /**
     * @param nombreUsuario
     * @param clave
     * @return UsuarioBean autenticado, o null si faltan datos o las credenciales no son válidas
     */
    public UsuarioBean iniciarSesion(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || clave == null || clave.isEmpty()) {
            return null;
        }
        return objDAO.iniciarSesion(nombreUsuario.trim(), clave);
    }
}
