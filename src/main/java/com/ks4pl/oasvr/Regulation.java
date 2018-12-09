package com.ks4pl.oasvr;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Regulation {
    private Integer Id;
    private String Name;
    private String Department;
    private Date PubDate;
    private String State;
    private Integer OperatorId;
    private Date OperateTime;

     public Regulation(Integer id, String name, String department, Date pubDate, String state, Integer operatorId, Date operateTime) {
        setId(id);
        setName(name);
        setDepartment(department);
        setPubDate(pubDate);
        setState(state);
        setOperatorId(operatorId);
        setOperateTime(operateTime);
    }

    @JsonProperty("Id")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @JsonProperty("Department")
    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    @JsonProperty("PubDate")
    public Date getPubDate() {
        return PubDate;
    }

    public void setPubDate(Date pubeDate) {
        PubDate = pubeDate;
    }

    public Integer getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(Integer operatorId) {
        OperatorId = operatorId;
    }

    public Date getOperateTime() {
        return OperateTime;
    }

    public void setOperateTime(Date operateTime) {
        OperateTime = operateTime;
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "Id=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Department='" + getDepartment() + '\'' +
                ", PubDate=" + getPubDate() +
                ", OperatorId=" + getOperatorId() +
                ", OperateTime=" + getOperateTime() +
                '}';
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
