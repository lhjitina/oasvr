package com.ks4pl.oasvr.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;


public class ServiceBase {
    @Autowired
    private SessionService sessionService;

    protected void addPageParam(HashMap<String, Object> con, Integer num, Integer size){
            con.put("num", (num-1)*size);
            con.put("size", size);
    }

    protected  Integer getCurrentUserId(){
        return sessionService.getCurrentUserId();
    }
}
