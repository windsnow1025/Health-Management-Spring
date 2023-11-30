package com.windsnow1025.javaspringboot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windsnow1025.javaspringboot.db.AlertDAO;
import com.windsnow1025.javaspringboot.db.RecordDAO;
import com.windsnow1025.javaspringboot.db.JDBCHelper;
import com.windsnow1025.javaspringboot.db.ReportDAO;
import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.Record;
import com.windsnow1025.javaspringboot.model.Report;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final JDBCHelper jdbcHelper = new JDBCHelper();
    private final RecordDAO recordDAO = new RecordDAO(jdbcHelper);
    private final ReportDAO reportDAO = new ReportDAO(jdbcHelper);
    private final AlertDAO alertDAO = new AlertDAO(jdbcHelper);

    @PostMapping("/get")
    public ResponseEntity<?> getData(@RequestBody SyncRequest request) {
        String phoneNumber = request.getPhone_number();
        try {
            List<Record> recordList = recordDAO.getData(phoneNumber);
            List<Report> reportList = reportDAO.getData(phoneNumber);
            List<Alert> alertList = alertDAO.getData(phoneNumber);

            SyncReturn syncReturn = new SyncReturn(recordList, reportList, alertList);

            return ResponseEntity.ok(syncReturn);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> syncData(@RequestBody SyncRequest request) {
        List<Record> recordList = request.getRecords();
        List<Report> reportList = request.getReports();
        List<Alert> alertList = request.getAlerts();
        String phoneNumber = request.getPhone_number();
        try {

            recordDAO.insertData(recordList, phoneNumber);
            reportDAO.insertData(reportList, phoneNumber);
            alertDAO.insertData(alertList, phoneNumber);

            return ResponseEntity.ok(Map.of("status", "Success", "message", "Update successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

