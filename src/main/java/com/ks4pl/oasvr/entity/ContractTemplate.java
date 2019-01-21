package com.ks4pl.oasvr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class ContractTemplate {
    private Integer id;

    @NotNull @NotBlank
    private String name;
    private Date issueDate;

    @NotNull @NotBlank
    private String state;
    private Integer operatorId;
    private Timestamp operateTime;


    public ContractTemplate(){}

    public ContractTemplate(Integer id, String name, Date issueDate, String state, Integer operatorId, Timestamp operateTime) {
        this.id = id;
        this.name = name;
        this.issueDate = issueDate;
        this.state = state;
        this.operatorId = operatorId;
        this.operateTime = operateTime;
    }
}

