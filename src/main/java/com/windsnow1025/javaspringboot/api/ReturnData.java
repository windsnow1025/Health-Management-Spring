package com.windsnow1025.javaspringboot.api;

import com.windsnow1025.javaspringboot.model.Alert;
import com.windsnow1025.javaspringboot.model.Record;
import com.windsnow1025.javaspringboot.model.Report;

import java.util.List;

public class ReturnData {
    private List<Record> recordList;
    private List<Report> reportList;
    private List<Alert> alerts;
    private String phone_number;

    public ReturnData(List<Record> recordList, List<Report> reportList, List<Alert> alerts, String phone_number) {
        this.recordList = recordList;
        this.reportList = reportList;
        this.alerts = alerts;
        this.phone_number = phone_number;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
