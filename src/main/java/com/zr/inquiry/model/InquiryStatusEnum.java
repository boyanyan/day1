package com.zr.inquiry.model;

/**
 * Created by lenovo on 2019/6/18.
 */

public enum InquiryStatusEnum {
    SAVE(10),
    TIJIAO(20),
    SHENHE(30),
    SHENPI(40),
    ZUOFEI(50);

    private InquiryStatusEnum(Integer status){

        this.status = status;
    }
    private Integer status;

    public Integer getStatus(){
        return this.status;
    }
}
