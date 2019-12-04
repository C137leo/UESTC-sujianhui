package com.sujianhui.materialsManagement.model;

import lombok.Data;

@Data
public class UserRole {
    private  Long id;
    private  Long uid;
    private  Long rid;

    public UserRole(Long uid, Long rid){
        this.uid=uid;
        this.rid=rid;
    }
}
