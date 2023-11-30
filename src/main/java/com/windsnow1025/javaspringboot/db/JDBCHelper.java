package com.windsnow1025.javaspringboot.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JDBCHelper {
    private static final Logger logger = LoggerFactory.getLogger(JDBCHelper.class);
    private static final String DATABASE_URL = "jdbc:mysql://learn-mysql:3306/" + System.getenv("MYSQL_DATABASE");
    private static final String DATABASE_USER = System.getenv("MYSQL_USER");
    private static final String DATABASE_PASSWORD = System.getenv("MYSQL_PASSWORD");
    private static final String DATABASE_VERSION = "1.3";

    private Connection connection;

    private static final String CREATE_TABLE_METADATA = """
            CREATE TABLE IF NOT EXISTS metadata (
                version VARCHAR(255)
            );
            """;


    private static final String CREATE_TABLE_USER = """
            CREATE TABLE IF NOT EXISTS user (
                phone_number VARCHAR(255) PRIMARY KEY,
                username VARCHAR(255),
                password VARCHAR(255),
                birthday DATE,
                sex VARCHAR(255)
            );
            """;

    // 病例记录
    // organ 是器官
    private static final String CREATE_TABLE_RECORD = """
            CREATE TABLE IF NOT EXISTS record (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                phone_number VARCHAR(255),
                record_date DATE,
                hospital VARCHAR(255),
                doctor VARCHAR(255),
                organ VARCHAR(255),
                symptom TEXT,
                conclusion TEXT,
                suggestion TEXT,
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;

    // 体检报告
    // report_type 是全检 / 部分检查
    private static final String CREATE_TABLE_REPORT = """
            CREATE TABLE IF NOT EXISTS report (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                phone_number VARCHAR(255),
                report_date DATE,
                hospital VARCHAR(255),
                report_type VARCHAR(255),
                picture TEXT,
                detail TEXT,
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;

    // 吃药提醒/就诊提醒 取决于 is_medicine
    // alert_type 在record里是organ，在report里是report_type
    private static final String CREATE_TABLE_ALERT = """
            CREATE TABLE IF NOT EXISTS alert (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                phone_number VARCHAR(255),
                alert_type VARCHAR(255),
                advice TEXT,
                title VARCHAR(255),
                alert_date DATE,
                alert_cycle VARCHAR(255),
                is_medicine VARCHAR(255),
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;

    private static final String INSERT_METADATA = """
            INSERT INTO metadata (version) VALUES (0)
            """;

    public JDBCHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            String currentVersion = getDatabaseVersionFromMetadata();
            if (currentVersion == null) {
                onCreate(connection); // Initialize database version to 0
                setDatabaseVersionInMetadata();
            } else if (!currentVersion.equals(DATABASE_VERSION)) {
                onUpgrade(connection);
                setDatabaseVersionInMetadata();
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Database connection failed", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public void onCreate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_METADATA);
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_RECORD);
            statement.executeUpdate(CREATE_TABLE_REPORT);
            statement.executeUpdate(CREATE_TABLE_ALERT);
            statement.executeUpdate(INSERT_METADATA);
        }
        logger.info("Database created");
    }

    // Change this function for each new version
    public void onUpgrade(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Drop all tables
            statement.executeUpdate("DROP TABLE IF EXISTS metadata");
            statement.executeUpdate("DROP TABLE IF EXISTS history");
            statement.executeUpdate("DROP TABLE IF EXISTS record");
            statement.executeUpdate("DROP TABLE IF EXISTS report");
            statement.executeUpdate("DROP TABLE IF EXISTS alert");
            statement.executeUpdate("DROP TABLE IF EXISTS user");

            // Recreate all tables
            onCreate(connection);
        }
        logger.info("Database upgraded");
    }

    private String getDatabaseVersionFromMetadata() {
        final String SELECT_METADATA = "SELECT version FROM metadata";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_METADATA)) {
            if (resultSet.next()) {
                return resultSet.getString("version");
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private void setDatabaseVersionInMetadata() throws SQLException {
        final String UPDATE_METADATA = """
                UPDATE metadata SET version = ?
                """;
        try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_METADATA)) {
            updateStatement.setString(1, DATABASE_VERSION);
            updateStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}