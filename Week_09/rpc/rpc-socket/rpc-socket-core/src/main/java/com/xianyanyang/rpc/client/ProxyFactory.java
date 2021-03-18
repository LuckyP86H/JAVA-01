package com.xianyanyang.rpc.client;

import com.xianyanyang.rpc.LoadBalancer;
import com.xianyanyang.rpc.register.RegisterCenter;
import com.xianyanyang.rpc.RpcRequest;
import com.xianyanyang.rpc.exception.RpcException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

import com.xianyanyang.rpc.URL;

import java.util.List;

public class ProxyFactory<T> {

    public static <T> T getProxy(Class clazz, LoadBalancer loadBalancer) throws RpcException {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            String className = clazz.getName();

            List<URL> urls = RegisterCenter.getUrls(className);
            URL url = loadBalancer.select(urls);
            if (url == null) {
                return new RpcException("CREATE_SERVICE_000", String.format("Create service %s failed.", className));
            }

            try (Socket socket = new Socket(url.getHost(), url.getPort());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject(new RpcRequest()
                        .setClassName(clazz.getName())
                        .setMethodName(method.getName())
                        .setParameterTypes(method.getParameterTypes())
                        .setParameters(args));
                out.flush();
                return in.readObject();
            } catch (Exception e) {
                return new RpcException("CREATE_SERVICE_001", "Create service failed.");
            }
        });
    }
}
