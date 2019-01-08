package com.ks4pl.oasvr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Policy {
    private Integer id;

    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String institution;
    private Date issueDate;
    @NotNull @NotBlank
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
