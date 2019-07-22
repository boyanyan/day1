package com.zr.inquiry.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 备件实体
 */
@Data
public class InquiryQuationPartsVo {
    //备件ID
    @NotNull(message = "备件ID不能为空!")
    private Integer partsId;
    //询价单ID
    private Integer inquiryId;
    //询价单号
    private String InquiryNo;
    //备件Cod
    @NotBlank(message = "备件编码不能为空!")
    private String partsCode;
    //备件名称
    @NotBlank(message = "备件名称不能为空!")
    private String partsName;
    private String unit;
    @NotNull(message = "询价数量不能为空！")
    private Integer amount;
    private Integer moneyType;
    private Integer moq;
    private Integer deliveryPeriod;
    private Date IntendDeliverDate;

}
