package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.PartnerDoc;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PdocListItem extends PartnerDoc {
    private String operatorName;


    @Override
    public String toString() {
        return "PdocListItem{" +
                super.toString() +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
