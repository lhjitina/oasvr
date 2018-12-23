package com.ks4pl.oasvr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ks4pl.oasvr.MyUtils;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.model.UserListItem;
import com.ks4pl.oasvr.service.SessionService;
import com.ks4pl.oasvr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    private ArrayList<User> users;

    public UserController() {
        users = new ArrayList<>();
    }

    @RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
    public void userAdd(@RequestBody User user) {

        System.out.println("add user: " + user.toString());
        user.setPasswd("123456");
        user.setRegistTime(new Timestamp(System.currentTimeMillis()));
        user.setState("启用");
        userService.insert(user);
    }

    @RequestMapping(value = "/api/user/detail", method= RequestMethod.GET)
    public User getUserDetail(@RequestParam(value = "id") Integer uid){
        System.out.println("/api/user/detail with id="+uid);
        return userService.selectUserById(uid);
    }

    @RequestMapping(value = "/api/user/list", method= RequestMethod.GET)
    public ArrayList<UserListItem> getUserList(String userName, String departmentId, String tel, String email, String state){
        Map<String, Object> condition = new HashMap<>();
        if ((userName != null) && !userName.trim().isEmpty()){
            condition.put("name", userName);
        }
        if ((departmentId !=null) && !departmentId.trim().isEmpty()){
            condition.put("departmentId", departmentId);
        }
        if ((tel != null) && !tel.trim().isEmpty()){
            condition.put("tel", tel);
        }
        if ((email != null) && !email.trim().isEmpty()){
            condition.put("email", email);
        }
        if ((state != null) && !state.trim().isEmpty()){
            condition.put("state", state);
        }

        System.out.println("get user list with condition: " + condition);
        return userService.selectUserListItemByCondition(condition);
    }

    @RequestMapping(value = "/api/user/update", method= RequestMethod.POST)
    public Integer getUserEdit(@RequestBody User user){
        System.out.println("/api/user/update" + user);
        return userService.updateById(user);
    }

    @RequestMapping(value = "/api/user/delete", method = RequestMethod.GET)
    public Integer deleteUser(@RequestParam("id") Integer uid){
        System.out.println("/api/user/delete uid="+uid);
        return userService.deleteUserById(uid);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public Integer userLogin(@RequestBody String params){
        Integer ret = 200;
        JSONObject jsonObject = JSONObject.parseObject(params);
        String loginName = jsonObject.getString("loginName");
        String passwd = jsonObject.getString("passwd");

        System.out.println("loginame:"+loginName + "  passwd:"+passwd);
        User u = userService.selectUserByTelOrEmail(loginName);
        if (u == null){
            System.out.println("user name error:" + loginName);
            ret = 201;
        }
        else if (!u.getPasswd().equals(passwd)){
            System.out.println("passwd error: user=" + loginName +
                "   passwd input is:" + passwd +
                "   right passwd is:" + u.getPasswd());
            ret = 202;
        }
        else if (sessionService.getCurrentUserId() != 0){
            System.out.println("the user has login, repeat login error");
            ret = 203;
        }
        else{
           sessionService.saveUserInfo(1, u.getName());
        }
        return ret;
    }

    @RequestMapping(value = "/api/user/passwd/reset", method = RequestMethod.GET)
    public Integer ResetPasswd(@RequestParam("id") Integer uid){
        return userService.resetPasswd(uid, "123456");
    }

}
