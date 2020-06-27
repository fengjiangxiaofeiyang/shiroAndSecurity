package com.ihr.common.entity;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 16:22
 */

public enum ReturnCode {
    SUCCESS(true,10000,"操作成功"),
    FAIL(false,10001,"操作失败"),
    UNAUTHENTICATED(false,10002,"您还未登陆"),
    UNAUTHORIZE(false,10003,"权限不足"),
    SERVERERROR(false,99999,"抱歉，系统繁忙，清稍后重试!"),
    MOBILEORPASSWORDERROR(false,20000,"用户名错误");
    boolean success;
    String message;
    int code;
    ReturnCode(boolean success,int code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public boolean success(){
        return success;
    }
    public int code(){
        return code;
    }
    public String message(){
        return message;
    }
}
