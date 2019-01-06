package com.ks4pl.oasvr.service;

import java.util.HashMap;


public class ServiceBase {
    protected void addPageParam(HashMap<String, Object> con,
                           Integer num,
                           Integer size) throws ParamException {
        if (num != null && size != null && num > 0 && size > 0){
            con.put("num", (num-1)*size);
            con.put("size", size);
        }
        else{
            System.out.println("page num or size is invalid");
            throw new ParamException("page num or size is invalid(null or <0).");
        }
    }
}
