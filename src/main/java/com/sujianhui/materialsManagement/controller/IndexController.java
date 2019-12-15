package com.sujianhui.materialsManagement.controller;

import com.sujianhui.materialsManagement.model.Role;
import com.sujianhui.materialsManagement.service.MyUserDetailsService;
import com.sujianhui.materialsManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    //主页
    @GetMapping("/")
    public String signin() {
        return "test";
    }

//    //主页
//    @GetMapping("/searchTbl")
//    public String search() {
//        return "searchTbl";
//    }
    //登录操作
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    //登录操作
//    @GetMapping("/logout")
//    public String logout() {
//        return "redirect:/";
//    }
    //注册操作
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    //test.HTML中写的跳转页
    @GetMapping("/materialsTbl")
    public String show(HttpServletRequest request) {
        request.getSession().setAttribute("username",getCurrentUsername());
        request.getSession().setAttribute("userRole",getCurrentUserRole());
       return "materialsTbl";
    }

    @GetMapping("/registerdemo")
    public String showdemo() {
        return "register";
    }

    public String getCurrentUsername() {
        //这句话可以返回当前用户的name
        Collection collection=SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
    public HashSet getCurrentUserRole() {
        //这句话可以返回当前用户的role
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());

        return new HashSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

    }
}
