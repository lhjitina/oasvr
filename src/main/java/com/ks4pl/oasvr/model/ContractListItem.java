package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContractListItem extends Contract {
    public String operatorName;


    public ContractListItem(String operatorName) {
        this.operatorName = operatorName;
    }

    public ContractListItem(Integer id, String name, Date issueDate, String state, Integer operatorId, Timestamp operateTime, String operatorName) {
        super(id, name, issueDate, state, operatorId, operateTime);
        this.operatorName = operatorName;
    }
}