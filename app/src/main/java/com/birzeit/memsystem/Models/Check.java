package com.birzeit.memsystem.Models;

public class Check {

    private int checkId;
    private String heartBeat;
    private String bodyTemp;
    private String bloodPressure;
    private String dateOfCheck;
    private String role;
    public Check() {
    }

//    public Check(int checkId, String heartBeat, String bodyTemp, String bloodPressure, String dateOfCheck) {
//        this.checkId = checkId;
//        this.heartBeat = heartBeat;
//        this.bodyTemp = bodyTemp;
//        this.bloodPressure = bloodPressure;
//        this.dateOfCheck = dateOfCheck;
//    }


    public Check(int checkId, String heartBeat, String bodyTemp, String bloodPressure, String dateOfCheck, String role) {
        this.checkId = checkId;
        this.heartBeat = heartBeat;
        this.bodyTemp = bodyTemp;
        this.bloodPressure = bloodPressure;
        this.dateOfCheck = dateOfCheck;
        this.role = role;
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
}
