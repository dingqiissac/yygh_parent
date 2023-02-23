package com.atguigu.yygh.common.exception;

import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.fail();
    }
}
