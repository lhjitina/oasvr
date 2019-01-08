package com.ks4pl.oasvr.controller;

import org.springframework.validation.Errors;

public class ControllerBase {
    void argumentError(Errors errors) throws IllegalArgumentException{
        if (errors.hasErrors()){
            throw new IllegalArgumentException(errors.getAllErrors().toString());
        }
    }
}
