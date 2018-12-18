package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BasicDataController {

    @Autowired
    private DepartmentService departmentService;

    public BasicDataController() {
    }


    @RequestMapping(value = "/api/department/list", method= RequestMethod.GET)
    public ArrayList<Department>  getDepartments(){
        return departmentService.getAll();
    }
}
