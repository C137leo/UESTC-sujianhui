package com.sujianhui.materialsManagement.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sujianhui.materialsManagement.dao.MaterialsMapper;
import com.sujianhui.materialsManagement.model.*;
import org.springframework.beans.BeanUtils;
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


    public void updateMaterials(MaterialsDTO materialsDTO) {
        materialsMapper.updateMaterials(materialsDTO);
    }
    public MaterialsDTO getMaterials(Integer id) {
        MaterialsDTO materialsDTO=materialsMapper.getMaterials(id);
        return materialsDTO;
    }

    public boolean checkMaterials(String name) {
        MaterialsDTO materialsDTO=materialsMapper.checkMaterials(name);
        if (materialsDTO==null){
            return true;
        }else {
            return  false;
        }
    }

    public void saveMaterials(Materials materials) {
        Stock stock=new Stock();
        Store store=new Store();
        Borrow borrow=new Borrow();
        MaterialsDTO materialsDTO=new MaterialsDTO();
        BeanUtils.copyProperties(materials,materialsDTO);
        materialsDTO.setBorrowNum(0);
        materialsDTO.setStoreNum(0);
        materialsDTO.setStockNum(0);
        materialsMapper.saveMaterials(materialsDTO);
        Integer materialsId=materialsMapper.getMaterialsId(materialsDTO.getName());
        stock.setMaterialId(materialsId);
        stock.setStockNum(0);
        store.setMaterialId(materialsId);
        store.setStoreNum(0);
        borrow.setMaterialId(materialsId);
        borrow.setBorrowNum(0);
        materialsMapper.createMaterialsBorrow(borrow);
        materialsMapper.createMaterialsStock(stock);
        materialsMapper.createMaterialsStore(store);
    }

    public void deleteMaterialsById(Integer id) {

        materialsMapper.deleteMaterialsById(id);
    }

//    public MaterialsDTO getMaterialsByName(String name) {
//        MaterialsDTO materialsDTO=materialsMapper.getMaterialsByName(name);
//        return materialsDTO;
//    }
    public MaterialsDTO getMaterialsByName(String name) {
        MaterialsDTO materialsDTO=materialsMapper.getMaterialsByName(name);
        return materialsDTO;
    }
}
