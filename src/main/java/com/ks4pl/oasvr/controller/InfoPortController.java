package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.ShareInfoDelete;
import com.ks4pl.oasvr.model.ShareInfoListItem;
import com.ks4pl.oasvr.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@RestController
public class InfoPortController extends ControllerBase {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private InfoPortService infoPortService;

    @RequestMapping(value = "/api/share/list", method = RequestMethod.POST)
    public RespPage getShareInfoList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        logger.info("getShareInfoList:" + pageReqParam);
        argumentError(errors);
        logger.info(infoPortService.selectByCondition(pageReqParam.getParam()).toString());
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                infoPortService.total(pageReqParam.getParam()),
                infoPortService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value="/api/share/content", method = RequestMethod.GET)
    public void getShareInfoContent(@RequestParam String name, HttpServletResponse response)
            throws ServiceException{
        if (infoPortService.getShareInfoContent(name, response) == false){
            logger.error("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/share/upload", method = RequestMethod.POST)
    public RespData fileUpload(@RequestParam String tag, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException {
        infoPortService.upload(tag, file);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/share/refresh", method = RequestMethod.POST)
    public RespData fileRefresh(@RequestParam String tag, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        infoPortService.refresh(tag, file);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/share/delete", method = RequestMethod.POST)
    public RespData fileDelete(@RequestBody ShareInfoDelete name) throws ServiceException{
        logger.info("delete file: " + name);
        infoPortService.delete(name.getName());
        return RespData.ok();
    }

    @RequestMapping(value = "/api/share/fuzzy", method = RequestMethod.POST)
    public RespPage fuzzyQuery(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        logger.info("fuzzyQuery:" + pageReqParam);
        argumentError(errors);
        ArrayList<ShareInfoListItem> res = infoPortService.fuzzyQuery(pageReqParam.getParam());
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                res.size(),
                res);
    }
}
