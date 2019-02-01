package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.entity.Contract;
import com.ks4pl.oasvr.model.ContractDelete;
import com.ks4pl.oasvr.model.ContractTemplateListItem;
import com.ks4pl.oasvr.model.ContractUpdate;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public RespData deleteContract(@RequestBody ContractDelete contractDelete){
        logger.info("/api/contract/delete: {}", contractDelete.toString());
        contractService.deleteCon(contractDelete.getName());
        return RespData.ok();
    }

    @PostMapping(value = "/api/contract/update")
    public RespData updateContract(@RequestBody ContractUpdate contractUpdate)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        logger.info("/api/constract/update: " + contractUpdate.toString());
        contractUpdate.setOperatorId(sessionService.getCurrentUserId());
        contractUpdate.setOperateTime(new Timestamp(System.currentTimeMillis()));
        if (contractService.updateCon(contractUpdate) == 0){
            logger.error("update contract error: " + contractUpdate.toString());
            return RespData.err(RespCode.SERV_ERR);
        }
        return RespData.ok();
    }

    @RequestMapping(value = "/api/contract/fuzzy", method = RequestMethod.POST)
    public RespPage fuzzyQuery(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        logger.info("fuzzyQuery:" + pageReqParam);
        argumentError(errors);
        ArrayList<Contract> res = contractService.fuzzyQuery(pageReqParam.getParam());
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                res.size(), res);
    }

}
