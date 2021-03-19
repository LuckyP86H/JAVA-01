package com.xianyanyang.aop.proxy;

import com.xianyanyang.aop.log.LogService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {

    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public <T> T createProxy(final Object targetService) {
        return (T) Proxy.newProxyInstance(targetService.getClass().getClassLoader(),
                targetService.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (logService != null) {
                            logService.log(targetService.getClass().getName());
                        }
                        return method.invoke(targetService, args);
                    }
                });
    }

}
