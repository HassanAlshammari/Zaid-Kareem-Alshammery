package com.example.najaf.locationpharmacy;

public class AdminLogin {
    private String ID;
    private String AdminName;
    private  String Password;

    public AdminLogin(String ID, String adminName, String password) {
        this.ID = ID;
        AdminName = adminName;
        Password = password;
    }
    public AdminLogin(){}

    public AdminLogin(String adminName, String password) {
        AdminName = adminName;
        Password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
