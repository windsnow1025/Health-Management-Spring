package com.windsnow1025.javaspringboot.model;

public class User {
    private String phoneNumber;
    private String username;

    public User(String phoneNumber, String username) {
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    // Getters and setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
