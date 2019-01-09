package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;

public class InfoPortController extends ControllerBase {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private InfoPortService infoPortService;

    @RequestMapping(value = "/api/share/list", method = RequestMethod.POST)
    public RespPage frontGetPdocList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                infoPortService.total(pageReqParam.getFilter()),
                infoPortService.selectByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value="/api/share/content", method = RequestMethod.GET)
    public void getShareInfoContent(@RequestParam String name, HttpServletResponse response){
        if (infoPortService.getShareInfoContent(name, response) == false){
            logger.error("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/share/upload", method = RequestMethod.POST)
    public RespData fileUpload(MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        infoPortService.upload(file);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/share/refresh", method = RequestMethod.POST)
    public RespData fileRefresh(MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        infoPortService.refresh(file);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/share/delete", method = RequestMethod.POST)
    public RespData fileDelete(@RequestBody String name){
        logger.info("delete file: " + name);
        infoPortService.delete(name);
        return RespData.ok();
    }
}
