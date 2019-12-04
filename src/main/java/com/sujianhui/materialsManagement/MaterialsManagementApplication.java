package com.sujianhui.materialsManagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(basePackages = "com.sujianhui.materialsManagement.dao")
public class MaterialsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaterialsManagementApplication.class, args);
    }

}