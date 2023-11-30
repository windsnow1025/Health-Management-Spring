package com.windsnow1025.javaspringboot.api;

import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.Record;
import com.windsnow1025.javaspringboot.model.Report;

import java.util.List;

public class ReturnData {
    private List<Record> recordList;
    private List<Report> reportList;
    private List<Alert> alerts;

    public ReturnData(List<Record> recordList, List<Report> reportList, List<Alert> alerts) {
        this.recordList = recordList;
        this.reportList = reportList;
        this.alerts = alerts;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

}
