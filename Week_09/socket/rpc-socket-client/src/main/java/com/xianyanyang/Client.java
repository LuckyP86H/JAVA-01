package com.xianyanyang;

import com.xianyanyang.rpc.client.ClientStub;
import com.xianyanyang.rpc.exception.RpcException;
import com.xianyanyang.user.domain.entity.User;
import com.xianyanyang.user.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    private static String host = "127.0.0.1";

    private static int port = 9010;

    public static void main(String[] args) {
        try {
            UserService userService = (UserService) ClientStub.createService(UserService.class, host, port);
            User user = userService.getById(123);
            if (user != null) {
                logger.info("username:{}", user.getName());
            }
        } catch (RpcException e) {
            logger.info("Client error", e.getMessage());
        }
    }
}
