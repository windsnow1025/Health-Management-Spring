package com.windsnow1025.javaspringboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCHelper {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3307/" + System.getenv("MYSQL_DATABASE");
    private static final String DATABASE_USER = System.getenv("MYSQL_USER");
    private static final String DATABASE_PASSWORD = System.getenv("MYSQL_PASSWORD");
    private static final int DATABASE_VERSION = 1;

    public Connection connection;

    private static final String CREATE_TABLE_USER = """
            CREATE TABLE IF NOT EXISTS user (
                phone_number VARCHAR(255) PRIMARY KEY,
                username VARCHAR(255),
                password VARCHAR(255),
                email VARCHAR(255),
                birthday DATE,
                sex VARCHAR(255),
                is_login VARCHAR(255),
                is_multipled VARCHAR(255),
                is_deleted VARCHAR(255)
            );
            """;

    private static final String CREATE_TABLE_HISTORY = """
    CREATE TABLE IF NOT EXISTS history (
        ID INT AUTO_INCREMENT PRIMARY KEY,
        phone_number VARCHAR(255),
        history_No INT,
        history_date DATE,
        history_place VARCHAR(255),
        history_doctor VARCHAR(255),
        history_organ VARCHAR(255),
        symptom BLOB,
        conclusion BLOB,
        suggestion BLOB,
        is_deleted VARCHAR(255)
    );
    """;

    private static final String CREATE_TABLE_REPORT = """
    CREATE TABLE IF NOT EXISTS report (
        ID INT AUTO_INCREMENT PRIMARY KEY,
        phone_number VARCHAR(255),
        report_No INT,
        report_content BLOB,
        report_picture BLOB,
        report_type VARCHAR(255),
        report_date DATE,
        report_place VARCHAR(255),
        is_deleted VARCHAR(255)
    );
    """;

    private static final String CREATE_TABLE_ALERT = """
    CREATE TABLE IF NOT EXISTS alert (
        ID INT AUTO_INCREMENT PRIMARY KEY,
        phone_number VARCHAR(255),
        alert_No VARCHAR(255),
        type_No INT,
        type VARCHAR(255),
        content BLOB,
        title VARCHAR(255),
        date DATE,
        cycle VARCHAR(255),
        is_medicine VARCHAR(255),
        is_deleted VARCHAR(255)
    );
    """;

    public JDBCHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            int currentVersion = getCurrentDatabaseVersion();
            if (currentVersion == 0) {
                onCreate(connection);
            } else if (currentVersion < DATABASE_VERSION) {
                onUpgrade(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void onCreate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_HISTORY);
            statement.executeUpdate(CREATE_TABLE_REPORT);
            statement.executeUpdate(CREATE_TABLE_ALERT);
        }
    }

    public void onUpgrade(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
        }
    }

    private int getCurrentDatabaseVersion() {
        return 0;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}