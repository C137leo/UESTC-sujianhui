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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    /**
     * 查询所有物品
     *
     *查询所有的物品并返回列表
     * @param pageNo 当前点击的页码数
     * @param pageSize
     * @param model
     * @return Msg
     * 2019/12/4
     */

    @RequestMapping("/showMaterialsList")
    @ResponseBody
    public Msg getAllMaterials(@RequestParam(value="pn",defaultValue="1")int pageNo,
                               @RequestParam(value="pageSize",defaultValue="10")int pageSize,
                               Model model){
        PageInfo pageInfo=materialsService.getAllMaterials(pageNo,pageSize);
        Msg msg=Msg.success().add("pageInfo",pageInfo);
        msg.setIsAdmin(getCurrentUserRole().size());
        System.out.println(msg.getIsAdmin());
        return msg;
    }
    /**
     * 改对应的物品
     *
     * 根据模态框传回的信息编辑更新对应物品
     * 编辑修改物品
     * @param materialsDTO
     * @return Msg
     * 2019/12/5
     */
    @RequestMapping(value = "/materialsUpdate/{id}",method = RequestMethod.PUT)
    @ResponseBody
//    public Msg updateByMaterialsName(@PathVariable("id") Integer id,MaterialsDTO materialsDTO){
    public Msg updateByMaterialsName(MaterialsDTO materialsDTO){
        //如果传来的值为空或空格则设置为0
        System.out.println(materialsDTO);
//       judge(materialsDTO);
//        materialsDTO.setId(id);
//        materialsService.updateMaterials(id,materialsDTO);
        materialsService.updateMaterials(materialsDTO);
        return Msg.success();
    }

    /**
     * 根据id查单个的物品信息
     *
     * 根据模态框传回的信息查出物品的属性并返回前端
     * @param id
     * @return Msg.materials
     * 2019/12/5
     */
    @RequestMapping(value ="/getMaterials/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getMaterialsById(@PathVariable("id") Integer id){
        MaterialsDTO materials=materialsService.getMaterials(id);
        return Msg.success().add("materialsGet",materials);
    }

    /**
     * 根据物品名称查单个的物品信息
     *
     * 根据模态框传回的信息查出物品的属性并返回前端
     * @param name
     * @return Msg.materials
     * 2019/12/11
     */
    @RequestMapping(value ="/searchMaterials",method = RequestMethod.POST)
    public String getMaterialsByName(@RequestParam("name") String name,
                                  Model model){
        MaterialsDTO materials=materialsService.getMaterialsByName(name);
        System.out.println(materials);
        List<MaterialsDTO> materialsList=new ArrayList<>();
        materialsList.add(materials);
        model.addAttribute("materialsList",materialsList);
//      return Msg.success().add("materials",materials);
        return "searchTbl";
    }

    @RequestMapping(value ="/selectMaterialsByName",method = RequestMethod.POST)
    @ResponseBody
    public Msg selectMaterialsByName(@RequestParam("name") String name){
        System.out.println(name);
//        MaterialsDTO materials=materialsService.getMaterialsByName(name);
        Materials materials=materialsService.selectMaterialsByName(name);
        System.out.println(materials);
        return Msg.success().add("materials",materials);
    }




    /**
     * 查询物品名称是否重复
     *
     * @param name
     * @return
     * 2019/12/8
     */
    @RequestMapping(value = "/checkMaterialsName",method = RequestMethod.POST)
    @ResponseBody
    public Msg checkMaterialsName(@RequestParam("name") String name){
//        String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";用户名必须是2-5位的中文或者6-16位英文和数字的组合
        String regx="(^[a-zA-Z]{1,10}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!name.matches(regx)){
            return Msg.fail().add("va_msg", "物资名称必须是2-5位的中文或者1-10位英文字符");
        }
        //数据库用户名重复校验
        boolean b=materialsService.checkMaterials(name);
        if(b) {
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg", "物资的名称重复，请重新输入");
        }
    }



    /**
     *
     * @param materials
     * @return
     */
    //添加相应的物品
    @RequestMapping(value = "/createMaterials",method = RequestMethod.POST)
    @ResponseBody
        public Msg createMaterials(@Valid Materials materials,BindingResult result){
        System.out.println(materials);
        if(result.hasErrors()) {
            //校验失败应该返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map=new HashMap<>();
            List<FieldError> errors= result.getFieldErrors();
            for(FieldError fieldError :errors) {
                System.out.println("错误的名字："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else {
           if (materialsService.selectMaterialsByName(materials.getName())==null){
               materialsService.saveMaterials(materials);
               return Msg.success();
           }else {
               return Msg.fail().add("errorFields", "物资名称重复");
           }
        }
    }

    //删除相应的物品
    @RequestMapping(value = "/deleteMaterials/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteMaterialsByName(@PathVariable("ids")String ids){
        if(ids.contains("-")) {
            List<Integer> del_ids=new ArrayList<>();
            String[] str_ids=ids.split("-");
            for(String id:str_ids) {
                del_ids.add(Integer.parseInt(id));
                //原作有个批量删除的方法完全没看明白。
                //materialsService.deletBatch(del_ids);
                materialsService.deleteMaterialsById(Integer.parseInt(id));
            }
        }else {
            //强制转换int
            Integer id=Integer.parseInt(ids);
            materialsService.deleteMaterialsById(id);
        }
        return Msg.success();

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


    /**
     * 判断接收到的值为空或者为空格就把它们的价格或者数量赋值为0
     * @param
     * /stockNum/storeNum/borrowNum是否为空
     */
//    public void judge(MaterialsDTO materialsDTO){
//        if(materialsDTO.getSinglePrice().equals(" ")||materialsDTO.getSinglePrice()==null){
//            materialsDTO.setSinglePrice((double) 0);
//        }
//        if(materialsDTO.getStockNum().equals(" ")||materialsDTO.getStockNum()==null){
//            materialsDTO.setStockNum(0);
//        }
//        if(materialsDTO.getStoreNum().equals(" ")||materialsDTO.getStoreNum()==null){
//            materialsDTO.setStoreNum(0);
//        }
//        if(materialsDTO.getBorrowNum().equals("")||materialsDTO.getBorrowNum()==null){
//            materialsDTO.setBorrowNum(0);
//        }
//    }
    public HashSet getCurrentUserRole() {
        //这句话可以返回当前用户的role
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_ADMIN"));

        return new HashSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

    }

}
