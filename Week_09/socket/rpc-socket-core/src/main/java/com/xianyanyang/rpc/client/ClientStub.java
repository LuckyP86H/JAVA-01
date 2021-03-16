package com.xianyanyang.rpc.client;

import com.xianyanyang.rpc.exception.RpcException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class ClientStub {

    public static Object createService(Class clazz, String host, int port) throws RpcException {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            try (Socket socket = new Socket(host, port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeUTF(clazz.getName());
                out.writeUTF(method.getName());
                out.writeObject(method.getParameterTypes());
                out.writeObject(args);
                out.flush();
                return in.readObject();
            } catch (Exception e) {
                return new RpcException("createService-001", "Create service failed.");
            }
        };
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
    }
}
