package com.ihr.common.controller;

import com.ihr.common.entity.Result;
import com.ihr.common.entity.ReturnCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ErrorController {

    //公共错误跳转
    @RequestMapping(value="autherror")
    public Result autherror(int code) {
        return code ==1?new Result(ReturnCode.UNAUTHENTICATED):new Result(ReturnCode.UNAUTHORIZE);
    }

}
