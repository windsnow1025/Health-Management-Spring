package com.windsnow1025.healthmanagementspring.api;

import com.windsnow1025.healthmanagementspring.db.AlertDAO;
import com.windsnow1025.healthmanagementspring.db.RecordDAO;
import com.windsnow1025.healthmanagementspring.db.ReportDAO;
import com.windsnow1025.healthmanagementspring.model.Alert;
import com.windsnow1025.healthmanagementspring.model.Record;
import com.windsnow1025.healthmanagementspring.model.Report;
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

    public SyncController() {
        reportDAO = new ReportDAO();
        recordDAO = new RecordDAO();
        alertDAO = new AlertDAO();
    }

    @PostMapping("/get/record")
    public ResponseEntity<?> getRecordData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        List<Record> recordList = recordDAO.select(phoneNumber);

        return ResponseEntity.ok(recordList);
    }

    @PostMapping("/get/report")
    public ResponseEntity<?> getReportData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        List<Report> reportList = reportDAO.select(phoneNumber);

        return ResponseEntity.ok(reportList);
    }

    @PostMapping("/get/alert")
    public ResponseEntity<?> getData(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        List<Alert> alertList = alertDAO.select(phoneNumber);

        return ResponseEntity.ok(alertList);
    }

    @PutMapping("/update/record")
    public ResponseEntity<?> syncRecordData(@RequestBody SyncRequest<Record> request) {
        List<Record> recordList = request.getData();
        String phoneNumber = request.getPhone_number();

        recordDAO.sync(recordList, phoneNumber);

        return ResponseEntity.ok(Map.of("status", "Success", "message", "Update successful"));

    }

    @PutMapping("/update/report")
    public ResponseEntity<?> syncReportData(@RequestBody SyncRequest<Report> request) {
        List<Report> reportList = request.getData();
        String phoneNumber = request.getPhone_number();

        reportDAO.sync(reportList, phoneNumber);

        return ResponseEntity.ok(Map.of("status", "Success", "message", "Update successful"));
    }

    @PutMapping("/update/alert")
    public ResponseEntity<?> syncData(@RequestBody SyncRequest<Alert> request) {
        List<Alert> alertList = request.getData();
        String phoneNumber = request.getPhone_number();

        alertDAO.sync(alertList, phoneNumber);

        return ResponseEntity.ok(Map.of("status", "Success", "message", "Update successful"));

    }
}

