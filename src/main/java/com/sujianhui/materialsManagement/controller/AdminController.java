package com.sujianhui.materialsManagement.controller;

import com.sujianhui.materialsManagement.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    @Autowired
    private UserRoleService userRoleService;

}
