package com.zr.plant.controller;

import com.zr.plant.mode.AllRecords;
import com.zr.plant.mode.PageVO;
import com.zr.plant.mode.vo.PlantAddVo;
import com.zr.plant.mode.vo.PlantSelectVo;
import com.zr.plant.mode.vo.PlantUpdateVo;
import com.zr.plant.mode.vo.PlantVo;
import com.zr.plant.service.PlantService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/*
import com.zr.plant.mode.vo.PlantAddVo;
import com.zr.plant.mode.vo.PlantUpdateVo;
import com.zr.plant.mode.vo.PlantVo;
import com.zr.plant.service.PlantService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.controller.bind.annotation.*;

import javax.validation.Valid;
*/

/**
 * Created by lenovo on 2019/6/12.
 */
@RestController
public class PlantController extends PageVO {
    @Autowired
    private PlantService plantService;

    /**
     * 分页查询
     */
    @PostMapping("/queryPage")
    public Result<AllRecords<PlantVo>> queryPage(@RequestBody PlantSelectVo plantSelectVo){
       return plantService.queryPage(plantSelectVo);
    }




    /**
     * 新增接口
     * @param plantAddVo
     * @param bindingResult
     * @return
     */
    @PostMapping("/addPlant")
    public Result<PlantAddVo> addPlant(@RequestBody  @Valid PlantAddVo plantAddVo, BindingResult bindingResult){
        //判断返回值
        if (bindingResult.hasErrors()) {
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return  plantService.addPlant(plantAddVo);
    }

    /**
     * 修改
     * @param plantUpdateVo
     * @param bindingResult
     * @return
     */
    @PostMapping("updatePlant")
    public Result<PlantUpdateVo> updatePlant(@RequestBody @Valid PlantUpdateVo plantUpdateVo, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return plantService.updatePlant(plantUpdateVo);
    }
    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("queryPlantById")
    public Result<PlantVo> queryPlantById(@RequestParam("id" ) Integer id){
        return plantService.queryPlantById(id);
    }
}


