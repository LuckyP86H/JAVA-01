package com.xianyanyang;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //同时写缓存的线程数
                .concurrencyLevel(8)
                //写入后多久缓存失效
                .expireAfterWrite(2, TimeUnit.SECONDS)
                //访问后多久缓存失效
                .expireAfterAccess(1, TimeUnit.SECONDS)
                //设置缓存容量
                .initialCapacity(10)
                .maximumSize(100)
                //设置要统计缓存的命中率
                .recordStats()
                //设置缓存移除监听器
                .removalListener(removalNotification -> System.out.println(removalNotification.getKey() + " was removed, cause is :" + removalNotification.getCause()))

                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println("load data:" + key);
                        return key + ":cache-value";
                    }
                });

        cache.put("name", "xianyanyang");
        cache.put("sex", "female");

        System.out.println(cache.get("name"));
        System.out.println(cache.get("sex"));

        Thread.sleep(1000);
        System.out.println("after 1 second");
        System.out.println(cache.get("name"));
        System.out.println(cache.get("sex"));

        System.out.println("remove name");
        cache.invalidate("name");
        System.out.println(cache.get("name"));

        Thread.sleep(2000);
        System.out.println("after 2 second");
        System.out.println(cache.get("name"));
        System.out.println(cache.get("sex"));
    }
}
