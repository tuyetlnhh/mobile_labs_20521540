package com.example.mobile_labs_20521540;

public class HelperClass {
    String name, phone, user, pass;

    public HelperClass(String name, String phone, String user, String pass) {
        this.name = name;
        this.phone = phone;
        this.user = user;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
