package com.ks4pl.oasvr.service;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 224665088837800802L;

    public ServiceException(String msg){
        super(msg);
    }
}
