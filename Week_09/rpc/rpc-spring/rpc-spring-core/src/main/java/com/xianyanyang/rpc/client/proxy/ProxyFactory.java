package com.xianyanyang.rpc.client.proxy;

import com.xianyanyang.rpc.exception.RpcException;

public interface ProxyFactory {

    <T> T getProxy(Class serviceClass) throws RpcException;
}
