package com.zr.legalPerson.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/6/12.
 */
@Data
public class LegalPersonVo {

    private Integer id;
    private String plantCode;
    private String plantDesc;
    private Boolean plantStatus;
    private String plantStatusName;
    private Integer legalPersonId;
    private String legalPersonName;
    private String createName;
    private Integer createId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    private String updateName;
    private Integer updateId;
    //乐观锁版本记录
    private int version;
}



