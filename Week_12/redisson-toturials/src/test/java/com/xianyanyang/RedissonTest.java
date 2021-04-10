package com.xianyanyang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({Application.class})
public class RedissonTest {

    @Autowired
    private Redisson redisson;

    private int timeOut = 10;

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    @Test
    public void lockAndUnLock() {
        RLock lock = redisson.getLock("xianyanyang");
        try {
            lock.lock(timeOut, timeUnit);
            System.out.println("do something");
        } finally {
            lock.unlock();
        }
    }
}
