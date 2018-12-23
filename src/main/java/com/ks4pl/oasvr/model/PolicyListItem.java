package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
public class PolicyListItem {
    private Integer id;
    private String name;
    private String institution;
    private Date issueDate;
    private String state;
    private Integer operatorId;
    private String operatorName;
    private Timestamp operateTime;
}
