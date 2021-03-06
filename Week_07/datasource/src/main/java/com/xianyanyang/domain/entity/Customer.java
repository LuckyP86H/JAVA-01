package com.xianyanyang.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 顾客表
 */
@Entity
@Table(name = "wechat_customer")
public class Customer {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "nick_name")
    private String name;

    public String getName() {
        return this.name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }
}
