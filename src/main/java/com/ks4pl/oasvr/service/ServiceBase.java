package com.ks4pl.oasvr.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class ServiceBase {
    @Autowired
    private SessionService sessionService;

    protected void addPageParam(Map<String, Object> con, Integer num, Integer size){
            con.put("num", (num-1)*size);
            con.put("size", size);
    }

    protected void delBlankParam(Map<String, Object> con){
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(con.keySet());
        for (String k : keys){
            if (con.get(k).toString().trim().isEmpty()){
                con.remove(k);
                System.out.println("remove empty key:"+ k);
            }
        }
    }

    protected Integer getCurrentUserId() throws ServiceException{
        return sessionService.getCurrentUserId();
    }
}
