package com.xianyanyang.domain.service.impl;

import com.xianyanyang.domain.entity.User;
import com.xianyanyang.domain.repository.UserRepository;
import com.xianyanyang.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    /**
     * 备注：
     * @CachePut
     * 标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @param id
     * @param name
     * @return
     */
    @CachePut(value = "user", key = "#id")
    @Override
    public User updateUserName(String id, String name) {
        return userRepository.updateUserName(id, name);
    }

    /**
     * @CacheEvict
     * 用来标注在需要清除缓存元素的方法或类上。
     * 当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
     * @param id
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void deleteUserById(String id) {
        userRepository.deleteUserById(id);
    }
}
