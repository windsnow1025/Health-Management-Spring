package com.windsnow1025.javaspringboot.model;

import java.sql.Blob;
import java.sql.Date;

public class Alert {
    private String phone_number;
    private String alert_type;
    private String advice;
    private String title;
    private Date alert_date;
    private String alert_cycle;
    private String is_medicine;

    public Alert(String phone_number, String alert_type, String advice, String title, Date alert_date, String alert_cycle, String is_medicine) {
        this.phone_number = phone_number;
        this.alert_type = alert_type;
        this.advice = advice;
        this.title = title;
        this.alert_date = alert_date;
        this.alert_cycle = alert_cycle;
        this.is_medicine = is_medicine;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(Date alert_date) {
        this.alert_date = alert_date;
    }

    public String getAlert_cycle() {
        return alert_cycle;
    }

    public void setAlert_cycle(String alert_cycle) {
        this.alert_cycle = alert_cycle;
    }

    public String getIs_medicine() {
        return is_medicine;
    }

    public void setIs_medicine(String is_medicine) {
        this.is_medicine = is_medicine;
    }
}
