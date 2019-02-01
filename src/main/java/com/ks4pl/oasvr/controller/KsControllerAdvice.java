package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class KsControllerAdvice {
    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler(value = IllegalArgumentException.class)
    public RespData ArgumentError(IllegalArgumentException pe){
        logger.error(pe.getMessage());
        return RespData.err(RespCode.PARAM_ERR).setMsg(pe.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    public RespData ServiceError(ServiceException se){
        logger.error(se.getMessage());
        return RespData.err(RespCode.SERV_ERR).setMsg(se.getMessage());
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public RespPage SqlError(SQLIntegrityConstraintViolationException se){
        logger.error(se.getMessage());
        logger.error("return error page: " );
        logger.error(RespPage.errPage(RespCode.SERV_ERR).setMsg(se.getMessage()).toString());
        return (RespPage) RespPage.errPage(RespCode.SERV_ERR).setMsg(se.getMessage());
    }
}
