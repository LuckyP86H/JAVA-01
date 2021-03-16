package com.xianyanyang.rpc.server;

import com.xianyanyang.rpc.RequestResolver;
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

            String className = in.readUTF();
            String methodName = in.readUTF();
            if (StringUtils.isAllBlank(className, methodName)) {
                logger.error("ClassName or methodName can not be blank");
                return;
            }
            Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
            Object[] parameters = (Object[]) in.readObject();
            Object service = requestResolver.resolve(className);
            Method method = service.getClass().getMethod(methodName, parameterTypes);
            if (method == null) {
                logger.error("method {} not exists", methodName);
                return;
            }
            Object result = method.invoke(service, parameters);
            out.writeObject(result);
        } catch (Exception e) {
            logger.error("process error", e);
        }
    }
}
