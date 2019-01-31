package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PdocDelInfo {
    @NotBlank
    String name;
}
