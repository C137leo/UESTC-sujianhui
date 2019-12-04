package com.sujianhui.materialsManagement.dao;

import com.sujianhui.materialsManagement.model.Materials;
import com.sujianhui.materialsManagement.model.MaterialsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialsMapper {
    //返回所有物品的页面
    List<MaterialsDTO> getAllMaterials();

    void deleteMaterialsByName(String name);

    Materials selectMaterialsByName(String name);
}
