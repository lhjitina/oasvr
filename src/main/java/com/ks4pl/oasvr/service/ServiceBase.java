package com.ks4pl.oasvr.service;

import java.util.HashMap;


public class ServiceBase {
    protected void addPageParam(HashMap<String, Object> con, Integer num, Integer size){
            con.put("num", (num-1)*size);
            con.put("size", size);
    }
}
