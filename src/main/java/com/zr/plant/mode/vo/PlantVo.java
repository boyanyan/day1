package com.zr.plant.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/6/12.
 */
@Data
public class PlantVo {
    private Integer id;
    private String plantCode;
    private String plantDesc;

//    private String statusName;
    private Boolean plantStatus;
    private  String plantStatusName;
    private Integer legalPersonId;
    //法人名称
    private  String LegalPersonName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Beijing")
    private Date createTime;
    private String createName;
    private Integer createId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Beijing")
    private Date updateTime;
    private String updateName;
    private Integer updateId;
    //乐观锁版本号记录
    private int version;



}
