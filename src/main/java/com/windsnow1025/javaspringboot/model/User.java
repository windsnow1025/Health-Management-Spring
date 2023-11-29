package com.windsnow1025.javaspringboot.model;

public class User {
    private String phoneNumber;
    private String username;
    private String sex;

    public User(String phoneNumber, String username, String sex, String birthday) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.sex = sex;
        this.birthday = birthday;
    }

    private String birthday;


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
