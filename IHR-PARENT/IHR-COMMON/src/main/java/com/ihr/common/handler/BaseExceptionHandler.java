package com.ihr.common.handler;

import com.ihr.common.entity.Result;
import com.ihr.common.entity.ReturnCode;
import com.ihr.common.exception.CommonException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 17:42
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){
        if(e.getClass() == CommonException.class){
            e.printStackTrace();
            CommonException commonException =  (CommonException) e;
            Result result=new Result(commonException.getReturnCode());
            return result;
        }else {
            Result result =  new Result(ReturnCode.SERVERERROR);
            return result;
        }
    }
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,AuthorizationException e) {
        return new Result(ReturnCode.UNAUTHORIZE);
    }
}
