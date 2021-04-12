package com.xianyanyang.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"user"})
public class UserService {

    private static User user = new User().setId("1").setName("xianyanyang");

    @Cacheable(key = "getTarget  + #p0")
    public User getUser(String id) {
        System.out.println("getUser form db");
        return user;
    }

    @CachePut(key = "getTarget  + #p0")
    public User updateUserName(String id, String name) {
        System.out.println("updateUserName from db");
        return user.setName(name);
    }


    @CacheEvict(key = "getTarget  + #p0")
    public void deleteUser(String id) {
        user = null;
        System.out.println("deleteUser from db");
    }
}
