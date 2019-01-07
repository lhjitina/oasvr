package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.service.ParamException;
import com.ks4pl.oasvr.service.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class KsControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = ParamException.class)
    public RespPage ParamError(ParamException pe){
        return (RespPage)RespPage.errPage(RespCode.PARAM_ERR).setMsg(pe.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public RespPage ServiceError(ServiceException se){
        return (RespPage) RespPage.errPage(RespCode.SERV_ERR).setMsg(se.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public RespPage SqlError(SQLIntegrityConstraintViolationException se){
        return (RespPage) RespPage.errPage(RespCode.SERV_ERR).setMsg(se.getMessage());
    }
}
