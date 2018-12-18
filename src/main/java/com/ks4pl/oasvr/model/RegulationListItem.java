package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class RegulationListItem {
    private String name;
    private String department;
    private Date issueDate;
    private String state;
    private String operator;
    private Timestamp operateTime;

}
