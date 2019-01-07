package com.ks4pl.oasvr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginInfo {

    @NotNull @NotBlank
    String loginName;

    @NotNull @NotBlank
    String passwd;
}
