package com.xianyanyang.order;

import com.xianyanyang.order.domain.entity.Order;
import com.xianyanyang.order.domain.service.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order getByOrderNo(String orderNo) {
        return new Order(1, orderNo);
    }
}
