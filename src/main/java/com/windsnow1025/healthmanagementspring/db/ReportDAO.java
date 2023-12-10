package com.windsnow1025.healthmanagementspring.db;

import com.windsnow1025.healthmanagementspring.api.SyncController;
import com.windsnow1025.healthmanagementspring.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import java.util.Base64;

public class ReportDAO {

    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    private static final String SELECT_REPORT = """
            SELECT *
            FROM report
            WHERE phone_number = ?
            """;
    private static final String INSERT_REPORT = """
            INSERT INTO report(id, phone_number, report_date, hospital, report_type, picture, detail)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String DELETE_REPORT = """
            DELETE FROM report WHERE phone_number = ?
            """;
    private JDBCHelper jdbcHelper;

    public ReportDAO() {
        this.jdbcHelper = new JDBCHelper();
    }

    public List<Report> select(String phoneNumber) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(SELECT_REPORT, phoneNumber);
            List<Report> reportList = new ArrayList<>();
            for (Map<String, Object> row : results) {
                byte[] pictureBytes = (byte[]) row.get("picture");
                String picture = (pictureBytes != null) ? Base64.getEncoder().encodeToString(pictureBytes) : null;

                reportList.add(new Report(
                        (Integer) row.get("id"),
                        (String) row.get("phone_number"),
                        (String) row.get("report_date"),
                        (String) row.get("hospital"),
                        (String) row.get("report_type"),
                        picture,
                        (String) row.get("detail")
                ));
            }
            return reportList;
        } catch (SQLException e) {
            throw new RuntimeException("Select reports failed", e);
        }
    }

    private boolean delete(String phoneNumber) {
        try {
            jdbcHelper.executeUpdate(DELETE_REPORT, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Delete reports failed", e);
        }
    }

    public boolean insert(List<Report> reportList) {
        try {
            for (Report report : reportList) {
                String base64String = report.getPicture();
                byte[] pictureBytes = null;
                if (base64String != null && !base64String.isEmpty()) {
                    pictureBytes = Base64.getDecoder().decode(base64String);
                }

                jdbcHelper.executeUpdate(INSERT_REPORT,
                        report.getId(),
                        report.getPhone_number(),
                        report.getReport_date(),
                        report.getHospital(),
                        report.getReport_type(),
                        pictureBytes,
                        report.getDetail()
                );
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Insert reports failed", e);
        }
    }

    public boolean sync(List<Report> reportList, String phone_number) {
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(reportList);
        return deleteResult && insertResult;
    }

}
