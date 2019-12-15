package com.sujianhui.materialsManagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MaterialsDTO {
    private Integer id;
    private String name;
    private Double singlePrice;
    private Integer stockNum;
    private Integer storeNum;
    private Integer borrowNum;
}
