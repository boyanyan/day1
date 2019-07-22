package com.zr.plant.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by lenovo on 2019/6/12.
 */
@Data
public class PlantAddVo {
    @NotBlank(message = "新增时工厂编码不能为空！")
    private String plantCode;
    @NotBlank(message = "新增时工厂描述不能为空！")
    private String plantDesc;
    @NotNull(message = "新增时工厂状态不能为空！")
    private Boolean plantStatus;
    @NotNull(message = "新增时法人信息不能为空！")
    private Integer legalPersonId;




}
