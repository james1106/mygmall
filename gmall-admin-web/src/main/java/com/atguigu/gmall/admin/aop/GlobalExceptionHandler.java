package com.atguigu.gmall.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理器，给前端返回500的json
 *
 * 当我们编写环绕通知的时候，目标方法出现的异常一定要通过统一异常处理类再抛出去（除非这个异常对你有用）
 */
@Slf4j
//@ControllerAdvice
@RestControllerAdvice // 返回json
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ArithmeticException.class}) //表示是一个异常处理方法，value代表能处理的异常
    public Object handlerException(Exception exception){
        log.error("系统全局异常感知，信息：{}",exception.getStackTrace());
        return new CommonResult().validateFailed("数学没学好");
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public Object handlerException02(Exception exception){
        log.error("系统出现异常感知，信息：{}",exception.getMessage());
        return new CommonResult().validateFailed("空指针了...");
    }

}
