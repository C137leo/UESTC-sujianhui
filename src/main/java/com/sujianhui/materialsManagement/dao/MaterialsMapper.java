package com.sujianhui.materialsManagement.dao;

import com.sujianhui.materialsManagement.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialsMapper {
    //返回所有物品的页面
    List<MaterialsDTO> getAllMaterials();

    void deleteMaterialsByName(String name);

    Materials selectMaterialsByName(@Param("name")String name);

//    void updateMaterials(@Param("id")Integer id, MaterialsDTO materialsDTO);

    void updateMaterials(MaterialsDTO materialsDTO);

    MaterialsDTO getMaterials(@Param("id") Integer id);

    MaterialsDTO checkMaterials(@Param("name")String name);


    void saveMaterials(MaterialsDTO materialsDTO);

    void createMaterialsStock(Stock stock);

    void createMaterialsStore(Store store);

    void createMaterialsBorrow(Borrow borrow);

    Integer getMaterialsId(String name);

    void deleteMaterialsById(Integer id);

    MaterialsDTO getMaterialsByName(@Param("name")String name);
}
