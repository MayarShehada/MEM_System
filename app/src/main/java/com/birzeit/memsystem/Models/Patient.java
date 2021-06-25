package com.birzeit.memsystem.Models;

public class Patient {


    private String fullname,username ,email,phonenum,gender,address, relative1,relative2, doctorname ;

    public Patient() {
    }

    public Patient(String fullname, String username, String email, String phonenum, String gender, String address, String relative1, String relative2, String doctorname) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phonenum = phonenum;
        this.gender = gender;
        this.address = address;
        this.relative1 = relative1;
        this.relative2 = relative2;
        this.doctorname = doctorname;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelative1() {
        return relative1;
    }

    public void setRelative1(String relative1) {
        this.relative1 = relative1;
    }

    public String getRelative2() {
        return relative2;
    }

    public void setRelative2(String relative2) {
        this.relative2 = relative2;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }
}
