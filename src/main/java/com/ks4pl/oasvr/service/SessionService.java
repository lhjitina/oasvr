package com.ks4pl.oasvr.service;

import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class SessionService {

    @Autowired
    private HttpServletRequest request;

    public Integer getCurrentUserId() throws ServiceException{
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()){
            throw new ServiceException("request no token");
        }
        Integer userId;
        try {
            userId = Integer.valueOf(JwtUtil.fetchSubject(token));
        }
        catch (NumberFormatException e){
            throw new ServiceException("token's uid is not number:" + JwtUtil.fetchSubject(token));
        }
//        HttpSession session = request.getSession();
//        Integer userId = 0;
//        if (session.getAttribute("userId") != null){
//            userId = (Integer)session.getAttribute("userId");
//        }
        return userId;
    }

    public String createToken(Integer uid){
        return JwtUtil.token(uid);
    }
}
