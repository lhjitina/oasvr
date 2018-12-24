package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.mapper.UserListItemMapper;
import com.ks4pl.oasvr.mapper.UserMapper;
import com.ks4pl.oasvr.model.UserListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserListItemMapper userListItemMapper;

    public User selectUserByTelOrEmail(String userTelOrEmail){
        return userMapper.selectUserByTelOrEmail(userTelOrEmail);
    }

    public UserListItem selectUserListItemById(Integer uid){
        return userListItemMapper.selectById(uid);
}

    public ArrayList<UserListItem> selectUserListItemByCondition(Map<String, Object> condition){
        return userListItemMapper.selectByCondition(condition);
    }

    public Integer insert(User u){
        return userMapper.insert(u);
    }

    public Integer updateById(User u){
        return userMapper.updateById(u);
    }

    public Integer deleteUserById(Integer uid){
        return userMapper.deleteById(uid);
    }

    public Integer resetPasswd(Integer uid, String passwd){
        return userMapper.updatePasswdById(uid, passwd);
    }

    public Integer getLastInsertId(){
        return userMapper.getLastInsertId();
    }
}
