package com.zr.inquiry1.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by zlz on 2018/7/2.
 */

/**
 * 供应商实体
 */
@Data
public class InquirySupplierVo {
    //供应商ID
    @NotNull(message = "供应商ID不能为空!")
    private Integer supplierId;
    @NotBlank(message = "供应商编码不能为空!")
    private String supplierCode;
    @NotBlank(message = "供应商名称不能为空!")
    private String supplierName;
    //询价单ID
    private Integer inquiryId;
}
