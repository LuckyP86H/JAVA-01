package com.xianyanyang.domain.service.impl;

import com.xianyanyang.domain.entity.User;
import com.xianyanyang.domain.repository.UserRepository;
import com.xianyanyang.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 备注：
     * Spring在执行@Cacheable标注的方法前先查看缓存中是否有数据
     * 如果有数据，则直接返回缓存数据；
     * 若没有数据，执行该方法并将方法返回值放进缓存。
     * 参数：value缓存名、 key缓存键值、 condition满足缓存条件、unless否决缓存条件
     *
     * @param id 用户编号
     * @return 用户
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User findUserById(String id) {
        System.out.println("cache miss [findUserById] userId:" + id);
        return userRepository.findUserById(id);
    }

    @CachePut(value = "user", key = "#id")
    @Override
    public User updateUserName(String id) {
        return userRepository.updateUserName(id);
    }
}
