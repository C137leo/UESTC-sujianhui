package com.sujianhui.materialsManagement.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sujianhui.materialsManagement.dao.MaterialsMapper;
import com.sujianhui.materialsManagement.model.Materials;
import com.sujianhui.materialsManagement.model.MaterialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialsService {

    @Autowired
    MaterialsMapper materialsMapper;

    public PageInfo<MaterialsDTO> getAllMaterials(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<MaterialsDTO> materilasList=materialsMapper.getAllMaterials();
        PageInfo<MaterialsDTO> page = new PageInfo<MaterialsDTO>(materilasList);
        return page;
    }
    public List<MaterialsDTO> getAllMaterials() {
        List<MaterialsDTO> materilasList=materialsMapper.getAllMaterials();
        return materilasList;
    }

    public void deleteMaterialsByName(String name) {
        materialsMapper.deleteMaterialsByName(name);
    }

    public Materials selectMaterialsByName(String name) {
        Materials materials=materialsMapper.selectMaterialsByName(name);
        return materials;
    }
}
