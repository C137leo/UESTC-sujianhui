package com.sujianhui.materialsManagement.dao;

import com.sujianhui.materialsManagement.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper {

    void creatUserRole(UserRole userRole);
}
