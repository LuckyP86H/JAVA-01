package com.xianyanyang.domain.service.impl;

import com.xianyanyang.Application;
import com.xianyanyang.domain.entity.User;
import com.xianyanyang.domain.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({Application.class})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    /**
     * cache miss [findUserById] userId:5  -- 首次查询，缓存未命中
     * pool-1-thread-1 find User:xianyanyang5 -- 缓存命中
     * pool-1-thread-1 find User:xianyanyang5 -- 缓存命中
     * pool-1-thread-1 find User:xianyanyang5  -- 缓存命中
     * pool-1-thread-1 find User:xianyanyang5  -- 缓存命中
     * cache miss [findUserById] userId:5   -- 缓存过期，未命中
     * 5 was removed, cause is :EXPIRED
     * pool-1-thread-1 find User:xianyanyang5
     * Thread[main,5,main]find User done
     * @throws InterruptedException
     */
    @Test
    public void findUserById() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            if (i == 4) {
                Thread.sleep(3000);
            }
            pool.execute(() -> {
                User user = userService.findUserById("5");
                System.out.println(Thread.currentThread().getName() + " find User:" + user.getName());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread() + "find User done");
    }

    /**
     * 执行结果：
     * cache miss [findUserById] userId:5  -- 第一次查询，缓存未命中
     * find User:xianyanyang5  -- 缓存命中
     * find User:xianyanyang5  -- 缓存命中
     * 5 was removed, cause is :REPLACED  -- 缓存被修改
     * find User:xianyanyang5update  -- 缓存命中
     * find User:xianyanyang5update  -- 缓存命中
     * find User:xianyanyang5update  -- 缓存命中
     */
    @Test
    public void updateUserName() {
        User user = userService.findUserById("5");
        System.out.println("find User:" + user.getName());
        user = userService.findUserById("5");
        System.out.println("find User:" + user.getName());
        user = userService.updateUserName("5");
        System.out.println("find User:" + user.getName());
        user = userService.findUserById("5");
        System.out.println("find User:" + user.getName());
        user = userService.findUserById("5");
        System.out.println("find User:" + user.getName());
    }
}
