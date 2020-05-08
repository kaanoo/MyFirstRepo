package com.example.bilclub;

public class Account {
    String name;
    String department;
    int id;
    String key;
    public Account(){}
    public Account(String name, String department, int id, String key) {
        this.name = name;
        this.department = department;
        this.id = id;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
