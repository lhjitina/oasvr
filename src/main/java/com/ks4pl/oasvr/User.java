package com.ks4pl.oasvr;

import java.util.Arrays;

public class User {
    private Integer userId;
    private String userName;
    private String realName;
    private String department;
    private String role;
    private String tel;
    private String email;
    private String state;
    private Integer sex;

    private Integer[] priviledge;

    public User() {
    }

    public User(Integer userId, String userName, String department, String role, Integer[] priviledge) {
        this.userId = userId;
        this.userName = userName;
        this.department = department;
        this.role = role;
        this.priviledge = priviledge;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer[] getPriviledge() {
        return priviledge;
    }

    public void setPriviledge(Integer[] priviledge) {
        this.priviledge = priviledge;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", sex=" + sex +
                ", priviledge=" + Arrays.toString(priviledge) +
                '}';
    }
}
