package org.openjfx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // JDBC‑URLen för Supabase‑databasen
    private static final String URL = "jdbc:postgresql://aws-0-eu-north-1.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.uuswoanwqocqhppfqsfc";
    private static final String PASSWORD = "Jesper1235!";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}