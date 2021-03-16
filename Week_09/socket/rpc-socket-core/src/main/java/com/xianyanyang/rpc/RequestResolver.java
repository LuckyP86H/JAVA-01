package com.xianyanyang.rpc;

/**
 * 请求解析器
 */
public interface RequestResolver {

    /**
     * 服务解析
     *
     * @param className 类名
     * @return 类
     */
    Object resolve(String className);
}
