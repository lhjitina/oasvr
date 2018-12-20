package com.ks4pl.oasvr.controller;


import com.ks4pl.oasvr.MyUtils;
import com.ks4pl.oasvr.entity.Department;

import com.ks4pl.oasvr.entity.Regulation;
import com.ks4pl.oasvr.model.RegulationListItem;
import com.ks4pl.oasvr.service.DepartmentService;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.RegulationService;
import com.ks4pl.oasvr.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegulationController {

    @Autowired
    private RegulationService regulationService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermissionService permissionService;

    public RegulationController() {

    }

    @RequestMapping(value = "/api/regulation/list", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<RegulationListItem> GetRegulations(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "department", required = false) String department,
                                                        @RequestParam(value = "startDate", required = false) String startDate,
                                                        @RequestParam(value = "endDate", required = false) String endDate,
                                                        @RequestParam(value = "state", required = false) String state){

        System.out.println("enter:   /api/regulation/list");
        Map<String, Object> condition = new HashMap<>();
        if (name !=null && !name.trim().isEmpty()){
            condition.put("name", name);
        }
        if (department != null && MyUtils.isNumeric(department)){
            System.out.println("dep="+department+"is number");
            condition.put("departmentId", department);
        }
        if (startDate != null && !startDate.trim().isEmpty()){
            condition.put("startDate", startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()){
            condition.put("endDate", endDate);
        }
        if (state != null && !state.trim().isEmpty()){
            condition.put("state", state);
        }

        System.out.println("conditons:"+condition);
        return regulationService.selectListItemByCondition(condition);
    }

    @RequestMapping(value="/api/regulation/content/{name}", method = RequestMethod.GET)
    public Integer GetRegulationContent(@PathVariable String name, HttpServletResponse response){

        Integer ret = 200;
        if (!regulationService.getRegulationContent(name, response)){
            ret = 201;
        }
        return ret;
    }


    @RequestMapping(value = "/api/regulation/upload", method = RequestMethod.POST)
    @ResponseBody
    public String FileUpload(@RequestParam(value = "department") Integer departmentId,
                             @RequestParam(value = "issueDate") String issueDateStr,
                             MultipartFile file){

        System.out.println("upload file with department id=" + departmentId + "  issueDate=" + issueDateStr);

        //检查用户是否登录
        if (sessionService.getCurrentUserId() == 0){
            return "no login";
        }

        //检查用户权限
        if (!permissionService.permissionExist(sessionService.getCurrentUserId(), departmentId)){
            return "no permission";
        }


        //存储文件
        if (!regulationService.FileUpload(file)){
             return "fail";
        }

        //转换并检查发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = sdf.parse(issueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "fail";
        }

        //存入数据库
        Regulation regulation = new Regulation();
        regulation.setName(file.getOriginalFilename());
        regulation.setDepartment(departmentId);
        regulation.setIssueDate(issueDate);
        regulation.setState("有效");
        regulation.setOperatorId(sessionService.getCurrentUserId());
        regulation.setOperateTime(new Timestamp(System.currentTimeMillis()));

        if (regulationService.insert(regulation) == 0){
            return "fail";
        }
        return "ok";
    }
}
