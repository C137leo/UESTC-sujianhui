package com.sujianhui.materialsManagement.controller;

import com.sujianhui.materialsManagement.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //主页
    @GetMapping("/")
    public String signin() {
        return "test";
    }

    //登录操作
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //注册操作
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    //test.HTML中写的跳转页
    @GetMapping("/materialsTbl")
    public String show(HttpServletRequest request) {
        request.getSession().setAttribute("username",getCurrentUsername());
       return "materialsTbl";
    }

    //test.HTML中写的跳转页
//    @PostMapping("/tologin")
//    public String sho(){
//        return "signin";
//    }
    //test.HTML中写的跳转页
    @GetMapping("/registerdemo")
    public String showdemo() {
        return "register";
    }

    public String getCurrentUsername() {
        //这句话可以返回当前用户的name
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
}
