package com.windsnow1025.javaspringboot;

import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.History;
import com.windsnow1025.javaspringboot.model.Report;

import java.util.List;

public class SyncRequest {
    private List<History> histories;
    private List<Report> reports;
    private List<Alert> alerts;

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
}
