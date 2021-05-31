package com.birzeit.memsystem.Models;

public class ParamedicList {

    private String fullname, username , email, phonenum, gender, ambulanceid, doctorname, doctoremail;

    public ParamedicList() {
    }

    public ParamedicList(String fullname, String username, String email, String phonenum, String gender, String ambulanceid, String doctorname, String doctoremail) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phonenum = phonenum;
        this.gender = gender;
        this.ambulanceid = ambulanceid;
        this.doctorname = doctorname;
        this.doctoremail = doctoremail;
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

    public String getAmbulanceid() {
        return ambulanceid;
    }

    public void setAmbulanceid(String ambulanceid) {
        this.ambulanceid = ambulanceid;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctoremail() {
        return doctoremail;
    }

    public void setDoctoremail(String doctoremail) {
        this.doctoremail = doctoremail;
    }
}
