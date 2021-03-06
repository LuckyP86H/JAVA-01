package com.xianyanyang.config.annotation.wrire;

import com.xianyanyang.config.annotation.DataBaseType;
import com.xianyanyang.config.router.DataSourceRouterHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 写数据库切面
 */
@Aspect
@Component
public class WriteDataBaseAspect {

    @Around("@annotation(com.xianyanyang.config.annotation.wrire.WriteDataBase)")
    public Object writeDataBase(ProceedingJoinPoint joinPoint) throws Throwable {
        DataSourceRouterHolder.set(DataBaseType.write);
        Object proceed = joinPoint.proceed();
        DataSourceRouterHolder.clear();
        return proceed;
    }
}
