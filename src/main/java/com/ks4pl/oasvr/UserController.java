package com.ks4pl.oasvr;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class UserController {

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

        user.setUserId(rd.nextInt());
        users.add(user);
        logUsers();
        return user.getUserId();
    }




}
