package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.entity.Policy;
import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.PolicyListItem;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.PolicyService;
import com.ks4pl.oasvr.service.ServiceException;
import com.ks4pl.oasvr.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.rmi.server.ServerCloneException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PolicyController extends ControllerBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private PolicyService policyService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/front/policy/list", method = RequestMethod.POST)
    public RespPage frontGetPolicyList(@RequestBody @Valid  PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                policyService.total(pageReqParam.getFilter()),
                policyService.selectByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value = "/api/console/policy/list", method = RequestMethod.GET)
    public RespPage consoleGetPolicyList(@RequestBody @Valid  PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException {
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                policyService.total(pageReqParam.getFilter()),
                policyService.selectByCondition(pageReqParam.getFilter()));
    }

    @RequestMapping(value="/api/policy/content/{name}", method = RequestMethod.GET)
    public void getPolicyContent(@PathVariable String name, HttpServletResponse response){
        if (policyService.getPolicyContent(name, response) == false){
           logger.error("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/policy/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "institution") String institution,
                               @RequestParam(value = "issueDate") String issueDateStr,
                               MultipartFile file)
            throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException{
        logger.info("upload file with institution=" + institution + "  issueDate=" + issueDateStr);
       //检查用户权限
        if (!permissionService.poliPermissionExist(sessionService.getCurrentUserId())){
            logger.error("upload policy but no permission");
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            logger.error("issue date str error:" + issueDateStr);
            throw new IllegalArgumentException("issue date str error:" + issueDateStr);
        }        //存储文件
        policyService.FileUpload(institution, issueDate, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/policy/state")
    public RespData setState(@RequestBody @Valid PolicyListItem policyListItem, Errors errors)
            throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException{
        argumentError(errors);
        policyService.updateState(policyListItem.getName(), policyListItem.getInstitution(), policyListItem.getState());
        return RespData.ok();
    }
}
