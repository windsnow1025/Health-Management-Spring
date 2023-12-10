package com.windsnow1025.healthmanagementspring.db;

import com.windsnow1025.healthmanagementspring.model.User;

import java.sql.*;
import java.util.*;

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

    private static final String UPDATE_BIRTHDAY_QUERY = """
            UPDATE user SET birthday = ? WHERE phone_number = ?
            """;

    private JDBCHelper jdbcHelper;

    public UserDAO(JDBCHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
    }

    public User signin(String phoneNumber, String password) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(LOGIN_QUERY, phoneNumber, password);
            if (!results.isEmpty()) {
                Map<String, Object> row = results.get(0);
                return new User(
                        (String) row.get("phone_number"),
                        (String) row.get("username"),
                        (String) row.get("sex"),
                        (String) row.get("birthday")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Login failed", e);
        }
    }

    public boolean signup(String phoneNumber, String password, String sex, String birthday) {
        try {
            jdbcHelper.executeUpdate(SIGNUP_QUERY, phoneNumber, password, sex, birthday);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Signup failed", e);
        }
    }

    public boolean isExist(String phoneNumber) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(IS_EXIST_QUERY, phoneNumber);
            return !results.isEmpty();
        } catch (SQLException e) {
            return false;
        }
    }

    public User selectUser(String phoneNumber) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(SELECT_USER_QUERY, phoneNumber);
            if (!results.isEmpty()) {
                Map<String, Object> row = results.get(0);
                return new User(
                        (String) row.get("phone_number"),
                        (String) row.get("username"),
                        (String) row.get("sex"),
                        (String) row.get("birthday")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Select failed", e);
        }
    }

    public boolean updatePassword(String phoneNumber, String password) {
        try {
            jdbcHelper.executeUpdate(UPDATE_PASSWORD_QUERY, password, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

    public boolean updateUsername(String phoneNumber, String username) {
        try {
            jdbcHelper.executeUpdate(UPDATE_USERNAME_QUERY, username, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

    public boolean updateBirthday(String phoneNumber, String birthday) {
        try {
            jdbcHelper.executeUpdate(UPDATE_BIRTHDAY_QUERY, birthday, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

}
