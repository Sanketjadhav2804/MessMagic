package com.example.messmagic;

import java.time.LocalDate;

public class MembershipModel {

    String date , endDate;

    public MembershipModel(String date, String endDate) {
        this.date = date;
        this.endDate = endDate;
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