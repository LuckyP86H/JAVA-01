package com.xianyanyang.rpc;

import java.util.List;

/**
 * 负载均衡：根据规则选取服务的地址
 */
public interface LoadBalancer {

    URL select(List<URL> urls);

}
