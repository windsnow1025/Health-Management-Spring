package com.windsnow1025.javaspringboot.model;

import java.sql.Blob;
import java.sql.Date;

public class History {
    String phone_number;
    int history_No;
    Date history_date;
    String history_place;
    String history_doctor;
    String history_organ;
    Blob symptom;
    Blob conclusion;
    Blob suggestion;
    String is_deleted;

    public History(String phone_number, int history_No, Date history_date, String history_place, String history_doctor, String history_organ, Blob symptom, Blob conclusion, Blob suggestion, String is_deleted) {
        this.phone_number = phone_number;
        this.history_No = history_No;
        this.history_date = history_date;
        this.history_place = history_place;
        this.history_doctor = history_doctor;
        this.history_organ = history_organ;
        this.symptom = symptom;
        this.conclusion = conclusion;
        this.suggestion = suggestion;
        this.is_deleted = is_deleted;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getHistory_No() {
        return history_No;
    }

    public void setHistory_No(int history_No) {
        this.history_No = history_No;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public String getHistory_place() {
        return history_place;
    }

    public void setHistory_place(String history_place) {
        this.history_place = history_place;
    }

    public String getHistory_doctor() {
        return history_doctor;
    }

    public void setHistory_doctor(String history_doctor) {
        this.history_doctor = history_doctor;
    }

    public String getHistory_organ() {
        return history_organ;
    }

    public void setHistory_organ(String history_organ) {
        this.history_organ = history_organ;
    }

    public Blob getSymptom() {
        return symptom;
    }

    public void setSymptom(Blob symptom) {
        this.symptom = symptom;
    }

    public Blob getConclusion() {
        return conclusion;
    }

    public void setConclusion(Blob conclusion) {
        this.conclusion = conclusion;
    }

    public Blob getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Blob suggestion) {
        this.suggestion = suggestion;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
