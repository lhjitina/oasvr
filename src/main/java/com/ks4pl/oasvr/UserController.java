package com.ks4pl.oasvr;

import org.springframework.web.bind.annotation.*;

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
        return 200;
    }

    @RequestMapping(value = "/api/user/detail", method= RequestMethod.GET)
    public User getUserDetail(@RequestParam(value = "userName", required = true) String userName){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)) {
                return users.get(i);
            }
        }
        return null;
    }

    @RequestMapping(value = "/api/user/list", method= RequestMethod.GET)
    public ArrayList<User> getUsers(String userName, String realName, String tel, String department){
        System.out.println("...get user list...username=" + userName + "...realName=" + realName + "...tel=" + tel + "...dep=" + department);
        ArrayList<User> ret = new ArrayList<>();
        for (int i = 0; i < users.size(); i++){
            System.out.println(users.get(i).toString());
            if ((userName != null) && !userName.isEmpty() && !users.get(i).getUserName().equals(userName)){
                continue;
            }
            System.out.println("...find user with name:"+userName);
            if ((tel != null) && !tel.isEmpty() && !users.get(i).getTel().equals(tel)){
                continue;
            }
            if ((department != null) && !department.isEmpty() && !users.get(i).getDepartment().equals(department)){
                continue;
            }
            System.out.println("....find user:" + users.get(i).toString());
            ret.add(users.get(i));
        }
        return ret;
    }

    @RequestMapping(value = "/api/user/edit", method= RequestMethod.POST)
    public Integer getUserEdit(@RequestBody User user){
        for (User u: users) {
            if (u.getUserName().equals(user.getUserName())) {
                u.setUser(user);
                return 200;
            }
        }
        return null;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public Integer userLogin(@RequestBody User user){
        System.out.println("login user: ${user}");
        return 200;
    }

}
