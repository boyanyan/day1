package com.zr.test;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by lenovo on 2019/6/20.
 */
@Data
public class inquiryAddVo {
    private int id;
    @NotBlank(message = "单号不能为空")
    private String inquiryCode;
    private Integer status;
    private List<PartVo> partVoList;
    @Valid
    @NotEmpty(message = "供应商集合不能为空")
    private List<SupplierVo> supplierVoList;


}
