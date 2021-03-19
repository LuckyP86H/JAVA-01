package com.xianyanyang.aop.proxy;

import com.xianyanyang.aop.log.impl.LogServiceImpl;
import com.xianyanyang.aop.order.OrderService;
import com.xianyanyang.aop.order.impl.OrderServiceImpl;
import com.xianyanyang.aop.user.UserService;
import com.xianyanyang.aop.user.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class DynamicProxyITest {

    @Test
    public void testServiceProxy() {
        DynamicProxy dynamicProxy = new DynamicProxy();
        dynamicProxy.setLogService(new LogServiceImpl());

        UserService userService = dynamicProxy.createProxy(new UserServiceImpl());
        String userName = userService.getUserName("foo");
        Assert.assertEquals("xianyanyang", userName);

        OrderService orderService = dynamicProxy.createProxy(new OrderServiceImpl());
        String orderNo = orderService.getOrderNo("fee");
        Assert.assertEquals("No123456", orderNo);
    }

}
