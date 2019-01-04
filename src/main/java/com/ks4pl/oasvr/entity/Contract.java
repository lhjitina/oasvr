package com.ks4pl.oasvr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Contract {
    private Integer id;
    private String name;
    private Date issueDate;
    private String state;
    private Integer operatorId;
    private Timestamp operateTime;


    public Contract(){}

    public Contract(Integer id, String name, Date issueDate, String state, Integer operatorId, Timestamp operateTime) {
        this.id = id;
        this.name = name;
        this.issueDate = issueDate;
        this.state = state;
        this.operatorId = operatorId;
        this.operateTime = operateTime;
    }
}

