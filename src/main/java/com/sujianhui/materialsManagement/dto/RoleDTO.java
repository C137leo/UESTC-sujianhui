package com.sujianhui.materialsManagement.dto;

import com.sujianhui.materialsManagement.model.Role;
import com.sujianhui.materialsManagement.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class RoleDTO extends Role {
    private Set<User> users;
}
