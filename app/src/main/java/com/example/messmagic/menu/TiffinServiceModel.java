package com.example.messmagic.menu;

public class TiffinServiceModel {



    String userid , name , email , mobile ,tiffinNo, time , uploadId ,status ,date ;

    Object timeStamp;

    Double amount;


    public TiffinServiceModel(){

    }

    public TiffinServiceModel(String userid, String name, String email, String mobile, String tiffinNo, String time, String uploadID, String status,String date , Double amount,Object timeStamp) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.tiffinNo = tiffinNo;
        this.time = time;
        this.uploadId=uploadID;
        this.status = status;
        this.date=date;
        this.amount=amount;
        this.timeStamp=timeStamp;
    }

    public TiffinServiceModel(String userid, String name, String email, String mobile, String tiffinNo, String time, String uploadID, String status,String date, Object timeStamp ) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.tiffinNo = tiffinNo;
        this.time = time;
        this.uploadId=uploadID;
        this.status = status;
        this.date=date;
        this.timeStamp=timeStamp;

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTiffinNo() {
        return tiffinNo;
    }

    public void setTiffinNo(String tiffinNo) {
        this.tiffinNo = tiffinNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
