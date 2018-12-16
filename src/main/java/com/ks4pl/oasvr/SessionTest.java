package com.ks4pl.oasvr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionTest {

    SessionTest(){

    }
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Map<String, String> createSession(HttpServletRequest request) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        String username;
        HttpSession session = request.getSession();

        if (session.isNew()){
            System.out.println(".....create a new sssion.....");
            session.setAttribute("userName", "lhj");
            username = "new a name";
        }
        else{
            username = (String) session.getAttribute("userName");
            if (username == null){
                session.setAttribute("userName", "lhj");
                username = "new a name";
            }
            System.out.println("....seesion exist, username is :"+username);
        }
        map.put("username", username);

        return map;

    }
}
