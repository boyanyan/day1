package com.zr.inquiry1.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquiryTotalAddVo {
    @NotNull(message = "新增时，法人信息不能为空！")
    private Integer legalPersonId;
    private String legalPersonName;
    @NotNull(message = "新增时询价类型不能为空！")
    private Integer type;
    @NotNull(message = "币别信息不能为空！")
    private Integer moneyType;
    @NotNull(message = "是否含税信息不能为空！")
    private Integer isTax;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @NotNull(message = "询价开始日期不能为空")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @NotNull(message = "询价结束日期不能为空")
    private Date endDate;
    @NotNull(message = "询价状态不能为空")
    private Integer status;
    //备件集合
    @Valid
    @NotEmpty(message = "新增时备件明细信息不能为空！")
    private List<InquiryPartsVo> inquiryPartsVoList;
    //供应商集合
    @Valid
    @NotEmpty(message = "新增时供应商明细信息不能为空！")
    private List<InquirySupplierVo> supplierVoList;
    private Integer version;

}
