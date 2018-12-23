package com.ks4pl.oasvr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Regulation {
    private Integer id;
    private String name;
    private Integer department;
    private Date issueDate;
    private String state;
    private Integer operatorId;
    private Timestamp operateTime;

     public Regulation(String name, Integer department, Date issueDate, String state, Integer operatorId, Timestamp operateTime) {
        setId(0);
        setName(name);
        setDepartment(department);
        setIssueDate(issueDate);
        setState(state);
        setOperatorId(operatorId);
        setOperateTime(operateTime);
    }

    public Regulation() {
    }

}
