package com.xianyanyang.rpc;

/**
 * 解析器
 */
public interface RequestResolver {

    /**
     * 服务解析
     *
     * @param className 类名
     * @return 类
     */
    Class resolve(String className);
}
