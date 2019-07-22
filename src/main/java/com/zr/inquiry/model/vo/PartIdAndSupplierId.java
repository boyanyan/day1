package com.zr.inquiry.model.vo;

import lombok.Data;

/**
 * Created by zlz on 2018/7/2.
 */

/**
 * 备件供应商中间表
 */
@Data
public class PartIdAndSupplierId {
    //备件ID
    private Integer partsId;
    //供应商ID
    private Integer supplierId;
}
