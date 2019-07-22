package com.zr.inquiry.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 基础数据备件实体
 */
@Data
public class Parts {
    private Integer partsId;
    //备件Cod
    private String code;
    //备件名称
    private String name;

 }
