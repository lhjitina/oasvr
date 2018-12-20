package com.ks4pl.oasvr;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/*", filterName = "ksfilter")
public class KsoaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        return;

/*        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        if (url.equals("/api/login")){
            System.out.println("...login in.....");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if (session.isNew() || userName==null ){
            HttpServletResponse response= (HttpServletResponse)servletResponse;
            response.sendError(550);
            response.setStatus(560);
            return;
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
        */
    }

    @Override
    public void destroy() {

    }


}
