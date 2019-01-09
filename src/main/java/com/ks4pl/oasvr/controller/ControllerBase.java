package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class ControllerBase {
    @Autowired
    protected SessionService sessionService;
    @Autowired
    protected PermissionService permissionService;
    void argumentError(Errors errors) throws IllegalArgumentException{
        if (errors.hasErrors()){
            throw new IllegalArgumentException(errors.getAllErrors().toString());
        }
    }
}
