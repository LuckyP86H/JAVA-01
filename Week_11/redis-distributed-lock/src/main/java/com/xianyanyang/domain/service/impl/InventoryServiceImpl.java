package com.xianyanyang.domain.service.impl;

import com.xianyanyang.domain.service.InventoryService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void reduceInventory(String productId, int quantity) {
        String lockKey = productId + "_lock";
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            redissonLock.lock(10, TimeUnit.SECONDS);
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(productId + "_stock"));
            if (stock - quantity >= 0) {
                int nextStock = stock - quantity;
                this.stringRedisTemplate.opsForValue().set(productId + "_stock", nextStock + "");
                System.out.println("库存扣减成功，剩余库存为" + nextStock);
            } else {
                System.out.println("库存扣减失败，库存不足");
            }
        } finally {
            redissonLock.unlock();
        }
    }
}
