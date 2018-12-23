package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Summary {
    private Integer id;
    private String name;
    private Date meetingDate;
    private Integer operatorId;
    private Timestamp operateTime;

    public Summary(Integer id, String name, Date meetingDate, Integer operatorId, Timestamp operateTime) {
        this.id = id;
        this.name = name;
        this.meetingDate = meetingDate;
        this.operatorId = operatorId;
        this.operateTime = operateTime;
    }

    public Summary() {
    }
}
