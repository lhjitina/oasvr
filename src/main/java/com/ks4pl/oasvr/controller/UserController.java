package com.ks4pl.oasvr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ks4pl.oasvr.OasvrApplication;
import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.entity.Permission;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.model.PasswdModify;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.model.UserListItem;
import com.ks4pl.oasvr.service.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;


@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
    public RespData userAdd(@RequestBody @Valid UserListItem userListItem, Errors errors)
            throws ParamException,ServiceException, SQLIntegrityConstraintViolationException {
        if (errors.hasErrors()){
            throw new ParamException(errors.getAllErrors().toString());
        }
        System.out.println("add user: " + userListItem.toString());
        User user = User.fromUserListItem(userListItem);
        userService.addUser(user);
        Permission p = Permission.fromUserListItem(userListItem);
        p.setUid(userService.getLastInsertId());
        permissionService.addPerm(p);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/user/detail", method= RequestMethod.GET)
    public RespData getUserDetail(@RequestParam(value = "id") Integer uid){
        System.out.println("/api/user/detail with id="+uid);
        return RespData.ok(userService.selectUserListItemById(uid));
    }

    @RequestMapping(value = "/api/user/list", method= RequestMethod.POST)
    public RespPage getUsers(@RequestBody @Valid PageReqParam pageReqParam, Errors errors) throws ParamException{
        System.out.println(pageReqParam.toString());
        logger.info("/api/user/list");
        logger.error("error log......");
        if (errors.hasErrors()){
            throw new ParamException(errors.getAllErrors().toString());
        }
        return RespPage.okPage(pageReqParam.getNum(),
                    pageReqParam.getSize(),
                    userService.total(pageReqParam.getFilter()),
                    userService.selectUserListItemByCondition(pageReqParam.getFilter(),pageReqParam.getNum(), pageReqParam.getSize())
        );
    }

//    @RequestMapping(value = "/api/user/list", method= RequestMethod.GET)
//    public RespData getUserList(String userName, String departmentId, String tel, String email, String state){
//        Map<String, Object> condition = new HashMap<>();
//        if ((userName != null) && !userName.trim().isEmpty()){
//            condition.put("name", userName);
//        }
//        if ((departmentId !=null) && !departmentId.trim().isEmpty() && MyUtils.isNumeric(departmentId)){
//            condition.put("departmentId", departmentId);
//        }
//        if ((tel != null) && !tel.trim().isEmpty()){
//            condition.put("tel", tel);
//        }
//        if ((email != null) && !email.trim().isEmpty()){
//            condition.put("email", email);
//        }
//        if ((state != null) && !state.trim().isEmpty()){
//            condition.put("state", state);
//        }
//
//        System.out.println("get user list with condition: " + condition);
//        ArrayList<UserListItem> userListItems = userService.selectUserListItemByCondition(condition);
//        System.out.println(userListItems);
//        RespData respData = RespData.ok(userService.selectUserListItemByCondition(condition));
//        if (respData.getData() == null){
//            respData.setCode(RespCode.SERV_ERR);
//        }
//        return respData;
//    }

    @RequestMapping(value = "/api/user/update", method= RequestMethod.POST)
    public Integer getUserEdit(@RequestBody UserListItem userListItem){
        System.out.println("/api/user/update" + userListItem.toString());
        User user = User.fromUserListItem(userListItem);
        Integer ret = 1;
        if (userService.updateById(user) == 0){
            System.out.println("update user fail");
            ret = 0;
        }
        else{
            Permission p = Permission.fromUserListItem(userListItem);
            if (permissionService.update(p) == 0){
                System.out.println("update permission fail: " + userListItem.toString());
                ret = 0;
            }
        }
        return  ret;
    }

    @RequestMapping(value = "/api/user/delete", method = RequestMethod.GET)
    public Integer deleteUser(@RequestParam("id") Integer uid){
        System.out.println("/api/user/delete uid="+uid);
        return userService.deleteUserById(uid);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public RespData userLogin(@RequestBody String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        String loginName = jsonObject.getString("loginName");
        String passwd = jsonObject.getString("passwd");
        OasvrApplication.logger.info("user login....");
        System.out.println("loginame:"+loginName + "  passwd:"+passwd);
        User u = userService.selectUserByTelOrEmail(loginName);
        UserListItem userListItem = userService.selectUserListItemById(u.getId());
        RespData respData = null;
        if (u == null){
            respData = RespData.err(RespCode.NO_REGIST);
            System.out.println("user name error:" + loginName);
        }
        else if (!u.getPasswd().equals(passwd)){
            System.out.println("passwd error: user=" + loginName +
                "   passwd input is:" + passwd +
                "   right passwd is:" + u.getPasswd());
            respData = RespData.err(RespCode.PASS_ERR);
        }
        else if (sessionService.getCurrentUserId() != 0){
            System.out.println("the user has login, repeat login error");
            sessionService.saveUserInfo(u.getId(), u.getName());
            respData = RespData.err(RespCode.RELOGIN);
        }
        else{
            System.out.println("the user login success");
            sessionService.saveUserInfo(u.getId(), u.getName());
            respData = RespData.ok(userListItem);
        }
        return respData;
    }

    @RequestMapping(value = "/api/user/passwd/reset", method = RequestMethod.GET)
    public Integer ResetPasswd(@RequestParam("id") Integer uid){
        System.out.println("/api/user/passwd/reset uid=" + uid);
        return userService.resetPasswd(uid, "123456");
    }

    @RequestMapping(value = "/api/user/passwd/modify", method = RequestMethod.POST)
    public Integer modifyPasswd(@RequestBody PasswdModify pm){
        Integer ret = 200;
        User user = userService.selectUserById(sessionService.getCurrentUserId());
        if (user == null) {
            System.out.println("modify passwd fail, can not find current user with id=" + sessionService.getCurrentUserId());
            ret = 201;
        }
        else if (!user.getPasswd().equals(pm.getOldp())){
            System.out.println("modify passwd fail, input oldp:" + pm.getOldp() +"!= passwd:" + user.getPasswd());
            ret = 202;
        }
        else if (userService.modifyPasswd(user.getId(), pm.getNewp()) == 0){
            System.out.println("modify passwd fail, database return 0");
            ret = 203;
        }
        else{
            System.out.println("modify passwd success");
        }
        return ret;
    }

    @RequestMapping(value = "/api/user/logout", method = RequestMethod.GET)
    public Integer userLogout(){
        System.out.println("user logout uid=" + sessionService.getCurrentUserId() + "  name="+sessionService.getCurrentUserName());
        sessionService.deleteCurrentUserInfo();
        return 200;
    }

}
