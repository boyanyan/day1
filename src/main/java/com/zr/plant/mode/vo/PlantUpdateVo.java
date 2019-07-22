package com.zr.plant.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by lenovo on 2019/6/12.
 */
@Data
public class PlantUpdateVo {
//    @NotNull
//    @NotEmpty
    @NotNull(message = "修改时ID不能为空")
    private Integer id;
    @NotBlank(message = "修改时工厂描述不能为空")
    private String plantDesc;
    @NotNull(message = "修改时状态不能为空")
    private Boolean plantStatus;
    //乐观锁版本控制
    private int version;



}
