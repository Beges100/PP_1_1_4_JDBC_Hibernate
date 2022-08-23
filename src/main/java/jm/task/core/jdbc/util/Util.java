package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    static Connection connection;
    static String url      = "jdbc:mysql://localhost:3306/";
    private static String dbName   = "mydbtest";
    static String userName = "root";
    static String password = "Kostya102938";
    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            connection = DriverManager.getConnection(url + dbName, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
