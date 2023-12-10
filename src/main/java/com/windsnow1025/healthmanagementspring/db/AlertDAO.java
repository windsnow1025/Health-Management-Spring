package com.windsnow1025.healthmanagementspring.db;

import com.windsnow1025.healthmanagementspring.model.Alert;

import java.sql.*;
import java.util.*;

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

    public List<Alert> select(String phoneNumber) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(SELECT_ALERT, phoneNumber);
            List<Alert> alertList = new ArrayList<>();
            for (Map<String, Object> row : results) {
                alertList.add(new Alert(
                        (Integer) row.get("id"),
                        (Integer) row.get("record_id"),
                        (Integer) row.get("report_id"),
                        phoneNumber,
                        (String) row.get("alert_type"),
                        (String) row.get("advice"),
                        (String) row.get("title"),
                        (String) row.get("alert_date"),
                        (String) row.get("alert_cycle"),
                        (String) row.get("is_medicine")
                ));
            }
            return alertList;
        } catch (SQLException e) {
            throw new RuntimeException("Select alerts failed", e);
        }
    }

    private boolean delete(String phoneNumber) {
        try {
            jdbcHelper.executeUpdate(DELETE_ALERT, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Delete alerts failed", e);
        }
    }

    public boolean insert(List<Alert> alertList) {
        try {
            for (Alert alert : alertList) {
                jdbcHelper.executeUpdate(INSERT_ALERT,
                        alert.getReport_id(),
                        alert.getRecord_id(),
                        alert.getPhone_number(),
                        alert.getAlert_type(),
                        alert.getAdvice(),
                        alert.getTitle(),
                        alert.getAlert_date(),
                        alert.getAlert_cycle(),
                        alert.getIs_medicine()
                );
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Insert alerts failed", e);
        }
    }

    public boolean sync(List<Alert> alertList, String phone_number) {
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(alertList);
        return deleteResult && insertResult;
    }

}
