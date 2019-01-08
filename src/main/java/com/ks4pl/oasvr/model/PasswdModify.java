package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswdModify {
    @NotNull @Size(min=3, max=16)
    String oldp;

    @NotNull @Size(min=3, max=16)
    String newp;
}
