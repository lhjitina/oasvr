package com.ks4pl.oasvr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.service.SessionService;
import com.ks4pl.oasvr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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

    private void logUsers(){
        users.forEach(user -> System.out.println(user.toString()));
    }

    @RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
    public Integer userAdd(@RequestBody User user) {

        Random rd = new Random();

        user.setId(rd.nextInt());
        users.add(user);
        logUsers();
        return 200;
    }

    @RequestMapping(value = "/api/user/detail", method= RequestMethod.GET)
    public User getUserDetail(@RequestParam(value = "userName", required = true) String userName){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getTel().equals(userName)) {
                return users.get(i);
            }
        }
        return null;
    }

    @RequestMapping(value = "/api/user/list", method= RequestMethod.GET)
    public ArrayList<User> getUsers(String userName, String realName, String tel, String department){
         return null;
    }

    @RequestMapping(value = "/api/user/edit", method= RequestMethod.POST)
    public Integer getUserEdit(@RequestBody User user){
         return null;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public Integer userLogin(@RequestBody String params){
        Integer ret = 200;
        JSONObject jsonObject = JSONObject.parseObject(params);
        String loginName = jsonObject.getString("loginName");
        String passwd = jsonObject.getString("passwd");

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

}
