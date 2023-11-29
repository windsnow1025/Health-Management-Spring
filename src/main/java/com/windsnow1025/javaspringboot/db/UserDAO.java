package com.windsnow1025.javaspringboot.db;

import com.windsnow1025.javaspringboot.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String LOGIN_QUERY = """
            SELECT phone_number, username, sex, birthday FROM user 
            WHERE phone_number = ? AND password = ?
            """;

    private static final String SIGNUP_QUERY = """
            INSERT INTO user (phone_number, password, sex, birthday) VALUES (?, ?, ?, ?)
            """;

    private JDBCHelper jdbcHelper;

    public UserDAO(JDBCHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
    }

    public User signin(String phoneNumber, String password) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY)) {
            statement.setString(1, phoneNumber);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String phone_number = resultSet.getString("phone_number");
                String username = resultSet.getString("username");
                String sex = resultSet.getString("sex");
                String birthday = resultSet.getString("birthday");
                return new User(phone_number, username, sex, birthday);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Login failed", e);
        }
    }

    public boolean signup(String phoneNumber, String password, String sex, String birthday) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(SIGNUP_QUERY)) {
            statement.setString(1, phoneNumber);
            statement.setString(2, password);
            statement.setString(3, sex);
            statement.setString(4, birthday);
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Signup failed", e);
        }
    }
}
