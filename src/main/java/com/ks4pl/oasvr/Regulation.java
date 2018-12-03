package com.ks4pl.oasvr;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;

public class Regulation {
    private Integer Id;
    private String Name;
    private String Department;
    private Date PubDate;
    private Integer OperatorId;
    private Calendar OperateTime;

     public Regulation(Integer id, String name, String department, Date pubDate, Integer operatorId, Calendar operateTime) {
        Id = id;
        Name = name;
        Department = department;
        PubDate = pubDate;
        OperatorId = operatorId;
        OperateTime = operateTime;
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
    @JsonProperty("OperatorId")
    public Integer getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(Integer operatorId) {
        OperatorId = operatorId;
    }
    @JsonProperty("OperateTime")
    public Calendar getOperateTime() {
        return OperateTime;
    }

    public void setOperateTime(Calendar operateTime) {
        OperateTime = operateTime;
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Department='" + Department + '\'' +
                ", PubDate=" + PubDate +
                ", OperatorId=" + OperatorId +
                ", OperateTime=" + OperateTime +
                '}';
    }
}
