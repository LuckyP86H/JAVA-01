package com.xianyanyang.order.domain.entity;

import java.io.Serializable;

/**
 * 订单实体
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单标识
     */
    private int id;

    /**
     * 订单编号
     */
    private String orderNo;

    public Order(int id, String orderNo) {
        this.id = id;
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }
}
