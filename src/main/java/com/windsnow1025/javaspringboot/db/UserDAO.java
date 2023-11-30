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

    private static final String IS_EXIST_QUERY = """
            SELECT phone_number FROM user 
            WHERE phone_number = ?
            """;

    private static final String SELECT_USER_QUERY = """
            SELECT phone_number, username, sex, birthday FROM user
            WHERE phone_number = ?
            """;

    private static final String UPDATE_PASSWORD_QUERY = """
            UPDATE user SET password = ? WHERE phone_number = ?
            """;

    private static final String UPDATE_USERNAME_QUERY = """
            UPDATE user SET username = ? WHERE phone_number = ?
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

    public boolean isExist(String phoneNumber) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(IS_EXIST_QUERY)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Check failed", e);
        }
    }

    public User selectUser(String phoneNumber) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_QUERY)) {
            statement.setString(1, phoneNumber);
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
            throw new RuntimeException("Select failed", e);
        }
    }

    public boolean updatePassword(String phoneNumber, String password) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_QUERY)) {
            statement.setString(1, password);
            statement.setString(2, phoneNumber);
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

    public boolean updateUsername(String phoneNumber, String username) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERNAME_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, phoneNumber);
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

}
