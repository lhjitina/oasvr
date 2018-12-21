package com.ks4pl.oasvr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

public class Regulation {
    private Integer id;
    private String name;
    private Integer department;
    private Date issueDate;
    private String state;
    private Integer operatorId;
    private Timestamp operateTime;

     public Regulation(String name, Integer department, Date issueDate, String state, Integer operatorId, Timestamp operateTime) {
        setName(name);
        setDepartment(department);
        setIssueDate(issueDate);
        setState(state);
        setOperatorId(operatorId);
        setOperateTime(operateTime);
    }

    public Regulation() {
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("department")
    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    @JsonProperty("issueDate")
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date pubeDate) {
        issueDate = pubeDate;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", department='" + getDepartment() + '\'' +
                ", issueDate=" + getIssueDate() +
                ", operatorId=" + getOperatorId() +
                ", operateTime=" + getOperateTime() +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
