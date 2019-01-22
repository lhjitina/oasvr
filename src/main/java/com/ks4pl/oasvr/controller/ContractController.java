package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.ContractTemplateListItem;
import com.ks4pl.oasvr.service.ContractService;
import com.ks4pl.oasvr.service.ContractTemplateService;
import com.ks4pl.oasvr.service.ServiceException;
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
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ContractService contractService;


    @RequestMapping(value = "/api/contract/list", method = RequestMethod.POST)
    public RespPage getContractList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                contractService.total(pageReqParam.getParam()),
                contractService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value="/api/contract/content/{name}", method = RequestMethod.GET)
    public void getContractContent(@PathVariable String name, HttpServletResponse response)
            throws ServiceException {
        if (contractService.getContractContent(name, response) == false){
            logger.error("getContractContent error");
        }
    }

    @RequestMapping(value = "/api/contract/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam String partner,
                               @RequestParam String start,
                               @RequestParam String end,
                               @RequestParam String digest,
                               @RequestParam String type,
                               @RequestParam Integer autoRenewal,
                               @RequestBody  MultipartFile file)
            throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException {
        //检查用户权限
        logger.info("/api/contract/upload:.. {}", file.getOriginalFilename());
        if (!permissionService.cwPermissionExist(sessionService.getCurrentUserId())){
            logger.info("user(id={}) no permission", sessionService.getCurrentUserId());
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null, endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            throw new IllegalArgumentException("date invalide:" + start + " or " + end);
        }
        //存储文件
        contractService.FileUpload(partner, type, startDate, endDate, autoRenewal, digest, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/contract/delete")
    public RespData setContractState(@RequestParam String name){
        logger.info("/api/contract/delete: {}", name);
        contractService.deleteCon(name);
        return RespData.ok();
    }

}
