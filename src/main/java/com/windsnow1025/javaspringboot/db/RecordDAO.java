package com.windsnow1025.javaspringboot.db;

import com.windsnow1025.javaspringboot.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {
    private static final String SELECT_RECORD = """
            SELECT * FROM record
            WHERE phone_number = ?
            """;
    private static final String DELETE_RECORD = """
            DELETE FROM record
            WHERE phone_number = ?
            """;
    private static final String INSERT_RECORD = """
            INSERT INTO record(phone_number, record_date, hospital, doctor, organ, symptom, conclusion, suggestion)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private JDBCHelper jdbcHelper;

    public RecordDAO() {
        this.jdbcHelper = new JDBCHelper();
    }

    public List<Record> getData(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RECORD);
        ) {
            List<Record> recordList = new ArrayList<>();
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int record_id = resultSet.getInt("ID");
                String record_date = resultSet.getString("record_date");
                String hospital = resultSet.getString("hospital");
                String doctor = resultSet.getString("doctor");
                String organ = resultSet.getString("organ");
                String symptom = resultSet.getString("symptom");
                String conclusion = resultSet.getString("conclusion");
                String suggestion = resultSet.getString("suggestion");
                recordList.add(new Record(record_id,phone_number, record_date, hospital, doctor, organ, symptom, conclusion, suggestion));
            }

            if (recordList.isEmpty()) {
                return null;
            }

            return recordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RECORD)) {
            preparedStatement.setString(1, phone_number);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertData(List<Record> recordList, String phone_number) {

        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECORD)) {
            if (recordList == null) {
                return false;
            }

            delete(phone_number);

            for (Record record : recordList) {
                preparedStatement.setString(1, record.getPhone_number());
                preparedStatement.setString(2, record.getRecord_date());
                preparedStatement.setString(3, record.getHospital());
                preparedStatement.setString(4, record.getDoctor());
                preparedStatement.setString(5, record.getOrgan());
                preparedStatement.setString(6, record.getSymptom());
                preparedStatement.setString(7, record.getConclusion());
                preparedStatement.setString(8, record.getSuggestion());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
