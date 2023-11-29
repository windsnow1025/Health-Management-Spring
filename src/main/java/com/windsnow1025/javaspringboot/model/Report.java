package com.windsnow1025.javaspringboot.model;

import java.sql.Blob;
import java.sql.Date;

public class Report {
    String phone_number;
    int report_No;
    Blob report_content;
    Blob report_picture;
    String report_type;
    Date report_date;
    String report_place;
    String is_deleted;

    public Report(String phone_number, int report_No, Blob report_content, Blob report_picture, String report_type, Date report_date, String report_place, String is_deleted) {
        this.phone_number = phone_number;
        this.report_No = report_No;
        this.report_content = report_content;
        this.report_picture = report_picture;
        this.report_type = report_type;
        this.report_date = report_date;
        this.report_place = report_place;
        this.is_deleted = is_deleted;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getReport_No() {
        return report_No;
    }

    public void setReport_No(int report_No) {
        this.report_No = report_No;
    }

    public Blob getReport_content() {
        return report_content;
    }

    public void setReport_content(Blob report_content) {
        this.report_content = report_content;
    }

    public Blob getReport_picture() {
        return report_picture;
    }

    public void setReport_picture(Blob report_picture) {
        this.report_picture = report_picture;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getReport_place() {
        return report_place;
    }

    public void setReport_place(String report_place) {
        this.report_place = report_place;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
