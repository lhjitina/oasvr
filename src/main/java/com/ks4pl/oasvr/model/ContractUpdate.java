package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
public class ContractUpdate {
    @NotBlank
    String name;
    @NotNull
    String partner;
    @NotNull
    String type;
    @NotNull
    Integer autoRenewal;
    @NotNull
    Date start;
    @NotNull
    Date end;
    @NotNull
    String digest;

    Integer operatorId;
    Timestamp operateTime;
}
