package com.windsnow1025.javaspringboot.model;


import java.sql.Date;

public class Record {
    private int ID;
    private String phone_number;
    private Date record_date;
    private String hospital;
    private String doctor;
    private String organ;
    private String symptom;
    private String conclusion;
    private String suggestion;

    public Record(int record_id,String phone_number, Date record_date, String hospital, String doctor, String organ, String symptom, String conclusion, String suggestion) {
        this.ID = record_id;
        this.phone_number = phone_number;
        this.record_date = record_date;
        this.hospital = hospital;
        this.doctor = doctor;
        this.organ = organ;
        this.symptom = symptom;
        this.conclusion = conclusion;
        this.suggestion = suggestion;
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

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
