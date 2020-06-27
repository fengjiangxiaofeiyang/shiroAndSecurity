package com.ihr.common.exception;

import com.ihr.common.entity.ReturnCode;
import lombok.Getter;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 16:41
 */
@Getter
public class CommonException extends Exception{
    private ReturnCode returnCode;
    public CommonException(ReturnCode returnCode){
        this.returnCode = returnCode;
    }
}
