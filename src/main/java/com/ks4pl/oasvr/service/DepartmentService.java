package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.mapper.DepartmentMapper;
import com.ks4pl.oasvr.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DepartmentService extends ServiceBase{

    @Autowired
    private DepartmentMapper departmentMapper;

    public ArrayList<Department> selectByCondition(HashMap<String, Object> con, Integer num, Integer size){
        addPageParam(con, num, size);
        return departmentMapper.selectByCondition(con);
    }

    public Boolean isIdValid(Integer id){
        return (departmentMapper.selectById(id) != null);
    }

    public Integer total(HashMap<String, Object> con){
        return departmentMapper.total(con);
    }

}
