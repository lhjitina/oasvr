package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.MyUtils;
import com.ks4pl.oasvr.controller.IllegalArgumentException;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.mapper.UserListItemMapper;
import com.ks4pl.oasvr.mapper.UserMapper;
import com.ks4pl.oasvr.model.UserListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserService extends ServiceBase{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserListItemMapper userListItemMapper;

    private void validateQueryParam(HashMap<String, Object> con) throws IllegalArgumentException {
        ArrayList<String> ks = new ArrayList<>();
        ks.addAll(con.keySet());
        for (String k : ks){
            if (con.get(k).toString().trim().isEmpty()){
                con.remove(k);
                System.out.println("remove empty key:"+ k);
            }
        }
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

    public ArrayList<UserListItem> selectUserListItemByCondition(HashMap<String, Object> condition, int num, int size) throws IllegalArgumentException {
        validateQueryParam(condition);
        addPageParam(condition, num, size);
        return userListItemMapper.selectByCondition(condition);
    }

    public Integer total(HashMap<String, Object> condition){
        return userListItemMapper.total(condition);
    }

    public void addUser(User u) throws ServiceException, SQLIntegrityConstraintViolationException {
        u.setPasswd("123456");
        u.setRegistTime(new Timestamp(System.currentTimeMillis()));
        u.setState("启用");
        if (userMapper.insert(u) == 0){
            throw new ServiceException("insert user fail:" + u.toString());
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

    public Integer getLastInsertId(){
        return userMapper.getLastInsertId();
    }

    public void modifyPasswd(Integer uid, String passwd) throws ServiceException{
        if (userMapper.updatePasswdById(uid, passwd) == 0){
            throw new ServiceException("modify passwd fail, uid:" + uid + ";passwd:" + passwd);
        }
    }
}
