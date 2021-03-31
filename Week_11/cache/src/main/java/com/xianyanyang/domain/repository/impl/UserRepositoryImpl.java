package com.xianyanyang.domain.repository.impl;

import com.xianyanyang.domain.entity.User;
import com.xianyanyang.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findUserById(String id) {
        return new User(id, "xianyanyang" + id);
    }

    @Override
    public User updateUserName(String id) {
        return new User(id, "xianyanyang" + id + "update");
    }
}
