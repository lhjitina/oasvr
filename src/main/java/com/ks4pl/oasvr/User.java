package com.ks4pl.oasvr;

import java.util.Arrays;

public class User {
    private Integer userId;
    private String userName;
    private String department;
    private String role;
    private String tel;
    private String email;
    private String state;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", priviledge=" + Arrays.toString(priviledge) +
                '}';
    }

    public void setUser(User user){
        this.setUserId(user.getUserId());
        this.setUserName(user.getUserName());
        this.setTel(user.getTel());
        this.setEmail(user.getEmail());
        this.setDepartment(user.getDepartment());
         this.setState(user.getState());
        this.setRole(user.getRole());
        this.setPriviledge(user.getPriviledge());
    }
}
