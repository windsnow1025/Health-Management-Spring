package com.windsnow1025.javaspringboot.db;

import com.windsnow1025.javaspringboot.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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
            INSERT INTO record(id, phone_number, record_date, hospital, doctor, organ, symptom, conclusion, suggestion)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private JDBCHelper jdbcHelper;

    public RecordDAO() {
        this.jdbcHelper = new JDBCHelper();
    }

    public List<Record> select(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RECORD);
        ) {
            List<Record> recordList = new ArrayList<>();
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int record_id = resultSet.getInt("id");
                String record_date = resultSet.getString("record_date");
                String hospital = resultSet.getString("hospital");
                String doctor = resultSet.getString("doctor");
                String organ = resultSet.getString("organ");
                String symptom = resultSet.getString("symptom");
                String conclusion = resultSet.getString("conclusion");
                String suggestion = resultSet.getString("suggestion");
                recordList.add(new Record(record_id, phone_number, record_date, hospital, doctor, organ, symptom, conclusion, suggestion));
            }

            if (recordList.isEmpty()) {
                return Collections.emptyList();
            }

            return recordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean delete(String phone_number) {
        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RECORD)) {
            preparedStatement.setString(1, phone_number);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(List<Record> recordList) {

        try (Connection connection = jdbcHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECORD)) {
            if (recordList == null) {
                return false;
            }

            for (Record record : recordList) {
                preparedStatement.setInt(1, record.getId());
                preparedStatement.setString(2, record.getPhone_number());
                preparedStatement.setString(3, record.getRecord_date());
                preparedStatement.setString(4, record.getHospital());
                preparedStatement.setString(5, record.getDoctor());
                preparedStatement.setString(6, record.getOrgan());
                preparedStatement.setString(7, record.getSymptom());
                preparedStatement.setString(8, record.getConclusion());
                preparedStatement.setString(9, record.getSuggestion());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sync(List<Record> recordList, String phone_number) {
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(recordList);
        return deleteResult && insertResult;
    }
}
