package com.xianyanyang;

import com.xianyanyang.rpc.RpcRequest;
import com.xianyanyang.rpc.RpcResponse;
import com.xianyanyang.rpc.server.ServerSkeleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServerController {

    @Autowired
    private ServerSkeleton serverSkeleton;

    @PostMapping("/")
    public RpcResponse invokeService(@RequestBody RpcRequest rpcRequest) {
        return serverSkeleton.process(rpcRequest);
    }

}
