import java.sql.*;
import java.util.Random;

public class MySQLConnection {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/usuarios";
    private static final String USER = "root";
    private static final String PASSWORD = "Caldas14";


    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Crear una declaración (statement)
            statement = connection.createStatement();

            // Ejecutar una consulta
            String query = "SELECT * FROM usuarios";
            resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String correoElectronico = resultSet.getString("correo_electronico");
                String llaveCifrado = resultSet.getString("llave_cifrado");
                String llaveDescifrado = resultSet.getString("llave_descifrado");

                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Correo Electrónico: " + correoElectronico);
                System.out.println("Llave de Cifrado: " + llaveCifrado);
                System.out.println("Llave de Descifrado: " + llaveDescifrado);
                System.out.println();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}