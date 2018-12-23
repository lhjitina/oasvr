package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.mapper.PermissionMapper;
import com.ks4pl.oasvr.service.DepartmentService;
import com.ks4pl.oasvr.service.PermissionService;
import com.ks4pl.oasvr.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class BasicDataController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SessionService sessionService;

    public BasicDataController() {
    }


    @RequestMapping(value = "/api/department/list", method= RequestMethod.GET)
    public ArrayList<Department>  getDepartments(){
        return departmentService.selectAll();
    }

}
