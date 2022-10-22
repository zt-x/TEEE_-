package com.teee.Filter;

import com.teee.service.publicpart.Impl.PowerServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter("/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    boolean check(String uri){
        ArrayList<String> whiteList = new ArrayList<>();
        whiteList.add("/login");
        whiteList.add("/register");
        if(whiteList.contains(uri)){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        System.out.println(hsr.getRequestURI());
        servletRequest.setCharacterEncoding("UTF-8");
        if(check(hsr.getRequestURI())){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            try{
                String token = hsr.getHeader("Authorization");
                if (PowerServiceImpl.isTokenLegal(token)){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    System.out.println("Token ILLEGAL!");
                }
            }catch(NullPointerException npe){
                System.out.println("Token not exist!");

            }
        }

    }

    @Override
    public void destroy() {

    }
}
