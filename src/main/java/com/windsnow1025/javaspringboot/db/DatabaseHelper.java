package com.windsnow1025.javaspringboot.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public abstract class DatabaseHelper {
    protected static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    protected static HikariDataSource dataSource;
    protected static String dbUrl;
    protected static String dbUsername;
    protected static String dbPassword;
    protected static String dbDriverClassName;
    protected static String dbVersion;

    public DatabaseHelper() {
        try {
            setDatabaseConfig();

            // Data source
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);
            config.setDriverClassName(dbDriverClassName);
            dataSource = new HikariDataSource(config);

            // Version control
            String currentVersion = selectVersion();
            if (currentVersion == null) {
                onCreate();
            } else if (!currentVersion.equals(dbVersion)) {
                onUpgrade();
            }
        } catch (SQLException e) {
            logger.error("Database connection failed", e);
        }
    }

    protected abstract void setDatabaseConfig();

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // To be overridden
    protected void onCreate() throws SQLException {
        createMetadata();
        insertVersion();
        logger.info("Database created");
    }

    // To be overridden
    protected void onUpgrade() throws SQLException {
        updateVersion();
        logger.info("Database upgraded");
    }

    protected void createMetadata() throws SQLException {
        final String CREATE_METADATA = """
                CREATE TABLE IF NOT EXISTS metadata (
                    version VARCHAR(255)
                );
                """;
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(CREATE_METADATA);
        }
    }

    protected void dropMetadata() throws SQLException {
        final String DROP_METADATA = """
                DROP TABLE IF EXISTS metadata
                """;
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(DROP_METADATA);
        }
    }

    protected String selectVersion() {
        final String SELECT_METADATA = "SELECT version FROM metadata";
        try (Statement statement = getConnection().createStatement();
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

    protected void insertVersion() throws SQLException {
        final String INSERT_METADATA = """
                INSERT INTO metadata (version) VALUES (?)
                """;
        try (PreparedStatement insertStatement = getConnection().prepareStatement(INSERT_METADATA)) {
            insertStatement.setString(1, dbVersion);
            insertStatement.executeUpdate();
        }
    }

    protected void updateVersion() throws SQLException {
        final String UPDATE_METADATA = """
                UPDATE metadata SET version = ?
                """;
        try (PreparedStatement updateStatement = getConnection().prepareStatement(UPDATE_METADATA)) {
            updateStatement.setString(1, dbVersion);
            updateStatement.executeUpdate();
        }
    }
}