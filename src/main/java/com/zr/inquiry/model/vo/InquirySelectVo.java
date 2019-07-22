package com.zr.inquiry.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.plant.mode.PageVO;
import lombok.Data;

import java.util.Date;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquirySelectVo extends PageVO{
    //询价单号
    private String inquiryNo;
    //法人ID
    private Integer legalPersonId;
    //询价创建开始日期(询价日期)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createStartTime;
    //询价创建结束日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createEndTime;
    //询价开始/结束日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate;
    //供应商名称（根据code查询）
    private String supplierCode;
    //询价类型
    private Integer type;
    //询价状态
    private Integer status;

}
