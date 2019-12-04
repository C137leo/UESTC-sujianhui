package com.sujianhui.materialsManagement.controller;


import com.sujianhui.materialsManagement.model.UserRole;
import com.sujianhui.materialsManagement.model.Results;
import com.sujianhui.materialsManagement.model.User;
import com.sujianhui.materialsManagement.service.UserRoleService;
import com.sujianhui.materialsManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    //注册操作
    @RequestMapping(value = "/registeUser",method = RequestMethod.POST)
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "adminPassword") String adminPassword,
                           @RequestParam(value = "confirmPassword") String confirmPassword,
                           HttpServletRequest request) {
        User userTest=userService.getUser(username);
        if(userTest!=null){
            return "用户名已存在，请换个用户名注册";
        }else {
            if (username != null && password != null && password.equals(confirmPassword)) {
                User user = new User();
                user.setPassword(password);
                user.setUsername(username);
                user.setAdminPassword(adminPassword);
                Results results=userService.register(user);
                //如果用户注册成功，则在身份表中插入用户的身份信息
                if (results.getIsSuccess()){
                    UserRole userRoleUser= new UserRole(userService.findUserByName(username).getId(),(long) 1);
                    userRoleService.createUserRole(userRoleUser);
                }
                //如果用户以管理员身份注册
                if(adminPassword.equals("sujianhui107")){
                    //注册时向user_role表中插入admin信息
                    UserRole userRoleAdmin = new UserRole(userService.findUserByName(username).getId(),(long) 2);
                    userRoleService.createUserRole(userRoleAdmin);
                }
                return "firstPage";
            } else {
                User user = new User();
                Results results = userService.register(user);
                return results.getMsg();
            }
        }

    }

    @GetMapping("/getAllUser")
    @ResponseBody
    public List<User> getAll(){
        List<User> userList=userService.getAll();
        return userList;
    }
//    @PostMapping("/get-user")
//    @ResponseBody
//    public UserDTO getUser(@RequestParam String username,
//                           @RequestParam String password,
//                           Model model){
//        UserDTO userDTO=userService.getUser(username);
////        if (userDTO.getPassword().equals(password)){
////            System.out.println(userDTO);
////            model.addAttribute("username",userDTO.getUsername());
////            model.addAttribute("roles",userDTO.getRoles());
////        }
//        return userDTO;
//    }

    /**
     * 查看登录用户信息
     */
    @GetMapping("/get-auth")
    @ResponseBody
    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
