package molisalud.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import molisalud.login.beans.UsuarioBean;
import molisalud.util.ConexionBD;

/**
 * Persistencia/consulta de Usuario para autenticación
 */
public class LoginDAO {

    /**
     * Default constructor
     */
    public LoginDAO() {
    }

    /**
     * Conexión JDBC. Preferir una variable local por operación y cerrar con try-with-resources; evitar compartir conexiones entre solicitudes.
     */
    private java.sql.Connection cn;

    /**
     * Valida nombreUsuario y clave; devuelve el UsuarioBean autenticado (con su rol) o null si las credenciales no son válidas.
     * @param nombreUsuario
     * @param clave
     * @return
     */
    public UsuarioBean iniciarSesion(String nombreUsuario, String clave) {
        String sql = "SELECT idUsuario, nombreUsuario, rol FROM Usuario "
                   + "WHERE nombreUsuario = ? AND \"contraseña\" = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // La clave no se vuelve a guardar en el bean que viaja a la sesión.
                    return new UsuarioBean(rs.getInt("idUsuario"), rs.getString("nombreUsuario"), null, rs.getString("rol"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el usuario", e);
        }
    }
}
