package com.zr.kucun3.model;

/**
 * Created by Lx on 2018/10/11.
 */
public enum CustomerStatusNameEnum {
    QIYONG("启用"),
    JINYONG("禁用");

    private CustomerStatusNameEnum(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }
}
