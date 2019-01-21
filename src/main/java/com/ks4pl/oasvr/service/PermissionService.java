package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.Department;
import com.ks4pl.oasvr.entity.Permission;
import com.ks4pl.oasvr.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Boolean regPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission != null && permission.getReg() == 1);
        return bExist;
    }

    public Boolean poliPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission.getPol() == 1);
        return bExist;
    }

    public Boolean sumPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission.getSum() == 1);
        return bExist;
    }

    public Boolean docPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission.getDoc() == 1);
        return bExist;
    }

    public Boolean usrPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission.getUsr() == 1);
        return bExist;
    }

    public Boolean conTemplatePermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission != null && permission.getCon() == 1);
        return bExist;
    }
    public Boolean cwPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission != null && permission.getCw() == 1);
        return bExist;
    }
    public Boolean crPermissionExist(Integer uid){
        Boolean bExist = false;
        Permission permission = permissionMapper.selectPermissionByUser(uid);
        bExist = (permission != null && permission.getCr() == 1);
        return bExist;
    }
    public void addPerm(Permission p) throws SQLIntegrityConstraintViolationException {
         permissionMapper.insert(p);
    }

    public Integer update(Permission p){
        return permissionMapper.update(p);
    }
}
