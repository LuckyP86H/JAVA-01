package com.xianyanyang.rpc.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.rpc.RpcRequest;
import com.xianyanyang.rpc.RpcResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ServerSkeleton {

    private static Logger logger = LoggerFactory.getLogger(ServerSkeleton.class);

    private RequestResolver requestResolver;

    public ServerSkeleton(RequestResolver requestResolver) {
        this.requestResolver = requestResolver;
    }

    public RpcResponse process(RpcRequest request) {
        try {
            if (request == null) {
                logger.error("Invalid RpcRequest");
                return null;
            }
            String className = request.getClassName();
            String methodName = request.getMethodName();
            if (StringUtils.isAllBlank(className, methodName)) {
                logger.error("ClassName or methodName can not be blank");
                return null;
            }

            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();
            Class clazz = requestResolver.resolve(className);
            Method method = clazz.getMethod(methodName, parameterTypes);
            if (method == null) {
                logger.error("method {} not exists", methodName);
                return null;
            }
            Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), parameters);
            return new RpcResponse().setData(JSON.toJSONString(result, SerializerFeature.WriteClassName)).setStatus("success").setDescription("请求成功");
        } catch (Exception e) {
            logger.error("process error", e);
            return new RpcResponse().setData(null).setStatus("failed").setDescription("请求失败");
        }
    }
}
