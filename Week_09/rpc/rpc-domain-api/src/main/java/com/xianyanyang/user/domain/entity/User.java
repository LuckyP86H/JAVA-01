package com.xianyanyang.user.domain.entity;

import java.io.Serializable;

/**
 * 用户实体
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    private int id;

    /**
     * 用户名称
     */
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取用户姓名
     *
     * @return
     */
    public String getName() {
        return name;
    }
}
