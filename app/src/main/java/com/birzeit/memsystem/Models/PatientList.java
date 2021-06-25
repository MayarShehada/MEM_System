package com.birzeit.memsystem.Models;


public class PatientList {

    private int id;
    private String fullname,email,phonenum,gender,address, relative1,relative2,Doctor_fullName,Doctor_email ;

    public PatientList() {}

    public PatientList(int id, String fullname, String email, String phonenum, String gender, String address, String relative1, String relative2, String doctor_fullName, String doctor_email) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phonenum = phonenum;
        this.gender = gender;
        this.address = address;
        this.relative1 = relative1;
        this.relative2 = relative2;
        Doctor_fullName = doctor_fullName;
        Doctor_email = doctor_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDoctor_fullName() {
        return Doctor_fullName;
    }

    public void setDoctor_fullName(String doctor_fullName) {
        Doctor_fullName = doctor_fullName;
    }

    public String getDoctor_email() {
        return Doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        Doctor_email = doctor_email;
    }
}


