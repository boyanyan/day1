package com.zr.inquiry.model;

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

    //主表id
    private Integer inquiryId; //询价单号
    private String unit;//单位
    private Integer amount;//总计
    private Integer moneyType;
    private String moneyTypeName;
    private Integer moq;//最小订货量
    private Integer deliveryPeriod;//交货周期
    private Date IntendDeliverDate;//预计供货日期
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
