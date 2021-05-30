package com.birzeit.memsystem.Models;

public class Doctor {
    String fullName , userName ,email, phoneNum , gender , employeeId , specialty ;

    public Doctor(String fullName, String userName, String email, String phoneNum, String gender, String employeeId, String specialty) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.employeeId = employeeId;
        this.specialty = specialty;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender='" + gender + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
