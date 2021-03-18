package com.xianyanyang.impl;

import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.rpc.register.ServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RpcRequestResolver implements RequestResolver {

    private static Logger logger = LoggerFactory.getLogger(RpcRequestResolver.class);

    @Override
    public Class resolve(String className) {
        Map<Object, Class> registerClasses = ServiceRegister.listAll();
        if (!registerClasses.containsKey(className)) {
            logger.error("Class {} has none register implements class", className);
            return null;
        }
        try {
            return registerClasses.get(className);
        } catch (Exception e) {
            logger.error("Class {} instance failed", className);
            return null;
        }
    }
}
