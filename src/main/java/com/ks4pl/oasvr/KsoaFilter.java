package com.ks4pl.oasvr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ks4pl.oasvr.model.RespData;
import com.ks4pl.oasvr.model.RespCode;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@WebFilter(urlPatterns = "/api/*", filterName = "ksfilter")
public class KsoaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        String token = request.getHeader("authorization");

        if (url.equals("/api/login")){
            System.out.println("...login in.....");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (token == null) {
            System.out.println("token is null");
            RespData respData = RespData.err(RespCode.NO_TOKEN);
            JSONObject respJson = (JSONObject) JSON.toJSON(respData);
            PrintWriter pw = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            pw.print(respJson);
            pw.close();
        }
        else{
            System.out.println("token is ok");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }


}
