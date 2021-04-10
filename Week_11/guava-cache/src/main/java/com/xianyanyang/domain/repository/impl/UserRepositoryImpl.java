package com.xianyanyang.domain.repository.impl;

import com.xianyanyang.domain.entity.User;
import com.xianyanyang.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static HashMap<String, User> memoryUsers = new HashMap<String, User>() {
        {
            for (int i = 0; i < 100; i++) {
                put(i + "", new User(i + "", "xianyanyang" + i));
            }
        }
    };

    @Override
    public User findUserById(String id) {
        return memoryUsers.get(id);
    }

    @Override
    public User updateUserName(String id, String name) {
        User user = memoryUsers.get(id);
        user.setName(name);
        return user;
    }

    @Override
    public void deleteUserById(String id) {
        memoryUsers.remove(id);
    }
}
