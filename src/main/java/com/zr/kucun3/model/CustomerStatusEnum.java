package com.zr.kucun3.model;

/**
 * Created by Lx on 2018/10/11.
 */
public enum CustomerStatusEnum {
    QIYONG(true),
    JINYONG(false);

    private CustomerStatusEnum(Boolean status) {
        this.status = status;
    }

    private Boolean status;

    public Boolean getStatus() {
        return this.status;
    }
}
