package com.memoforward.blog.interceptor;

import com.memoforward.blog.model.User;
import com.memoforward.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    IUserService userService;

    @Override
    // 不知道为什么请求返回时会执行很多很多次查询
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) return true;
            for (Cookie cookie : cookies) {
                if ("blogUser".equals(cookie.getName())) {
                    String username = cookie.getValue();
                    User user = userService.findUserByUsername(username);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return true;
    }
}
