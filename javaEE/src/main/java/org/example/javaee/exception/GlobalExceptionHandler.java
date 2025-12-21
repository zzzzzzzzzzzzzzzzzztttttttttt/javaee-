package org.example.javaee.exception;


import org.example.javaee.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//标识为全局异常处理类
@ControllerAdvice(basePackages = "org.example.javaee.controller")
public class GlobalExceptionHandler {
    //捕获所有异常类
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json数据
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json数据
    public Result handleException(CustomException e) {
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }
}
