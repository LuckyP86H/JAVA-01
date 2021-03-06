package com.xianyanyang.config.annotation.read;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * 读数据库注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "readTransactionManager")
public @interface ReadDataBase {
}
