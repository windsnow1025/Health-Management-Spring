package com.windsnow1025.javaspringboot.api;

import com.windsnow1025.javaspringboot.db.AlertDAO;
import com.windsnow1025.javaspringboot.db.RecordDAO;
import com.windsnow1025.javaspringboot.db.ReportDAO;
import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.Record;
import com.windsnow1025.javaspringboot.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    private final RecordDAO recordDAO;
    private final ReportDAO reportDAO;
    private final AlertDAO alertDAO;

    public SyncController(){
        reportDAO = new ReportDAO();
        recordDAO = new RecordDAO();
        alertDAO = new AlertDAO();
    }

    @PostMapping("/get/record")
    public ResponseEntity<?> getRecordData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        try {
            List<Record> recordList = recordDAO.getData(phoneNumber);

            return ResponseEntity.ok(recordList);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/get/report")
    public ResponseEntity<?> getReportData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        try {
            List<Report> reportList = reportDAO.getData(phoneNumber);

            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/get/alert")
    public ResponseEntity<?> getData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        try {
            List<Alert> alertList = alertDAO.getData(phoneNumber);

            return ResponseEntity.ok(alertList);
        } catch (Exception e) {
            logger.error("Error: ", e);
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

