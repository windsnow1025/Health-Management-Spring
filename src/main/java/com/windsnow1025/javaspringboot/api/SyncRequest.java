package com.windsnow1025.javaspringboot.api;


import java.util.List;

public class SyncRequest<T> {
    private List<T> data;
    private String phone_number;

    public SyncRequest(List<T> data, String phone_number) {
        this.data = data;
        this.phone_number = phone_number;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
