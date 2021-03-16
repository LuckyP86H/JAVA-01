package com.xianyanyang.user;

import com.xianyanyang.user.domain.entity.User;
import com.xianyanyang.user.domain.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User getById(int id) {
        return new User(id, "xianyanyang");
    }
}
