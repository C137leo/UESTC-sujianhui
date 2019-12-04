package com.sujianhui.materialsManagement.model;

import lombok.Data;

@Data
public class Materials {
    private Integer id;
    private String name;
    private Double singlePrice;
    private boolean isDelete;
}
