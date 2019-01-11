package com.ks4pl.oasvr.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ShareInfoDelete {
    @NotBlank @NonNull
    String name;
}
