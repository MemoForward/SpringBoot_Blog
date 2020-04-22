package com.memoforward.blog.controller;

import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.model.User;
import com.memoforward.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    IUserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("email") String email,
                           @RequestParam(value = "avatar", defaultValue = "/images/tra_avatar.jpg") String avatar,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model) {
        try {
            if (username == null || password == null ||
                    email == null) {
                throw new CustomizedBlogException(BlogError.RESOURCES_NOT_FOUND);
            }
            User user = userService.findUserByUsername(username);
            if (user != null) {
                model.addAttribute("username", username);
                model.addAttribute("password", password);
                model.addAttribute("nickname", nickname);
                model.addAttribute("check_password", password);
                model.addAttribute("email", email);
                model.addAttribute("error", "用户名已存在!");
                return "register";
            } else {
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setAvatar(avatar);
                user.setEmail(email);
                // 注册为会员
                user.setType(1);
                user.setNickname((nickname == null || "".equals(nickname)) ? user.getUsername() : nickname);
                user.setCreateTime(System.currentTimeMillis());
                user.setModifiedTime(user.getCreateTime());
                userService.saveUser(user);
                request.getSession().setAttribute("user",user);
                Cookie cookie = new Cookie("blogUser", user.getUsername());
                cookie.setMaxAge(60 * 24 * 24 * 15);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }
        } catch (CustomizedBlogException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception ex) {
            throw ex;
        }
    }
}
