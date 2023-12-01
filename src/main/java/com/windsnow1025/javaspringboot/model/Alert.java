package com.windsnow1025.javaspringboot.model;


public class Alert {
    private int ID;
    private String phone_number;
    private String alert_type;
    private String advice;
    private String title;
    private String alert_date;
    private String alert_cycle;
    private String is_medicine;

    public Alert(int alert_id,String phone_number, String alert_type, String advice, String title, String alert_date, String alert_cycle, String is_medicine) {
        this.ID = alert_id;
        this.phone_number = phone_number;
        this.alert_type = alert_type;
        this.advice = advice;
        this.title = title;
        this.alert_date = alert_date;
        this.alert_cycle = alert_cycle;
        this.is_medicine = is_medicine;
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

    public String getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(String alert_date) {
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
