package com.windsnow1025.javaspringboot.model;



public class Record {
    private int id;
    private String phone_number;
    private String record_date;
    private String hospital;
    private String doctor;
    private String organ;
    private String symptom;
    private String conclusion;
    private String suggestion;

    public Record(int record_id,String phone_number, String record_date, String hospital, String doctor, String organ, String symptom, String conclusion, String suggestion) {
        this.id = record_id;
        this.phone_number = phone_number;
        this.record_date = record_date;
        this.hospital = hospital;
        this.doctor = doctor;
        this.organ = organ;
        this.symptom = symptom;
        this.conclusion = conclusion;
        this.suggestion = suggestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
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
