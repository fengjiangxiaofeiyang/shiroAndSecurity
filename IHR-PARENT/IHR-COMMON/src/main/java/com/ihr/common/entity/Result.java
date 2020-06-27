package com.ihr.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 16:30
 */
@Data
@NoArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public Result(ReturnCode returnCode){
        this.success = returnCode.success;
        this.code = returnCode.code;
        this.message = returnCode.message;
    }
    public Result(ReturnCode returnCode,Object data){
        this.success = returnCode.success;
        this.code = returnCode.code;
        this.message = returnCode.message;
        this.data = data;
    }
    public Result(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public static Result SUCCESS(){
        return new Result(ReturnCode.SUCCESS);
    }
    public static Result FAIL(){
        return new Result(ReturnCode.FAIL);
    }
    public static Result SERVER_ERROR(){
        return new Result(ReturnCode.SERVERERROR);
    }
}
