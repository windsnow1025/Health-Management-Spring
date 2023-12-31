package com.windsnow1025.healthmanagementspring.util;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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


    private static JDBCHelper instance;

    private JDBCHelper() {
        super();
    }

    public static synchronized JDBCHelper getInstance() {
        if (instance == null) {
            instance = new JDBCHelper();
        }
        return instance;
    }

    @Override
    public void setDatabaseConfig() {
        dbDriverClassName = "com.mysql.cj.jdbc.Driver";
        dbVersion = "1.8";

        String schemaName = System.getenv("MYSQL_DATABASE");
        dbUrl = "jdbc:mysql://learn-mysql:3306/" + schemaName;
        dbUsername = System.getenv("MYSQL_USER");
        dbPassword = System.getenv("MYSQL_PASSWORD");

        if (schemaName == null || dbUsername == null || dbPassword == null) {
            try (InputStream inputStream = JDBCHelper.class.getClassLoader().getResourceAsStream("config.json")) {
                String text = new String(inputStream.readAllBytes());
                JSONObject jsonObject = new JSONObject(text);
                dbUrl = jsonObject.getString("database_url");
                dbUsername = jsonObject.getString("database_username");
                dbPassword = jsonObject.getString("database_password");

                logger.info("Using development setting.");
            } catch (IOException e) {
                logger.error("Database config failed", e);
            }
        } else {
            logger.info("Using production setting.");
        }
    }

    @Override
    public void onCreate() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
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
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
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