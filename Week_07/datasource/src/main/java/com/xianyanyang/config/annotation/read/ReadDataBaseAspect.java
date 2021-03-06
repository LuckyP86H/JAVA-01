package com.xianyanyang.config.annotation.read;

import com.xianyanyang.config.annotation.DataBaseType;
import com.xianyanyang.config.router.DataSourceRouterHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 读数据库切面
 */
@Aspect
@Component
public class ReadDataBaseAspect {

    @Around("@annotation(com.xianyanyang.config.annotation.read.ReadDataBase)")
    public Object readDataBase(ProceedingJoinPoint joinPoint) throws Throwable {
        DataSourceRouterHolder.set(DataBaseType.read);
        Object proceed = joinPoint.proceed();
        DataSourceRouterHolder.clear();
        return proceed;
    }
}
