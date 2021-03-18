package com.xianyanyang.rpc.server;

import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.rpc.RpcRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ServerSkeleton {

    private static Logger logger = LoggerFactory.getLogger(ServerSkeleton.class);

    private RequestResolver requestResolver;

    public ServerSkeleton(RequestResolver requestResolver) {
        this.requestResolver = requestResolver;
    }

    public void process(Socket socket) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            RpcRequest request = (RpcRequest) in.readObject();
            if (request == null) {
                logger.error("Invalid RpcRequest");
                return;
            }
            String className = request.getClassName();
            String methodName = request.getMethodName();
            if (StringUtils.isAllBlank(className, methodName)) {
                logger.error("ClassName or methodName can not be blank");
                return;
            }

            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();
            Class clazz = requestResolver.resolve(className);
            Method method = clazz.getMethod(methodName, parameterTypes);
            if (method == null) {
                logger.error("method {} not exists", methodName);
                return;
            }
            Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), parameters);
            out.writeObject(result);
        } catch (Exception e) {
            logger.error("process error", e);
        }
    }
}
