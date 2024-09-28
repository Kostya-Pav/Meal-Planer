package mealplanner.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/meals_db";
    private final static String USER = "postgres";
    private final static String PASS = "1111";
    private final static int MAX_RETRIES = 100;
    private final static int RETRY_DELAY = 1000;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Can not load Driver", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                return DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException e) {
                attempt++;
                System.err.println("Connection attempt " + attempt + " failed: " + e.getMessage());
                if (attempt >= MAX_RETRIES) {
                    throw new SQLException("Failed to connect to the database after " + MAX_RETRIES + " attempts.", e);
                }
                try {
                    Thread.sleep(1, RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Connection attempt interrupted", ie);
                }
            }
        }
        throw new SQLException("Exhausted all retry attempts without connecting.");
    }
}