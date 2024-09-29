package com.example.messmagic;

import java.util.Date;

public class MonthlyMemberModel {


    String id, name, email, mobile, date, endDate;


    public MonthlyMemberModel() {
    }

    public MonthlyMemberModel(String id, String name, String email, String mobile, String date, String endDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.date = date;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
