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
            INSERT INTO alert(report_id, record_id, phone_number, alert_type, advice, title, alert_date, alert_cycle, is_medicine)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private JDBCHelper jdbcHelper;

    public AlertDAO() {
        this.jdbcHelper = new JDBCHelper();
    }

    public List<Alert> select(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALERT)) {
            List<Alert> alertList = new ArrayList<>();
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int record_id = resultSet.getInt("record_id");
                int report_id = resultSet.getInt("report_id");
                String alert_type = resultSet.getString("alert_type");
                String advice = resultSet.getString("advice");
                String title = resultSet.getString("title");
                String alert_date = resultSet.getString("alert_date");
                String alert_cycle = resultSet.getString("alert_cycle");
                String is_medicine = resultSet.getString("is_medicine");
                alertList.add(new Alert(id, record_id, report_id, phone_number, alert_type, advice, title, alert_date, alert_cycle, is_medicine));
            }
            if (alertList.isEmpty()) {
                return null;
            }

            return alertList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean delete(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALERT)) {
            preparedStatement.setString(1, phone_number);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(List<Alert> alertList) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALERT)) {
            if (alertList == null) {
                return false;
            }

            for (Alert alert : alertList) {
                preparedStatement.setInt(1, alert.getReport_id());
                preparedStatement.setInt(2, alert.getRecord_id());
                preparedStatement.setString(3, alert.getPhone_number());
                preparedStatement.setString(4, alert.getAlert_type());
                preparedStatement.setString(5, alert.getAdvice());
                preparedStatement.setString(6, alert.getTitle());
                preparedStatement.setString(7, alert.getAlert_date());
                preparedStatement.setString(8, alert.getAlert_cycle());
                preparedStatement.setString(9, alert.getIs_medicine());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sync(List<Alert> alertList, String phone_number) {
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(alertList);
        return deleteResult && insertResult;
    }

}
