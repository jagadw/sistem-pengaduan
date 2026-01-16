package projectpbo.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://127.0.0.1/projectpbo";
    private static final String USER = "root";
    private static final String PASS = "";
    public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);

    }
}
//127.0.0.1