package com.zr.inquiry.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquiryTotalUpdateVo {
    @NotNull(message = "修改时询价单id不能为空！")
    private Integer id;
    private String inquiryNo;//询价单号
    private Date date;
    @NotNull(message = "修改时法人信息不能为空！")
    private Integer legalPersonId;
    private String legalPersonName;
    @NotNull(message = "修改时询价类型不能为空！")
    private Integer type;
    private Integer moneyType;
    private Integer isTax;//是否纳税
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate;
    private Integer status;
    //备件集合
    @Valid
    @NotEmpty(message = "修改时备件明细信息不能为空！")
    private List<InquiryPartsVo> inquiryPartsVoList;
    //供应商集合
    @Valid
    @NotEmpty(message = "修改时供应商明细信息不能为空！")
    private List<InquirySupplierVo> supplierVoList;
    private Integer version;


}
