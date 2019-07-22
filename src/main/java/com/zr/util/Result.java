package com.zr.util;

import lombok.Data;

/**
 * Created by lenovo on 2019/6/13.
 */
@Data
public class Result<T> {
    //状态
    private Boolean status;
    //提示信息
    private String message;
    //错误码
    private String errorCode;
    //具体内容
    private T data;

}
