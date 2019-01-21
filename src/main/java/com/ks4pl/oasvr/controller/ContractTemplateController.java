package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.ContractTemplateListItem;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ContractTemplateController extends ControllerBase{
    private static final Logger logger = LogManager.getLogger(ContractTemplateController.class);

    @Autowired
    private ContractTemplateService contractTemplateService;


    @RequestMapping(value = "/api/front/contracttemplate/list", method = RequestMethod.POST)
    public RespPage frontGetContractList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                contractTemplateService.total(pageReqParam.getParam()),
                contractTemplateService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value = "/api/console/contracttemplate/list", method = RequestMethod.POST)
    public RespPage consoleGetContractList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        logger.info("/api/console/contracttemplate/list: " + pageReqParam.toString());
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                contractTemplateService.total(pageReqParam.getParam()),
                contractTemplateService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value="/api/contracttemplate/content/{name}", method = RequestMethod.GET)
    public void getContractContent(@PathVariable String name, HttpServletResponse response)
            throws ServiceException{
        if (contractTemplateService.getContractTemplateContent(name, response) == false){
            logger.error("getcontracttemplateContent error");
        }
    }

    @RequestMapping(value = "/api/contracttemplate/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "issueDate") String issueDateStr, MultipartFile file)
        throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException{
        //检查用户权限
        logger.info("/api/contracttemplate/upload:{} ... {}", issueDateStr, file.getOriginalFilename());
        if (!permissionService.conTemplatePermissionExist(sessionService.getCurrentUserId())){
            logger.info("user(id={}) no permission", sessionService.getCurrentUserId());
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("issue date invalide:" + issueDateStr);
        }
        //存储文件
        contractTemplateService.FileUpload(issueDate, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/contracttemplate/state")
    public void setContractState(@RequestBody @Valid ContractTemplateListItem contractTemplateListItem, Errors errors)
            throws IllegalArgumentException, SQLIntegrityConstraintViolationException, ServiceException{
        logger.info("/api/contracttemplate/state: {}", contractTemplateListItem.toString());
        argumentError(errors);
        contractTemplateService.updateState(contractTemplateListItem.getName(), contractTemplateListItem.getState());
    }
}
