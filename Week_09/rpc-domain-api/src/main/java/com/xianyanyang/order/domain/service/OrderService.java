package com.xianyanyang.order.domain.service;

import com.xianyanyang.order.domain.entity.Order;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 根据订单编号获取订单信息
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    Order getByOrderNo(String orderNo);
}
