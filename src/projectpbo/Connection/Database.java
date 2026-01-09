/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author JAGAD
 */
public class Database {
    private static final String URL = "jdbc:mysql://0.tcp.ap.ngrok.io:14653/projectpbo";
    private static final String USER = "brogrammers";
    private static final String PASS = "brobro123";
    public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);

    }
}
//127.0.0.1