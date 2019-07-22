package com.zr.plant.mapper;

import com.zr.plant.mode.Plant;
import com.zr.plant.mode.vo.PlantSelectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */
@Mapper
public interface PlantMapper {
    List<Plant> findByPlantCode(String plantCode);
    int savePlant(Plant plant );
    List<Plant> findByPlantId(Integer id);
    int updatePlant(Plant plant);
    Plant queryPlantById(Integer id);
    int queryCount(PlantSelectVo plantSelectVo);
    List<Plant> queryPage(PlantSelectVo plantSelectVo);

    List<Plant> findPlantAll();
}
