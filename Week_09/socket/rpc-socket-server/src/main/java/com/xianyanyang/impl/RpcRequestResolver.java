package com.xianyanyang.impl;

import com.xianyanyang.order.OrderServiceImpl;
import com.xianyanyang.order.domain.service.OrderService;
import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.user.UserServiceImpl;
import com.xianyanyang.user.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RpcRequestResolver implements RequestResolver {

    private static Logger logger = LoggerFactory.getLogger(RpcRequestResolver.class);

    private static Map<String, Class> registerClasses = new HashMap<String, Class>() {
        {
            put(UserService.class.getName(), UserServiceImpl.class);
            put(OrderService.class.getName(), OrderServiceImpl.class);
        }
    };

    @Override
    public Object resolve(String className) {
        if (!registerClasses.containsKey(className)) {
            logger.error("Class {} has none register implements class", className);
            return null;
        }
        try {
            return registerClasses.get(className).newInstance();
        } catch (Exception e) {
            logger.error("Class {} instance failed", className);
            return null;
        }
    }
}
