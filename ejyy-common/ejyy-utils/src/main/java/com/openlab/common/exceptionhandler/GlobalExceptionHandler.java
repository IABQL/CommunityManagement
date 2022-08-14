package com.openlab.common.exceptionhandler;

import com.openlab.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义异常
    @ExceptionHandler(value = CustomerException.class)
    @ResponseBody
    public Result handleException(CustomerException e) {
        log.error("{}", e.getMessage());
        return Result.error().message(e.getMessage());
    }

    // 指定特定的异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        log.error("{}", e.getMessage());
        return Result.error().message("执行了ArithmeticException异常处理.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error("{}", e.getMessage());
        return Result.error().message("执行了全局异常处理.");
    }
}
