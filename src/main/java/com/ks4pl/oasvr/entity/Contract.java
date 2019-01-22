package com.ks4pl.oasvr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class Contract {
    Integer id;

    @NotNull @NotBlank
    String name;
    String partner;
    String type;
    Integer autoRenewal;
    @NotNull
    Date start;
    @NotNull
    Date end;
    String digest;
    @NotNull
    Integer operatorId;
    String operatorName;
    Timestamp operateTime;
}
