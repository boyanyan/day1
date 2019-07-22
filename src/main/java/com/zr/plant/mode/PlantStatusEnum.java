package com.zr.plant.mode;

/**
 * Created by lenovo on 2019/6/18.
 */

public enum PlantStatusEnum {
    QIYONG(true),
    JINYONG(false);

    private PlantStatusEnum(Boolean status){

        this.status = status;
    }
    private Boolean status;

    public Boolean getStatus(){
        return this.status;
    }
}
