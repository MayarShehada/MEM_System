package com.birzeit.memsystem.Models;

public class Check {

    private int checkId;
    private String heartBeat;
    private String bodyTemp;
    private String bloodPressure;
    private String dateOfCheck;
    private String role;
    private String fullname;
    private String email;
    public Check() {
    }


    public Check(int checkId, String heartBeat, String bodyTemp, String bloodPressure, String dateOfCheck, String role, String fullname, String email) {
        this.checkId = checkId;
        this.heartBeat = heartBeat;
        this.bodyTemp = bodyTemp;
        this.bloodPressure = bloodPressure;
        this.dateOfCheck = dateOfCheck;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(String dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Check{" +
                "checkId=" + checkId +
                ", heartBeat='" + heartBeat + '\'' +
                ", bodyTemp='" + bodyTemp + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", dateOfCheck='" + dateOfCheck + '\'' +
                ", role='" + role + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
