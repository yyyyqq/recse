package com.recse4cloud.common.util.log;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志记录的注解作用类
 */
@Aspect
@Order(99)
@Component
public class LoggerAspect {
    ThreadLocal<Long> time = new ThreadLocal<>();

    @Pointcut("@annotation(com.recse4cloud.common.util.log.Log)")
    public void log() {
        Logger.info("切入点", getClass());
    }

    @Before("log()")
    public void before(JoinPoint point) {
        time.set(System.currentTimeMillis()); //记录开始时间
    }

    @After("log()")
    public void after(JoinPoint point) {
        Logger.info("[ useTimes ] " + (System.currentTimeMillis() - time.get()) / 1000 + " s ", point.getTarget().getClass());
    }

    /**
     * 发生异常的时候调用
     *
     * @param point
     */
    @AfterThrowing(value = "log()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        Object target = point.getTarget(); //拦截对象
        String methodName = point.getSignature().getName();//拦截方法名
        Logger.error("[ " + methodName + " ]", target.getClass()); //日志记录方法名
        Logger.error("[ Exception ]" + e.getMessage(), target.getClass()); //日志记录方法名
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object target = pjp.getTarget(); //拦截对象
        String methodName = pjp.getSignature().getName();//拦截方法名
        Object[] args = pjp.getArgs(); //拦截方法参数
        List<Object> objectList = Arrays.stream(args).filter(a -> !(a instanceof HttpServletRequest) && !(a instanceof HttpServletResponse) && !(a instanceof File)).collect(Collectors.toList());
        Logger.info("[ " + methodName + " ]", target.getClass()); //日志记录方法名
        Logger.info("[ args ] " + ((args == null || args.length == 0) ? " null " : JSON.toJSONString(objectList)), target.getClass()); //日志记录方法参数
        Object result = pjp.proceed();
        Logger.info("[ return ] " + ((result == null) ? " null " : JSON.toJSONString(result)), target.getClass());
        return result;
    }
}
