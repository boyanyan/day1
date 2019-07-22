package com.zr.plant.mode;

/**
 * Created by lenovo on 2019/6/18.
 */

public enum PlantStatusNameEnum {
    QIYONG("启用"),
    JINYONG("禁用");

    private PlantStatusNameEnum(String status){
        this.status = status;
    }
    private String status;

    public String getStatus(){
        return this.status;
    }
}
