package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.MyUtils;
import com.ks4pl.oasvr.controller.IllegalArgumentException;
import com.ks4pl.oasvr.entity.Permission;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.mapper.PermissionMapper;
import com.ks4pl.oasvr.mapper.UserListItemMapper;
import com.ks4pl.oasvr.mapper.UserMapper;
import com.ks4pl.oasvr.model.UserListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Service
public class UserService extends ServiceBase{
    private static Logger logger = LogManager.getLogger();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserListItemMapper userListItemMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    private final String defaultPasswd = "123456";

    private void validateQueryParam(Map<String, Object> con) throws IllegalArgumentException {
        if (con.get("departmentId") != null && !MyUtils.isNumeric(con.get("departmentId").toString())){
                throw new IllegalArgumentException("department id should be number");
        }
    }

    public User selectUserByTelOrEmail(String userTelOrEmail){
        return userMapper.selectUserByTelOrEmail(userTelOrEmail);
    }
    public User selectUserById(Integer uid){
        return userMapper.slectUserById(uid);
    }
    public UserListItem selectUserListItemById(Integer uid){
        return userListItemMapper.selectById(uid);
}

    public ArrayList<UserListItem> selectUserListItemByCondition(Map<String, Object> condition, int num, int size) throws IllegalArgumentException {
        delBlankParam(condition);
        validateQueryParam(condition);
        addPageParam(condition, num, size);
        return userListItemMapper.selectByCondition(condition);
    }

    public Integer total(Map<String, Object> condition){
        return userListItemMapper.total(condition);
    }

    @Transactional
    public void addUser(User u, Permission p) throws ServiceException, SQLIntegrityConstraintViolationException {
        u.setPasswd(defaultPasswd);
        u.setRegistTime(new Timestamp(System.currentTimeMillis()));
        if (userMapper.insert(u) == 0){
            logger.info("insert user error");
            throw new ServiceException("insert user fail:" + u.toString());
        }
        logger.info("insert user return id="+u.getId());
        p.setUid(u.getId());
        if (permissionMapper.insert(p) == 0){
            throw new ServiceException("inert permission into database fail, new user id="+u.getId());
        }
    }

    public void updateById(User u) throws ServiceException, SQLIntegrityConstraintViolationException {
        if (userMapper.updateById(u) == 0){
            throw new ServiceException("update user fail:" + u.toString());
        }
    }

    public void deleteUserById(Integer uid) throws ServiceException{
        if (userMapper.deleteById(uid) == 0){
            throw new ServiceException("delete user fail:" + uid);
        }
    }

    public void resetPasswd(Integer uid, String passwd) throws ServiceException{
        if (userMapper.updatePasswdById(uid, passwd) == 0){
            throw new ServiceException("reset passwd fail, uid:" + uid + ";passwd=" + passwd);
        }
    }

    public void modifyPasswd(Integer uid, String passwd) throws ServiceException{
        if (userMapper.updatePasswdById(uid, passwd) == 0){
            throw new ServiceException("modify passwd fail, uid:" + uid + ";passwd:" + passwd);
        }
    }

    public ArrayList<UserListItem> fuzzyQuery(Map<String, Object> condition){
        String[] keys = condition.get("keys").toString().split(" |,|，");
        return userListItemMapper.fuzzyQuery(keys);
    }
}
