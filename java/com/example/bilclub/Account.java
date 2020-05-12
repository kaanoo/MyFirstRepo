package com.example.bilclub;

import android.net.Uri;
import android.text.Editable;

public class Account {
    String id;
    String userName;
    String department;
    String phone;
    String pass;
    String key;
    boolean isManager;
    Uri uri;
    String clubName;
    public Account(){}

    public Account(String id, String userName, String department, String phone, String pass, String key, boolean isManager, Uri uri, String clubName) {
        this.id = id;
        this.userName = userName;
        this.department = department;
        this.phone = phone;
        this.pass = pass;
        this.key = key;
        this.isManager = isManager;
        this.uri = uri;
        this.clubName = clubName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
