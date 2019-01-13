package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.ContractListItem;
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
public class ContractController extends ControllerBase{
    private static final Logger logger = LogManager.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;


    @RequestMapping(value = "/api/front/contract/list", method = RequestMethod.POST)
    public RespPage frontGetContractList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                contractService.total(pageReqParam.getParam()),
                contractService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value = "/api/console/contract/list", method = RequestMethod.POST)
    public RespPage consoleGetContractList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                contractService.total(pageReqParam.getParam()),
                contractService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value="/api/contract/content/{name}", method = RequestMethod.GET)
    public void getContractContent(@PathVariable String name, HttpServletResponse response)
            throws ServiceException{
        if (contractService.getContractContent(name, response) == false){
            logger.error("getContractContent error");
        }
    }

    @RequestMapping(value = "/api/contract/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "issueDate") String issueDateStr, MultipartFile file)
        throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException{
        //检查用户权限
        logger.info("/api/contract/upload:{} ... {}", issueDateStr, file.getOriginalFilename());
        if (!permissionService.conPermissionExist(sessionService.getCurrentUserId())){
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
        contractService.FileUpload(issueDate, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/contract/state")
    public void setContractState(@RequestBody @Valid ContractListItem contractListItem, Errors errors)
            throws IllegalArgumentException, SQLIntegrityConstraintViolationException, ServiceException{
        logger.info("/api/contract/state: {}", contractListItem.toString());
        argumentError(errors);
        contractService.updateState(contractListItem.getOperatorName(),contractListItem.getState());
    }
}
