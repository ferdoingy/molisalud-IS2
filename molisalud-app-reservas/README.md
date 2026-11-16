# Molisalud
Proyecto Maven (WAR, Java 11, Tomcat 9). Variables de entorno: DATABASE_URL (Render) o DB_URL/DB_USER/DB_PASSWORD (local).
Base de datos: ejecutar molisalud_schema.sql una vez. Usuario de prueba:
INSERT INTO Usuario(nombreUsuario,"contraseña",rol) VALUES ('admin','admin123','Administrador de la clínica');
