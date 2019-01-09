package com.ks4pl.oasvr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter @Setter
public class ShareInfo {
    private Integer id;

    @NotNull
    @NotBlank
    private String name;
    @NotNull @NotBlank
    private Integer operatorId;
    private Timestamp operateTime;
}
