package com.windsnow1025.javaspringboot.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String LOGIN_QUERY = """
            SELECT phone_number, username FROM user 
            WHERE phone_number = ? AND password = ?
            """;

    private JDBCHelper jdbcHelper;

    public UserDAO(JDBCHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
    }

    public User login(String phoneNumber, String password) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY)) {
            statement.setString(1, phoneNumber);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getString("phone_number"), resultSet.getString("username"));
            }
            return null; // User not found or password mismatch
        } catch (SQLException e) {
            throw new RuntimeException("Login failed", e);
        }
    }

    public static class User {
        private String phoneNumber;
        private String username;

        public User(String phoneNumber, String username) {
            this.phoneNumber = phoneNumber;
            this.username = username;
        }

        // Getters and setters
        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
