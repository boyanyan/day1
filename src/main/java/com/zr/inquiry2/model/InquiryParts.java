package com.zr.inquiry2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 备件实体
 */
@Data
public class InquiryParts {
    private Integer id;
    //备件ID
    private Integer partsId;
    //备件Cod
    private String partsCode;
    //备件名称
    private String partsName;
    //主表ID
    private Integer inquiryId;
    private String unit;
    private Integer amount;
    private Integer moneyType;
    private String moneyTypeName;
    private Integer moq;
    private Integer deliveryPeriod;
    private Date IntendDeliverDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    private Integer createId;
    private String createName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    private Integer updateId;
    private String updateName;
    private Integer version;
}
