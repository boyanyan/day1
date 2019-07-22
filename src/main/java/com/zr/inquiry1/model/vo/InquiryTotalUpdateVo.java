package com.zr.inquiry1.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created by zlz on 2018/7/2.
 */
@Data
public class InquiryTotalUpdateVo {
    private Integer id;
    @NotBlank(message = "修改时单号不能为空！")
    private String inquiryNo;
    private Date date;
    private Integer legalPersonId;
    private String legalPersonName;
    private Integer type;
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
  /*  //备件集合
    List<InquiryParts> partsList;
    //供应商集合
    List<InquirySupplier> supplierList;*/
    private Integer version;

}
