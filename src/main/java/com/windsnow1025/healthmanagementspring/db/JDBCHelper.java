package com.windsnow1025.healthmanagementspring.db;

import java.sql.*;

public class JDBCHelper extends DatabaseHelper {

    private static final String CREATE_TABLE_USER = """
            CREATE TABLE IF NOT EXISTS user (
                phone_number VARCHAR(255),
                username VARCHAR(255),
                password VARCHAR(255),
                birthday VARCHAR(255),
                sex VARCHAR(255),
                PRIMARY KEY (phone_number)
            );
            """;

    // 病例记录
    // organ 是器官
    private static final String CREATE_TABLE_RECORD = """
            CREATE TABLE IF NOT EXISTS record (
                id INT,
                phone_number VARCHAR(255),
                record_date VARCHAR(255),
                hospital VARCHAR(255),
                doctor VARCHAR(255),
                organ VARCHAR(255),
                symptom TEXT,
                conclusion TEXT,
                suggestion TEXT,
                PRIMARY KEY (id, phone_number),
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;

    // 体检报告
    // report_type 是全检 / 部分检查
    private static final String CREATE_TABLE_REPORT = """
            CREATE TABLE IF NOT EXISTS report (
                id INT,
                phone_number VARCHAR(255),
                report_date VARCHAR(255),
                hospital VARCHAR(255),
                report_type VARCHAR(255),
                picture BLOB,
                detail TEXT,
                PRIMARY KEY (id, phone_number),
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;

    // 吃药提醒/就诊提醒 取决于 is_medicine
    // alert_type 在record里是organ，在report里是report_type
    private static final String CREATE_TABLE_ALERT = """
            CREATE TABLE IF NOT EXISTS alert (
                id INT AUTO_INCREMENT,
                record_id INT,
                report_id INT,
                phone_number VARCHAR(255),
                alert_type VARCHAR(255),
                advice TEXT,
                title VARCHAR(255),
                alert_date VARCHAR(255),
                alert_cycle VARCHAR(255),
                is_medicine VARCHAR(255),
                PRIMARY KEY (id),
                FOREIGN KEY (phone_number) REFERENCES user(phone_number)
            );
            """;


    public JDBCHelper() {
        super();
    }

    @Override
    public void setDatabaseConfig() {
        dbUrl = "jdbc:mysql://learn-android-mysql:3306/" + System.getenv("MYSQL_DATABASE");
        dbUsername = System.getenv("MYSQL_USER");
        dbPassword = System.getenv("MYSQL_PASSWORD");
        dbDriverClassName = "com.mysql.cj.jdbc.Driver";
        dbVersion = "1.8";
    }

    @Override
    public void onCreate() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_RECORD);
            statement.executeUpdate(CREATE_TABLE_REPORT);
            statement.executeUpdate(CREATE_TABLE_ALERT);
        }

        createMetadata();
        insertVersion();
        logger.info("Database created");
    }

    @Override
    public void onUpgrade() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            // Drop all
            statement.executeUpdate("DROP TABLE IF EXISTS alert");
            statement.executeUpdate("DROP TABLE IF EXISTS record");
            statement.executeUpdate("DROP TABLE IF EXISTS report");
            statement.executeUpdate("DROP TABLE IF EXISTS user");

            // Create all
            statement.executeUpdate(CREATE_TABLE_USER);
            statement.executeUpdate(CREATE_TABLE_RECORD);
            statement.executeUpdate(CREATE_TABLE_REPORT);
            statement.executeUpdate(CREATE_TABLE_ALERT);
        }

        updateVersion();
        logger.info("Database upgraded");
    }

}