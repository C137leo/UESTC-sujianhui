package com.sujianhui.materialsManagement.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Materials {
    private Integer id;
    @NotBlank
    @Size(min= 2 ,max= 18  )
    private String name;
    @Range(min=0, max=10000)
    private Double singlePrice;
    private boolean isDelete;
}
