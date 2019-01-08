package com.ks4pl.oasvr.model;

import com.ks4pl.oasvr.entity.Policy;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class PolicyListItem extends Policy{
    private String operatorName;
}
