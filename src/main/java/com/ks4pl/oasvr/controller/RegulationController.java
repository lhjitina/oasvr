package com.ks4pl.oasvr.controller;


import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RegulationController extends ControllerBase {
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private RegulationService regulationService;

    @RequestMapping(value = "/api/regulation/list", method = RequestMethod.POST)
    public RespPage consoleGetRegulationList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException {
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                regulationService.total(pageReqParam.getParam()),
                regulationService.selectListItemByCondition(pageReqParam.getParam()));
    }

    @RequestMapping(value = "/api/regulation/content/{name}", method = RequestMethod.GET)
    public void GetRegulationContent(@PathVariable String name, HttpServletResponse response)
            throws ServiceException{
        if (regulationService.getRegulationContent(name, response) == false) {
            logger.error("GetRegulationContent error");
        }
    }

    @RequestMapping(value = "/api/regulation/upload", method = RequestMethod.POST)
    public RespData FileUpload(@RequestParam(value = "department") Integer departmentId,
                               @RequestParam(value = "issueDate") String issueDateStr,
                               MultipartFile file)
            throws IllegalArgumentException, ServiceException {
        //检查用户权限
        if (!permissionService.regPermissionExist(sessionService.getCurrentUserId())) {
            return RespData.err(RespCode.NO_PERM);
        }
        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("issudate error:" + issueDateStr);
        }        //存储文件
        regulationService.FileUpload(departmentId, issueDate, file);
        return RespData.ok();
    }

    @PostMapping(value = "/api/regulation/state")
    public void setRegulationState(@RequestBody @Valid RegulationListItem regulationListItem, Errors errors)
            throws IllegalArgumentException, ServiceException {
        argumentError(errors);
        regulationService.updateState(regulationListItem.getName(),
                regulationListItem.getDepartmentId(),
                regulationListItem.getState());
    }
}
