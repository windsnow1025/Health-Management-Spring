package com.windsnow1025.javaspringboot.api;

import com.windsnow1025.javaspringboot.db.AlertDAO;
import com.windsnow1025.javaspringboot.db.HistoryDAO;
import com.windsnow1025.javaspringboot.db.ReportDAO;
import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.History;
import com.windsnow1025.javaspringboot.model.Report;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final HistoryDAO historyDAO = new HistoryDAO();
    private final ReportDAO reportDAO = new ReportDAO();
    private final AlertDAO alertDAO = new AlertDAO();

    @PostMapping("/")
    public ResponseEntity<?> syncData(@RequestBody SyncRequest request) {
        try {
            // Process and sync history
            for (History history : request.getHistories()) {
                historyDAO.syncHistory(history);
            }

            // Process and sync report
            for (Report report : request.getReports()) {
                reportDAO.syncReport(report);
            }

            // Process and sync alert
            for (Alert alert : request.getAlerts()) {
                alertDAO.syncAlert(alert);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

