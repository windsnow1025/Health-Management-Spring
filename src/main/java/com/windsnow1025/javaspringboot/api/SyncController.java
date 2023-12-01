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


    private final RecordDAO recordDAO;
    private final ReportDAO reportDAO;
    private final AlertDAO alertDAO;

    public SyncController(){
        JDBCHelper jdbcHelper = new JDBCHelper();
        reportDAO = new ReportDAO(jdbcHelper);
        recordDAO = new RecordDAO(jdbcHelper);
        alertDAO = new AlertDAO(jdbcHelper);
    }

    @PostMapping("/get/record")
    public ResponseEntity<?> getRecordData(@RequestBody String phoneNumber) {
        try {
            List<Record> recordList = recordDAO.getData(phoneNumber);

            System.out.println("Work success");
            System.out.println(recordList.size());

            return ResponseEntity.ok(recordList);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/get/report")
    public ResponseEntity<?> getReportData(@RequestBody String phoneNumber) {
        try {
            List<Report> reportList = reportDAO.getData(phoneNumber);

            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/get/alert")
    public ResponseEntity<?> getData(@RequestBody String phoneNumber) {
        try {
            List<Alert> alertList = alertDAO.getData(phoneNumber);

            return ResponseEntity.ok(alertList);
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

