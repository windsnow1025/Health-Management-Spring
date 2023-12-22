package com.windsnow1025.healthmanagementspring.db;

import com.windsnow1025.healthmanagementspring.model.Record;

import java.sql.*;
import java.util.*;

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
        jdbcHelper = JDBCHelper.getInstance();
    }

    public List<Record> select(String phoneNumber) {
        try {
            List<Map<String, Object>> results = jdbcHelper.select(SELECT_RECORD, phoneNumber);
            List<Record> recordList = new ArrayList<>();
            for (Map<String, Object> row : results) {
                recordList.add(new Record(
                        (Integer) row.get("id"),
                        (String) row.get("phone_number"),
                        (String) row.get("record_date"),
                        (String) row.get("hospital"),
                        (String) row.get("doctor"),
                        (String) row.get("organ"),
                        (String) row.get("symptom"),
                        (String) row.get("conclusion"),
                        (String) row.get("suggestion")
                ));
            }
            return recordList;
        } catch (SQLException e) {
            throw new RuntimeException("Select records failed", e);
        }
    }

    private boolean delete(String phoneNumber) {
        try {
            jdbcHelper.executeUpdate(DELETE_RECORD, phoneNumber);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Delete records failed", e);
        }
    }

    public boolean insert(List<Record> recordList) {
        try {
            for (Record record : recordList) {
                jdbcHelper.executeUpdate(INSERT_RECORD,
                        record.getId(),
                        record.getPhone_number(),
                        record.getRecord_date(),
                        record.getHospital(),
                        record.getDoctor(),
                        record.getOrgan(),
                        record.getSymptom(),
                        record.getConclusion(),
                        record.getSuggestion()
                );
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Insert records failed", e);
        }
    }

    public boolean sync(List<Record> recordList, String phone_number) {
        boolean deleteResult = delete(phone_number);
        boolean insertResult = insert(recordList);
        return deleteResult && insertResult;
    }
}
