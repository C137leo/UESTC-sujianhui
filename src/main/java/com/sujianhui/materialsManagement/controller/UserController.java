package com.sujianhui.materialsManagement.controller;


import com.sujianhui.materialsManagement.model.Msg;
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

    /**注册操作
     *
     * @param username
     * @param password
     * @param adminPassword
     * @param confirmPassword
     * @param request
     * @return
     */
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
                if(adminPassword.equals("uestc")){
                    //注册时向user_role表中插入admin信息
                    UserRole userRoleAdmin = new UserRole(userService.findUserByName(username).getId(),(long) 2);
                    userRoleService.createUserRole(userRoleAdmin);
                }
                return "login";
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
    /**
     * 查看登录用户信息
     */
    @GetMapping("/get-auth")
    @ResponseBody
    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = "/checkUserName",method = RequestMethod.POST)
    @ResponseBody
    public Msg checkUserName(@RequestParam("username") String username){
        System.out.println(username);
        String regx="(^[a-zA-Z0-9_-]{2,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
//        String regx="(^[a-zA-Z]{1,10}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!username.matches(regx)){
            return Msg.fail().add("va_msg", "用户名必须是2-5位的中文或者2-16位英文和数字的组合");
        }
        //数据库用户名重复校验
        User userTest=userService.getUser(username);
        System.out.println(userTest);
        boolean b=false;
        if (userTest==null){
            b=true;
        }else {
            b=false;
        }
        if(b) {
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg", "用户的名称重复，请重新输入");
        }
    }


}
