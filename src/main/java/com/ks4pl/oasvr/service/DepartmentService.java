package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.mapper.DepartmentMapper;
import com.ks4pl.oasvr.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public ArrayList<Department> selectAll(){
        return departmentMapper.selectAll();
    }

    public Department selectByName(String name){
        if (name == null)
            return null;
        return departmentMapper.selectByName(name);
    }

    public Boolean isIdValid(Integer id){
        return (departmentMapper.selectById(id) != null);
    }



}
