package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.ContractTemplate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class ContractTemplateListItem extends ContractTemplate {
    public String operatorName;


    public ContractTemplateListItem(Integer id, String name, Date issueDate, String state, Integer operatorId, Timestamp operateTime, String operatorName) {
        super(id, name, issueDate, state, operatorId, operateTime);
        this.operatorName = operatorName;
    }

    public ContractTemplateListItem() {
    }
}
