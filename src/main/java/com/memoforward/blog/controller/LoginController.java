package com.memoforward.blog.controller;

import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.model.User;
import com.memoforward.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    IUserService userService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        if(request.getSession().getAttribute("blogUser") != null){
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes attributes) {
        try {
            User user = userService.checkUser(username, password);
            user.setPassword(null);
            request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie("blogUser", user.getUsername());
            cookie.setMaxAge(60 * 24 * 24 * 15);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } catch (CustomizedBlogException e) {
            attributes.addFlashAttribute("error", e.getMessage());
//            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/user/login";
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/traLogin")
    public String traLogin(HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            User user = new User();
            long num = userService.countUsers();
            user.setAvatar("/images/tra_avatar.jpg");
            user.setNickname("游客" + String.valueOf(num + 1));
            // 注册为游客
            user.setType(2);
            user.setPassword("");
            String username = UUID.randomUUID().toString();
            user.setUsername(username);
            user.setCreateTime(System.currentTimeMillis());
            user.setModifiedTime(user.getCreateTime());
            userService.saveUser(user);
            request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie("blogUser", username);
            cookie.setMaxAge(60 * 24 * 24 * 15);
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch(Exception ex){
            throw ex;
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("blogUser".equals(cookie.getName())){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        return "redirect:/";
    }
}
