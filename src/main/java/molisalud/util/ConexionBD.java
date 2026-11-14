package molisalud.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilidad compartida para obtener conexiones JDBC.
 * La configuración viene de variables de entorno (nada se guarda en el código):
 *  - DATABASE_URL  (formato de Render: postgres://usuario:clave@host:5432/base)
 *  - o bien DB_URL (jdbc:postgresql://host:5432/base), DB_USER y DB_PASSWORD (uso local).
 * Cada DAO cierra su conexión con try-with-resources.
 */
public class ConexionBD {

    public ConexionBD() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de PostgreSQL", e);
        }

        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null && !databaseUrl.isBlank()) {
            try {
                URI uri = new URI(databaseUrl);
                String[] userInfo = uri.getUserInfo().split(":", 2);
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath();
                return DriverManager.getConnection(jdbcUrl, userInfo[0], userInfo.length > 1 ? userInfo[1] : "");
            } catch (java.net.URISyntaxException e) {
                throw new SQLException("DATABASE_URL no tiene un formato válido", e);
            }
        }

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");
        if (url == null || url.isBlank()) {
            throw new SQLException("Falta configurar DATABASE_URL (o DB_URL, DB_USER y DB_PASSWORD)");
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
