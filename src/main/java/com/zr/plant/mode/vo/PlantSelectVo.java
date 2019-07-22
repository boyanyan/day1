package com.zr.plant.mode.vo;

import com.zr.plant.mode.PageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/6/12.
 */
@Data
public class PlantSelectVo extends PageVO {

    private String plantCode;
    private String plantDesc;
    private Boolean plantStatus;
    private Integer legalPersonId;


}
