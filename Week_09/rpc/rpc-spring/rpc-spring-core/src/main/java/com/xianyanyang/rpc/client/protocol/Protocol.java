package com.xianyanyang.rpc.client.protocol;

import com.xianyanyang.rpc.RpcRequest;
import com.xianyanyang.rpc.URL;

public interface Protocol {
    Object doRequest(RpcRequest request, URL url);
}
