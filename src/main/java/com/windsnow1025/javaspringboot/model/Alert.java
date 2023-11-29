package com.windsnow1025.javaspringboot.model;

import java.sql.Blob;
import java.sql.Date;

public class Alert {
    String phone_number;
    String alert_No;
    int type_No;
    String type;
    Blob content;
    String title;
    Date date;
    String cycle;
    String is_medicine;
    String is_deleted;

    public Alert(String phone_number, String alert_No, int type_No, String type, Blob content, String title, Date date, String cycle, String is_medicine, String is_deleted) {
        this.phone_number = phone_number;
        this.alert_No = alert_No;
        this.type_No = type_No;
        this.type = type;
        this.content = content;
        this.title = title;
        this.date = date;
        this.cycle = cycle;
        this.is_medicine = is_medicine;
        this.is_deleted = is_deleted;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAlert_No() {
        return alert_No;
    }

    public void setAlert_No(String alert_No) {
        this.alert_No = alert_No;
    }

    public int getType_No() {
        return type_No;
    }

    public void setType_No(int type_No) {
        this.type_No = type_No;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getIs_medicine() {
        return is_medicine;
    }

    public void setIs_medicine(String is_medicine) {
        this.is_medicine = is_medicine;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
