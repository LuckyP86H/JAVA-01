package com.xianyanyang.rpc.register;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 服务实现类注册中心
 */
public class ServiceRegister {

    private static Map<Object, Class> registerClasses = new HashMap<>();

    public static void registerService(String name, Class clazz) {
        registerClasses.put(name, clazz);
    }

    public static Map<Object, Class> listAll() {
        return registerClasses;
    }
}
