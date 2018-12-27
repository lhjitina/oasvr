package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class PartnerDoc {
    private Integer id;
    private String name;
    private String partner;
    private Integer operatorId;
    private Timestamp operateTime;


    public PartnerDoc(){}
}
