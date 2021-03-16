package com.xianyanyang;

import com.xianyanyang.impl.RpcRequestResolver;
import com.xianyanyang.rpc.RequestResolver;
import com.xianyanyang.rpc.server.ServerSkeleton;
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
        logger.warn("start server...");
        RequestResolver requestResolver = new RpcRequestResolver();
        ServerSkeleton serverSkeleton = new ServerSkeleton(requestResolver);
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
