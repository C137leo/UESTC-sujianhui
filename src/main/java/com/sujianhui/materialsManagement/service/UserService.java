package com.sujianhui.materialsManagement.service;

import com.sujianhui.materialsManagement.dao.UserMapper;
import com.sujianhui.materialsManagement.dto.UserDTO;
import com.sujianhui.materialsManagement.model.Results;
import com.sujianhui.materialsManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Results register(User user){
        Results results=new Results();
        results.setIsSuccess(false);
        results.setDetail(null);
        try{
            //注意此处一定要用equals方法，否则string的内存地址不同会被认为是不同的对象，就无法向数据库存入adminPassword。·
            if(user.getAdminPassword().equals("sujianhui107")){
                User existUser=userMapper.findUserByName(user.getUsername());
                if(existUser!=null){
                    results.setMsg("用户名已存在");
                }else {
                    userMapper.register(user);
                    results.setMsg("以管理员身份注册成功");
                    results.setIsSuccess(true);
                    results.setDetail(user);
                }
            }else {
                User existUser = userMapper.findUserByName(user.getUsername());
                if (existUser != null) {
                    results.setMsg("用户名已存在");
                } else {
                    user.setAdminPassword(null);
                    userMapper.register(user);
                    results.setMsg("注册成功");
                    results.setIsSuccess(true);
                    results.setDetail(user);
                }
            }

        }catch (Exception e) {
            results.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    public User findUserByName(String username) {
        return  userMapper.findUserByName(username);
    }

    public UserDTO getUser(String username){
        return userMapper.selectUserByUsername(username);
    }

    public List<User> getAll() {
        List<User> userList=userMapper.getAll();
        return  userList;
    }
}
