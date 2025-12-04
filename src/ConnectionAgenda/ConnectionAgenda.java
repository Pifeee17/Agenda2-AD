package ConnectionAgenda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionAgenda {

    private static final String URL = "jdbc:sqlite:database/Agenda2.db";
    private static final String USER = "root";
    private static final String PASS = "";

    // Evita que alguien instancie esta clase
    private ConnectionAgenda() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
