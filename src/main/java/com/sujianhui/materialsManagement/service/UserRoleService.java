package com.sujianhui.materialsManagement.service;

import com.sujianhui.materialsManagement.dao.UserRoleMapper;
import com.sujianhui.materialsManagement.model.UserRole;
import com.sujianhui.materialsManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public void createUserRole(UserRole userRole) {
        userRoleMapper.creatUserRole(userRole);
    }
}
