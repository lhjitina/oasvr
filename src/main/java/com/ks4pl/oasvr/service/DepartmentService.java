package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.mapper.DepartmentMapper;
import com.ks4pl.oasvr.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public ArrayList<Department> getAll(){
        return departmentMapper.selectAll();
    }
}
