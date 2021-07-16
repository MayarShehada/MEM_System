package com.birzeit.memsystem.Models;

public class DoctorNotification {
    
    String id, title, doctorName, patientName, checkId, date;

    public DoctorNotification() {
    }

    public DoctorNotification(String id, String title, String doctorName, String patientName, String checkId, String date) {
        this.id = id;
        this.title = title;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.checkId = checkId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
