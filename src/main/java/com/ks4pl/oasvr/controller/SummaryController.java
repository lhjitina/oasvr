package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.ServiceException;
import com.ks4pl.oasvr.service.SessionService;
import com.ks4pl.oasvr.service.SummaryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class SummaryController extends ControllerBase{
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private SummaryService summaryService;

    @RequestMapping(value = "/api/front/summary/list", method = RequestMethod.GET)
    public RespPage frontGetSummaryList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                summaryService.total(pageReqParam.getFilter()),
                summaryService.selectByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value = "/api/console/summary/list", method = RequestMethod.POST)
    public RespPage consoleGetPolicyList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
            argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                summaryService.total(pageReqParam.getFilter()),
                summaryService.selectByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value="/api/summary/content/{name}", method = RequestMethod.GET)
    public void getSummaryContent(@PathVariable String name, HttpServletResponse response){
        if (summaryService.getSummaryContent(name, response) == false){
            logger.error("getSummaryContent error");
        }
    }

    @RequestMapping(value = "/api/summary/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "meetingDate") String meetingDateStr, MultipartFile file)
            throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException{
        //检查用户权限
        if (!permissionService.sumPermissionExist(sessionService.getCurrentUserId())){
            logger.error("no permission");
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查会议日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date meetingDate = null;
        try {
            meetingDate = sdf.parse(meetingDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("meetging data error:" + meetingDateStr);
        }
        //存储文件
        summaryService.FileUpload(meetingDate, file);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/summary/delete", method = RequestMethod.GET)
    public RespData deleteSummary(@RequestParam("name")String name) throws ServiceException{
        logger.info("deleteSummary:" + name);
        summaryService.deleteByName(name);
        return RespData.ok();
    }

}
