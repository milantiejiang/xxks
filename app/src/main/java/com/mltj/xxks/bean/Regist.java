package com.mltj.xxks.bean;

import java.io.Serializable;

public class Regist implements Serializable {
    private String name;
    private String password;
    private String phone;
    private int registrationSide;
    private String userName;
    private int company;
    private int department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRegistrationSide() {
        return registrationSide;
    }

    public void setRegistrationSide(int registrationSide) {
        this.registrationSide = registrationSide;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
}
