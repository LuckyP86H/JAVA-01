package com.xianyanyang.domain.service;

import com.xianyanyang.domain.entity.User;

public interface UserService {

    User findUserById(String userId);

    User updateUserName(String userId);
}
