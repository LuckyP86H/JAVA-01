package com.xianyanyang.aop.user.impl;

import com.xianyanyang.aop.user.UserService;

public class UserServiceImpl implements UserService {

    public String getUserName(String userId) {
        return "xianyanyang";
    }
}
