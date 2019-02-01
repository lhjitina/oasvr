package com.ks4pl.oasvr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ks4pl.oasvr.dto.RespData;
import com.ks4pl.oasvr.dto.RespCode;
import com.ks4pl.oasvr.service.JwtUtil;
import com.ks4pl.oasvr.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@WebFilter(urlPatterns = "/api/*", filterName = "ksfilter")
public class KsoaFilter implements Filter {
    private static Logger logger = LogManager.getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);
    }
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String url = request.getRequestURI();
//        String token = request.getHeader("authorization");
//
//        if (url.equals("/api/user/login")){
//            System.out.println("...login.....");
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//        else if (token == null || token.trim().isEmpty()) {
//            logger.info("request token is null or empty, url=" + url);
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        }
//        else if (!JwtUtil.isValid(token)) {
//            logger.info("token is invalid: ", token);
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        }
//        else{
//            logger.info("request token is ok, url="+url);
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }

    @Override
    public void destroy() {

    }


}
