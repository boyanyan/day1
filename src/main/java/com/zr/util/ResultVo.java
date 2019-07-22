package com.zr.util;

import lombok.Data;

/**
 * Created by lenovo on 2019/6/13.
 */
@Data
public class ResultVo {
    public static Result success(Object object){
        Result result = new Result();
        result.setStatus(true);
        result.setData(object);
        return result;
    }

    public static Result error(String errorCode,String message){
        Result result = new Result();
        result.setStatus(false);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
    }
}

