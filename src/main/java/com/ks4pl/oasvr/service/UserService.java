package com.ks4pl.oasvr.service;

import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserByTelOrEmail(String userTelOrEmail){
        return userMapper.selectUserByTelOrEmail(userTelOrEmail);
    }
}
