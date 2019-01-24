package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ContractDelete {
    @NotBlank
    String name;
}
