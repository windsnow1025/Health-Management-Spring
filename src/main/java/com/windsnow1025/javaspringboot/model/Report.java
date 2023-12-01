package com.windsnow1025.javaspringboot.model;

import java.sql.Blob;
import java.sql.Date;

public class Report {
    private int ID;
    private String phone_number;
    private Date report_date;
    private String hospital;
    private String report_type;
    private Blob picture;
    private String detail;

    public Report(int report_id,String phone_number, Date report_date, String hospital, String report_type, Blob picture, String detail) {
        this.ID = report_id;
        this.phone_number = phone_number;
        this.report_date = report_date;
        this.hospital = hospital;
        this.report_type = report_type;
        this.picture = picture;
        this.detail = detail;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
