package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Policy {
    private Integer id;
    private String name;
    private String institution;
    private Date issueDate;
    private String state;
    private Integer operatorId;
    private Timestamp operateTime;


    public Policy(String name, String institution, Date issueDate, String state, Integer operatorId, Timestamp operateTime) {
        this.id = 0;
        this.name = name;
        this.institution = institution;
        this.issueDate = issueDate;
        this.state = state;
        this.operatorId = operatorId;
        this.operateTime = operateTime;
    }
    public Policy(){}
}
