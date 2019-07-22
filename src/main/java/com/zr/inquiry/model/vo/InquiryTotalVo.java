package com.zr.inquiry.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.inquiry1.model.vo.*;
import com.zr.inquiry1.model.vo.InquiryPartsVo;
import com.zr.inquiry1.model.vo.InquirySupplierVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquiryTotalVo {
    private Integer id;
    private String inquiryNo;
    private Date date;
    private Integer legalPersonId;
    private String legalPersonName;
    private Integer type;

    private String typeName;
    private Integer moneyType;
    private Integer isTax;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    private Integer createId;
    private String createName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    private Integer updateId;
    private String updateName;

    private List<InquiryPartsVo> inquiryPartsVoList;
    //供应商集合
    private List<InquirySupplierVo> supplierVoList;
    private Integer version;


}

