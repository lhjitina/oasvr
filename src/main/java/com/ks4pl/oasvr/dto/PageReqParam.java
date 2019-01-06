package com.ks4pl.oasvr.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class PageReqParam {
    Integer num;
    Integer size;
    HashMap<String, Object> filter;

}
