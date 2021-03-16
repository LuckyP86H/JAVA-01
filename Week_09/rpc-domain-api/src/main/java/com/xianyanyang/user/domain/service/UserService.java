package com.xianyanyang.user.domain.service;

import com.xianyanyang.user.domain.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据用户编号获取用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    User getById(int id);
}
