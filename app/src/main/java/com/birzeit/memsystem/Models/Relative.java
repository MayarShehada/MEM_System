package com.birzeit.memsystem.Models;

public class Relative {

    private String fullname,username ,email,phonenum,gender,patientname;

    public Relative() {
    }

    public Relative(String fullname, String username, String email, String phonenum, String gender, String patientname) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phonenum = phonenum;
        this.gender = gender;
        this.patientname = patientname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }
}
