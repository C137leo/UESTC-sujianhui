package com.sujianhui.materialsManagement.controller;

import com.github.pagehelper.PageInfo;
import com.sujianhui.materialsManagement.model.Materials;
import com.sujianhui.materialsManagement.model.MaterialsDTO;
import com.sujianhui.materialsManagement.model.Msg;
import com.sujianhui.materialsManagement.service.MaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MaterialsController {
    @Autowired
    MaterialsService materialsService;

    //查询所有物品（并在前端根据用户身份区分是否可以增删改查）
//    @RequestMapping("/showMaterialsList")
//    public String getAllMaterials(@RequestParam(value="pageNo",defaultValue="1")int pageNo,
//                                  @RequestParam(value="pageSize",defaultValue="3")int pageSize,
//                                  Model model){
//        PageInfo pageInfo=materialsService.getAllMaterials(pageNo,pageSize);
//        model.addAttribute("pageInfo",pageInfo);
//        return "materialsTbl";
//    }
    //
    @RequestMapping("/showMaterialsList")
    @ResponseBody
    public Msg getAllMaterials(@RequestParam(value="pn",defaultValue="1")int pageNo,
                               @RequestParam(value="pageSize",defaultValue="3")int pageSize,
                               Model model){
        PageInfo pageInfo=materialsService.getAllMaterials(pageNo,pageSize);
//        model.addAttribute("pageInfo",pageInfo);
        return Msg.success().add("pageInfo",pageInfo);
    }

    public String getCurrentUsername() {
        //这句话可以返回当前用户的name
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
    //删除相应的物品
    @RequestMapping("/deleteMaterials")
    public String deleteMaterialsByName(String name){
        materialsService.deleteMaterialsByName(name);
        Materials materials=materialsService.selectMaterialsByName(name);
        if(materials==null){
            return "已删除xxx，返回删除后的表页面";
        }else
            return "删除失败";
    }

    //批量删除相应的物品
    @RequestMapping("/deleteSomeMaterials")
    public String deleteSomeMaterials(String name){
        materialsService.deleteMaterialsByName(name);
        Materials materials=materialsService.selectMaterialsByName(name);
        if(materials==null){
            return "已删除xxx，返回删除后的表页面";
        }else
            return "删除失败";
    }

    //根据物品名字查询物品的信息
    @RequestMapping("/selectMaterialsByname")
    public String selectMaterialsByname(String name){
        Materials materials=materialsService.selectMaterialsByName(name);
        if (materials!=null){
            return "已经查到物品";
        }else {
            return "未查到物品";
        }
    }

    //编辑修改物品
    @RequestMapping("/updateMaterials")
    public String updateByMaterialsName(String name,Integer amount){
        return "修改";
    }

    //添加相应的物品
    @RequestMapping("/createMaterials")
    public String createMaterials(Materials materials){
        return "创建物品";
    }

}
