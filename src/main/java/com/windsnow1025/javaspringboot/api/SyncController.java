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

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody SyncRequest request) {
        String phone_number = request.getPhone_number();
        try {
            List<Record> recordList = recordDAO.getData(phone_number);
            List<Report> reportList = reportDAO.getData(phone_number);
            List<Alert> alertList = alertDAO.getData(phone_number);

            ReturnData returnData = new ReturnData(recordList,reportList,alertList,phone_number);

            // 使用Jackson库将对象转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(returnData);


            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updateData")
    public ResponseEntity<?> syncData(@RequestBody SyncRequest request){
        List<Record> recordList = request.getRecords();
        List<Report> reportList = request.getReports();
        List<Alert> alertList = request.getAlerts();
        String phone_number = request.getPhone_number();
        try {

            recordDAO.insertData(recordList,phone_number);
            reportDAO.insertData(reportList,phone_number);
            alertDAO.insertData(alertList,phone_number);

            return ResponseEntity.ok(Map.of("status", "Success", "message", "Update successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

