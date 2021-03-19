package com.xianyanyang.user.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户实体
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {


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
