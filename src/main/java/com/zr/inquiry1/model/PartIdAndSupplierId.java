package com.zr.inquiry1.model;

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
