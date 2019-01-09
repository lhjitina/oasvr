package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BasicDataController extends ControllerBase{

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/api/department/list", method= RequestMethod.POST)
    public RespPage getDepartments(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                departmentService.total(pageReqParam.getParam()),
                departmentService.selectByCondition(pageReqParam.getParam(), pageReqParam.getNum(), pageReqParam.getSize()));
    }

    @RequestMapping(value = "/api/departments", method = RequestMethod.POST)
    public RespPage getDepartmentList(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
        throws IllegalArgumentException{
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                departmentService.total(pageReqParam.getParam()),
                departmentService.selectByCondition(pageReqParam.getParam(), pageReqParam.getNum(), pageReqParam.getSize()));
    }
}
