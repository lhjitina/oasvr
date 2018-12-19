package com.ks4pl.oasvr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class SessionService {

    @Autowired
    private HttpServletRequest request;

    public Integer getCurrentUserId(){
        HttpSession session = request.getSession();
        Integer userId = 0;
        if (session.getAttribute("userId") != null){
            userId = (Integer)session.getAttribute("userId");
        }
        return userId;
    }

    public String getCurrentUserName(){
        HttpSession session = request.getSession();
        String name = null;
        if (session.getAttribute("userName") != null){
            name = (String)session.getAttribute("userName");
        }
        return name;
    }

    public void saveUserInfo(Integer id, String name){
        HttpSession session = request.getSession();
        session.setAttribute("userId", id);
        session.setAttribute("userName", name);
    }

}
