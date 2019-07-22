package com.zr.plant.service.impl;

import com.zr.plant.mapper.PlantMapper;
import com.zr.plant.mode.AllRecords;
import com.zr.plant.mode.Plant;
import com.zr.plant.mode.PlantStatusEnum;
import com.zr.plant.mode.PlantStatusNameEnum;
import com.zr.plant.mode.vo.PlantAddVo;
import com.zr.plant.mode.vo.PlantSelectVo;
//import com.zr.plant.mode.vo.PageVo;
import com.zr.plant.mode.vo.PlantUpdateVo;
import com.zr.plant.mode.vo.PlantVo;
import com.zr.plant.service.PlantService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */
@Service
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantMapper plantMapper;
    @Override
    public Result<PlantAddVo> addPlant(PlantAddVo plantAddVo) {
        //1、工厂编码，在系统内是唯一的，需要验重；一个法人可以对应多个工厂，一个工厂只能对应一个法人；
        Plant plant = new Plant();
        //把左边对象赋值给右边
        BeanUtils.copyProperties(plantAddVo,plant);
        //查询方法
        Result<Plant> plantAddVoResult = validData(plant);
        if (!plantAddVoResult.getStatus()) {
            return ResultVo.error(plantAddVoResult.getErrorCode(),plantAddVoResult.getMessage());
        }
        //新增入库
        Date nowDate = new Date();
        plant.setCreateId(1024);
        plant.setCreateName("小强");
        plant.setCreateTime(nowDate);
        plant.setUpdateId(1024);
        plant.setUpdateName("小强");
        plant.setUpdateTime(nowDate);

        //新增方法
        plantMapper.savePlant(plant);
        return ResultVo.success(plantAddVo);

    }

    @Override
    public Result<PlantUpdateVo> updatePlant(PlantUpdateVo plantUpdateVo) {
        //2.修改方法
        //验证要修改的内容是否存在，根据ID进行验证
        Plant plant = new Plant();
        BeanUtils.copyProperties(plantUpdateVo,plant);
        Result<Plant> plantResult = validUpdateData(plant);
        if (!plantResult.getStatus()) {
            return ResultVo.error(plantResult.getErrorCode(),plantResult.getMessage());
        }
        //修改数据库
        plant.setUpdateId(1024);
        plant.setUpdateName("小强");
        plant.setUpdateTime(new Date());
        //乐观锁
        int version = plantMapper.updatePlant(plant);
        if (version == 0) {
            return ResultVo.error("500","当前数据不是最新数据，请刷新后重试");
        }
        return ResultVo.success(plantUpdateVo);
    }

    @Override
    public Result<PlantVo> queryPlantById(Integer id) {
        Plant plant = plantMapper.queryPlantById(id);
        PlantVo plantVo = new PlantVo();
        BeanUtils.copyProperties(plant,plantVo);
        if (plant.getPlantStatus() == PlantStatusEnum.QIYONG.getStatus()) {//后台启动
            plantVo.setPlantStatusName(PlantStatusNameEnum.QIYONG.getStatus());//返回前端页面启用
        }
        if (plant.getPlantStatus() == PlantStatusEnum.JINYONG.getStatus()) {//后台禁用
            plantVo.setPlantStatusName(PlantStatusNameEnum.JINYONG.getStatus());//返回前端页面禁用
        }
        return ResultVo.success(plantVo);
    }

    @Override
    public Result<AllRecords<PlantVo>> queryPage(PlantSelectVo plantSelectVo) {
        //1.查询条数
       int count = plantMapper.queryCount(plantSelectVo);
        System.out.println(count);
        //2.查询数据
        List<Plant> plantList =  plantMapper.queryPage(plantSelectVo);
        System.out.println(plantList);
        AllRecords allRecords = new  AllRecords();
        allRecords.setPageIndex(plantSelectVo.getPageIndex());
        allRecords.setPageSize(plantSelectVo.getPageSize());
        allRecords.setTotalNumber(count);
        allRecords.setDataList(plantList);
        //总页数方法
        allRecords.resetTotalNumber(count);
        //3.给AllRecords赋值返回数据
        return ResultVo.success(allRecords);
    }


    private Result<Plant> validUpdateData(Plant plant){
        //根据ID查询数据库
        List<Plant> byPlantId = plantMapper.findByPlantId(plant.getId());
        if (byPlantId.size() == 0 ) {
            return ResultVo.error("500","修改的数据不存在");

        }
        return ResultVo.success(plant);
    }
    private Result<Plant> validData(Plant plant){
        //查询数据库中编码是否存在方法
        List<Plant> byPlantCode = plantMapper.findByPlantCode(plant.getPlantCode());

        if (byPlantCode.size() > 0 ) {
            return ResultVo.error("500","工厂编码已经存在");

        }
        return ResultVo.success(plant);
    }

}
