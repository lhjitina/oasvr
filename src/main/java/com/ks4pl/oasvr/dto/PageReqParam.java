package com.ks4pl.oasvr.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Data
public class PageReqParam {
    @NotNull @Min(1)
    Integer num;
    @NotNull @Min(10) @Max(100)
    Integer size;
    @NotNull
    Map<String, Object> param;

}
