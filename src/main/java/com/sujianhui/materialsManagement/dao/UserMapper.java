package com.sujianhui.materialsManagement.dao;

import com.sujianhui.materialsManagement.dto.UserDTO;
import com.sujianhui.materialsManagement.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    Long login(User user);

    boolean register(User user);

    User findUserByName(String name);

    // 查询用户
    UserDTO selectUserByUsername(@Param("username") String username);

    List<User> getAll();
}
