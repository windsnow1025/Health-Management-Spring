package com.windsnow1025.javaspringboot.db;

import com.windsnow1025.javaspringboot.model.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {
    private static final String SELECT_ALERT = """
            SELECT *
            FROM alert
            WHERE phone_number = ?
            """;
    private static final String DELETE_ALERT = """
            DELETE FROM alert
            WHERE phone_number = ?
            """;
    private static final String INSERT_ALERT = """
            INSERT INTO alert(phone_number, alert_type, advice, title, alert_date, alert_cycle, is_medicine)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private JDBCHelper jdbcHelper;

    public AlertDAO(JDBCHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
    }

    public List<Alert> getData(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALERT)) {
            List<Alert> alertList = new ArrayList<>();
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String alert_type = resultSet.getString("alert_type");
                String advice = resultSet.getString("advice");
                String title = resultSet.getString("title");
                Date alert_date = resultSet.getDate("alert_date");
                String alert_cycle = resultSet.getString("alert_cycle");
                String is_medicine = resultSet.getString("is_medicine");
                alertList.add(new Alert(phone_number, alert_type, advice, title, alert_date, alert_cycle, is_medicine));
            }
            if (alertList.isEmpty()) {
                return null;
            }

            return alertList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALERT)) {
            preparedStatement.setString(1, phone_number);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertData(List<Alert> alertList, String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALERT)) {
            if (alertList == null) {
                return false;
            }

            delete(phone_number);

            for (Alert alert : alertList) {
                preparedStatement.setString(1, alert.getPhone_number());
                preparedStatement.setString(2, alert.getAlert_type());
                preparedStatement.setString(3, alert.getAdvice());
                preparedStatement.setString(4, alert.getTitle());
                preparedStatement.setDate(5, alert.getAlert_date());
                preparedStatement.setString(6, alert.getAlert_cycle());
                preparedStatement.setString(7, alert.getIs_medicine());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
