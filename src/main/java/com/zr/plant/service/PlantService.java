package com.zr.plant.service;

import com.zr.plant.mode.AllRecords;
import com.zr.plant.mode.vo.PlantAddVo;
import com.zr.plant.mode.vo.PlantSelectVo;
import com.zr.plant.mode.vo.PlantUpdateVo;
import com.zr.plant.mode.vo.PlantVo;
import com.zr.util.Result;

/**
 * Created by lenovo on 2019/6/13.
 */
public interface PlantService {
    Result<PlantAddVo> addPlant(PlantAddVo plantAddVo);
    Result<PlantUpdateVo> updatePlant(PlantUpdateVo plantUpdateVo);
    Result<PlantVo> queryPlantById(Integer id);
    Result<AllRecords<PlantVo>> queryPage(PlantSelectVo plantSelectVo);
}
