package com.sujianhui.materialsManagement.model;

import lombok.Data;

@Data
public class Results <T>{
    //返回信息
    private String msg;
    //是否正常请求
    private Boolean isSuccess;
    //返回具体的数据
    private T detail;

    private Boolean isAdmin;

}
