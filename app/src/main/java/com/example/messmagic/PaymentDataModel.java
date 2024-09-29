package com.example.messmagic;


public class PaymentDataModel {

    String userid , name , email , mobile ,paymnetId, date;

    Double amount ;



    public PaymentDataModel() {

    }


    public PaymentDataModel(String userid, String name, String email, String mobile, String paymnetId, Double amount, String date)  {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.paymnetId = paymnetId;
        this.amount=amount;
        this.date=date;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getPaymnetId() {
        return paymnetId;
    }

    public void setPaymnetId(String paymnetId) {
        this.paymnetId = paymnetId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
