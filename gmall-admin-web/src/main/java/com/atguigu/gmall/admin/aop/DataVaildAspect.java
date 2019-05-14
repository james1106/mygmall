package com.atguigu.gmall.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 第一步 引入aop场景
 * 第二步 编写切面
 *      1.@Aspect
 *      2.切入点表达式
 *      3.通知
 *          前置通知，方法执行之前触发
 *          后置通知，方法执行之后触发
 *          返回通知，方法正常退出之后触发
 *          异常通知，方法报异常触发
 *          环绕通知，4合1
 *
 *          正常执行：前置通知 ==》 返回通知 ==》 后置通知
 *          异常执行：前置通知 ==》 异常通知 ==》 后置通知
 *
 *     注册之前要进行验证(前置通知)， 注册成功或失败都要进行返回(返回通知) ..
 *     所以 校验的aop适合使用环绕通知
 *
 */
@Slf4j
@Aspect
@Component
public class DataVaildAspect {

    @Around("execution(* com.atguigu.gmall.admin..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint point) throws Throwable{
        Object proceed = null;

        log.debug("校验切面介入工作...");
        Object orjs[] = point.getArgs();
        for(Object obj: orjs){
            if(obj instanceof BindingResult){
                BindingResult r = (BindingResult)obj;
                if(r.getErrorCount() > 0){
                    // 框架自动检测到错误
                    return new CommonResult().validateFailed(r);
                }
            }
        }
        // 没有错误，执行类似于反射的method.invoke();
        proceed = point.proceed(point.getArgs());
        log.debug("校验切面将目标方法已经放行....{}",proceed);

        return proceed;
    }

}
