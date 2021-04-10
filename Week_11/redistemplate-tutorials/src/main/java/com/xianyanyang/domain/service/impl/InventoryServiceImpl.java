package com.xianyanyang.domain.service.impl;

import com.xianyanyang.domain.service.InventoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void reduceInventory(String productId, int quantity) {
        String clientId = UUID.randomUUID().toString();
        String lockKey = productId + "_lock";
        try {
            Boolean ownedLock = this.stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
            this.newThreadReimbursedTimeOutSecond(10, TimeUnit.SECONDS);
            if (!ownedLock) {
                System.out.println("库存扣减失败，请重试");
                return;
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(productId + "_stock"));
            if (stock - quantity >= 0) {
                int nextStock = stock - quantity;
                this.stringRedisTemplate.opsForValue().set(productId + "_stock", nextStock + "");
                System.out.println("库存扣减成功，剩余库存为" + nextStock);
            } else {
                System.out.println("库存扣减失败，库存不足");
            }
        } finally {
            String currentLockOwner = this.stringRedisTemplate.opsForValue().get(lockKey);
            if (StringUtils.equals(clientId, currentLockOwner)) {
                this.stringRedisTemplate.delete(lockKey);
            }
        }
    }

    /**
     * 新建一个线程给当前的过期时间补偿1/3时间，避免过期时间不够业务执行
     *
     * @param timeOut
     * @param timeUnit
     */
    private void newThreadReimbursedTimeOutSecond(int timeOut, TimeUnit timeUnit) {
        //todo:xianyanyang
    }
}
