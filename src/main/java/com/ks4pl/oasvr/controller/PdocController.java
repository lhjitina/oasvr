package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.PdocDelInfo;
import com.ks4pl.oasvr.service.PdocService;
import com.ks4pl.oasvr.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class PdocController extends ControllerBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private PdocService pdocService;

    @RequestMapping(value = "/api/front/pdoc/list", method = RequestMethod.POST)
    public RespPage frontGetPdocList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                pdocService.total(pageReqParam.getParam()),
                pdocService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value = "/api/console/pdoc/list", method = RequestMethod.POST)
    public RespPage consoleGetPdocList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
                throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                pdocService.total(pageReqParam.getParam()),
                pdocService.selectByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value="/api/pdoc/content", method = RequestMethod.GET)
    public void getPolicyContent(@RequestParam String name,
                                 @RequestParam String partner,
                                 HttpServletResponse response)
            throws  ServiceException{
        if (pdocService.getPdocContent(name, response) == false){
            logger.error("GetPolicyContent error");
        }
    }

    @RequestMapping(value = "/api/pdoc/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam String partner, MultipartFile file)
            throws ServiceException, SQLIntegrityConstraintViolationException{
        //检查用户权限
        if (!permissionService.docPermissionExist(sessionService.getCurrentUserId())) {
            return RespData.err(RespCode.NO_PERM);
        }
        //存储文件
        pdocService.FileUpload(partner, file);
        return RespData.ok();
    }

    public RespData delete(@RequestBody @Valid  PdocDelInfo pdocDelInfo, Errors errors)
            throws IllegalArgumentException{
        argumentError(errors);
        pdocService.delete(pdocDelInfo.getPartner(), pdocDelInfo.getName());
        return RespData.ok();
    }
}
