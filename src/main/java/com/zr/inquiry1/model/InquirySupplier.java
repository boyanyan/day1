package com.zr.inquiry1.model;

import lombok.Data;

/**
 * Created by zlz on 2018/7/2.
 */

/**
 * 供应商实体
 */
@Data
public class InquirySupplier {
    private Integer id;
    //供应商ID
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    //主表ID
    private Integer inquiryId;
}
