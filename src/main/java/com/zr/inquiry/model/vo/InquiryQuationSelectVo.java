package com.zr.inquiry.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.plant.mode.PageVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquiryQuationSelectVo extends PageVO{
    //供应商id
    private Integer supplierId;
    //询价单号
    private String inquiryNo;
    private String partCode;
    //备件集合
    private List<Integer> partIdList;

}
