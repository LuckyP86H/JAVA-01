package com.xianyanyang;

import com.xianyanyang.impl.RpcRequestResolver;
import com.xianyanyang.order.OrderServiceImpl;
import com.xianyanyang.order.domain.service.OrderService;
import com.xianyanyang.rpc.register.RegisterCenter;
import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.rpc.URL;
import com.xianyanyang.rpc.register.ServiceRegister;
import com.xianyanyang.rpc.server.ServerSkeleton;
import com.xianyanyang.user.UserServiceImpl;
import com.xianyanyang.user.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于Socket编程接口的服务器实现
 */
public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);

    private static int port = 9010;

    public static void main(String[] args) {
        RequestResolver requestResolver = new RpcRequestResolver();
        ServerSkeleton serverSkeleton = new ServerSkeleton(requestResolver);

        logger.warn("start register services...");
        RegisterCenter.registerService(UserService.class.getName(), new URL("localhost", port));
        RegisterCenter.registerService(OrderService.class.getName(), new URL("localhost", port));

        logger.warn("start register classes...");
        ServiceRegister.registerService(UserService.class.getName(), UserServiceImpl.class);
        ServiceRegister.registerService(OrderService.class.getName(), OrderServiceImpl.class);

        logger.warn("start server...");
        while (true) {
            logger.warn("server running...");
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket socket = serverSocket.accept()) {
                serverSkeleton.process(socket);
                logger.warn("process client");
            } catch (IOException e) {
                logger.error("server error...");
            }
        }

    }
}
