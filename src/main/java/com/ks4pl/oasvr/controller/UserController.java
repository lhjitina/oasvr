package com.ks4pl.oasvr.controller;

import com.ks4pl.oasvr.dto.PageReqParam;
import com.ks4pl.oasvr.dto.RespPage;
import com.ks4pl.oasvr.entity.Permission;
import com.ks4pl.oasvr.entity.User;
import com.ks4pl.oasvr.model.LoginInfo;
import com.ks4pl.oasvr.model.PasswdModify;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.model.SummaryListItem;
import com.ks4pl.oasvr.model.UserListItem;
import com.ks4pl.oasvr.service.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


@RestController
public class UserController extends ControllerBase{
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
    public RespData userAdd(@RequestBody @Valid UserListItem userListItem, Errors errors)
            throws IllegalArgumentException, ServiceException, SQLIntegrityConstraintViolationException {
        argumentError(errors);
        logger.info("add user: " + userListItem.toString());
        User user = User.fromUserListItem(userListItem);
        Permission p = Permission.fromUserListItem(userListItem);
        userService.addUser(user, p);
        return RespData.ok();
    }

    @RequestMapping(value = "/api/user/detail", method= RequestMethod.GET)
    public RespData getUserDetail(@RequestParam(value = "id") Integer uid){
        logger.info("/api/user/detail with id="+uid);
        logger.info(userService.selectUserListItemById(uid).toString());
        return RespData.ok(userService.selectUserListItemById(uid));
    }

    @RequestMapping(value = "/api/user/list", method= RequestMethod.POST)
    public RespPage getUsers(@RequestBody @Valid PageReqParam pageReqParam, Errors errors) throws IllegalArgumentException {
        logger.info(pageReqParam.toString());
        argumentError(errors);
        return RespPage.okPage(pageReqParam.getNum(),
                    pageReqParam.getSize(),
                    userService.total(pageReqParam.getParam()),
                    userService.selectUserListItemByCondition(pageReqParam.getParam(),pageReqParam.getNum(), pageReqParam.getSize())
        );
    }

    @RequestMapping(value = "/api/user/update", method= RequestMethod.POST)
    public RespData updateUser(@RequestBody @Valid UserListItem userListItem, Errors errors)
            throws IllegalArgumentException, SQLIntegrityConstraintViolationException, ServiceException{
        argumentError(errors);
        User user = User.fromUserListItem(userListItem);
        userService.updateById(user);

        Permission p = Permission.fromUserListItem(userListItem);
        permissionService.update(p);

        return RespData.ok();
    }

    @RequestMapping(value = "/api/user/delete", method = RequestMethod.GET)
    public void deleteUser(@RequestParam("id") Integer uid) throws ServiceException{
        logger.info("/api/user/delete uid="+uid);
        userService.deleteUserById(uid);
    }

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public RespData userLogin(@RequestBody @Valid LoginInfo loginInfo, Errors errors,
                              HttpServletResponse response)
                            throws IllegalArgumentException, ServiceException {
        logger.info("login: "+ loginInfo);
        argumentError(errors);
        User u = userService.selectUserByTelOrEmail(loginInfo.getLoginName());

        RespData respData;
        if (u == null){
            respData = RespData.err(RespCode.NO_REGIST);
            logger.error("user name error:" + loginInfo.getLoginName());
        }
        else if (!u.getPasswd().equals(loginInfo.getPasswd())){
            logger.error("passwd error: user=" + loginInfo.getLoginName() +
                "   passwd input is:" + loginInfo.getPasswd() +
                "   right passwd is:" + u.getPasswd());
            respData = RespData.err(RespCode.PASS_ERR);
        }
        else{
            UserListItem userListItem = userService.selectUserListItemById(u.getId());
            response.setHeader("Authorization", sessionService.createToken(u.getId()));
            respData = RespData.ok(userListItem);
            logger.info("the user login success: ", userListItem.toString());
        }
        return respData;
    }

    @RequestMapping(value = "/api/user/passwd/reset", method = RequestMethod.GET)
    public void ResetPasswd(@RequestParam("id") Integer uid) throws ServiceException{
        logger.info("/api/user/passwd/reset uid=" + uid);
        userService.resetPasswd(uid, "123456");
    }

    @RequestMapping(value = "/api/user/passwd/modify", method = RequestMethod.POST)
    public RespData modifyPasswd(@RequestBody @Valid PasswdModify pm, Errors errors)
            throws IllegalArgumentException, ServiceException{
        argumentError(errors);
        User user = userService.selectUserById(sessionService.getCurrentUserId());
        RespData respData;
        if (user == null) {
            respData = RespData.err(RespCode.SERV_ERR);
            logger.info("modify passwd fail, can not find current user with id=" + sessionService.getCurrentUserId());
        }
        else if (!user.getPasswd().equals(pm.getOldp())){
            logger.info("modify passwd fail, input oldp:" + pm.getOldp() +"!= passwd:" + user.getPasswd());
            respData = RespData.err(RespCode.PASS_ERR);
        }
        else{
            userService.modifyPasswd(user.getId(), pm.getNewp());
            respData = RespData.ok();
        }
        return respData;
    }

    @RequestMapping(value = "/api/user/logout", method = RequestMethod.GET)
    public RespData userLogout() throws ServiceException{
        logger.info("user logout uid=" + sessionService.getCurrentUserId());
        return RespData.ok();
    }

    @RequestMapping(value = "/api/user/verifytoken", method = RequestMethod.GET)
    public RespData verifyToken(){
        String token = sessionService.getToken();
        logger.info("verify token: " + token);
        if (!JwtUtil.isValid(token)){
            logger.info("token is invalid:" + token);
            return RespData.err(RespCode.TOKEN_EXP);
        }
        else{
            UserListItem userListItem = userService.selectUserListItemById(Integer.valueOf(JwtUtil.fetchSubject(token)));
            if (userListItem == null){
                logger.info("verify token, user no exist, uid="+ JwtUtil.fetchSubject(token));
                return RespData.err(RespCode.NO_REGIST);
            }
            else{
                logger.info("verify token ok, user:" + userListItem);
                return RespData.ok(userListItem);
            }
        }
    }

    @RequestMapping(value = "/api/user/fuzzy", method = RequestMethod.POST)
    public RespPage fuzzyQuery(@RequestBody @Valid PageReqParam pageReqParam, Errors errors)
            throws IllegalArgumentException {
        logger.info("fuzzyQuery:" + pageReqParam);
        argumentError(errors);
        ArrayList<UserListItem> res = userService.fuzzyQuery(pageReqParam.getParam());
        return RespPage.okPage(pageReqParam.getNum(),
                pageReqParam.getSize(),
                res.size(), res);
    }
}
