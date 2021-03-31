package com.xianyanyang.domain.repository;

import com.xianyanyang.domain.entity.User;

public interface UserRepository {

    User findUserById(String id);

    User updateUserName(String id);
}
