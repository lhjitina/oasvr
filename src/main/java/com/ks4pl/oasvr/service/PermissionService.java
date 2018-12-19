package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public ArrayList<Department> getPermissionDeprtmentByUser(Integer userId){
        return permissionMapper.selectPermissionDepartmentByUser(userId);
    }
}
