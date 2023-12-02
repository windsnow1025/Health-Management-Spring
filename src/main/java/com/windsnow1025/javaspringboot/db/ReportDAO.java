package com.windsnow1025.javaspringboot.db;

import com.windsnow1025.javaspringboot.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
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
    public ReportDAO(){
        this.jdbcHelper = new JDBCHelper();
    }

    public List<Report> select(String phone_number){
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT);
        ){
            List<Report> reportList = new ArrayList<>();
            preparedStatement.setString(1,phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int report_id = resultSet.getInt("id");
                String report_date = resultSet.getString("report_date");
                String hospital = resultSet.getString("hospital");
                String report_type = resultSet.getString("report_type");
                String picture = resultSet.getString("picture");
                String detail = resultSet.getString("detail");
                reportList.add(new Report(report_id,phone_number, report_date, hospital, report_type, picture, detail));
            }

            if (reportList.isEmpty()) {
                return null;
            }

            return reportList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean delete(String phone_number){
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)){
            preparedStatement.setString(1,phone_number);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(List<Report> reportList){
        try (Connection connection = jdbcHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT)){
            if (reportList == null){
                return false;
            }

            for (Report report:reportList){
                preparedStatement.setInt(1,report.getId());
                preparedStatement.setString(2,report.getPhone_number());
                preparedStatement.setString(3,report.getReport_date());
                preparedStatement.setString(4,report.getHospital());
                preparedStatement.setString(5,report.getReport_type());
                preparedStatement.setString(6,report.getPicture());
                preparedStatement.setString(7,report.getDetail());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sync(List<Report> reportList, String phone_number){
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(reportList);
        return deleteResult && insertResult;
    }

}
