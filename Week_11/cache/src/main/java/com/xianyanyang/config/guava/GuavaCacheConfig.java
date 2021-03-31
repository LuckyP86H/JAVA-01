package com.xianyanyang.config.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties({GuavaProperties.class})
@Configuration
public class GuavaCacheConfig {

    @Bean(value = "cacheBuilder")
    public CacheBuilder<Object, Object> cacheBuilder(@Autowired GuavaProperties guavaProperties) {
       return CacheBuilder.newBuilder()
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
                .removalListener(removalNotification -> System.out.println(removalNotification.getKey() + " was removed, cause is :" + removalNotification.getCause()));

    }

    @DependsOn({"cacheBuilder"})
    @Bean
    public CacheManager cacheManager(CacheBuilder<Object, Object> cacheBuilder) {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }

    @DependsOn({"cacheBuilder"})
    @Bean
    public Cache<Object, Object> init(CacheBuilder<Object, Object> cacheBuilder) {
        return cacheBuilder.build(new CacheLoader<Object, Object>() {
            @Override
            public String load(Object key) {
                System.out.println("load data:" + key);
                return key + ":cache-value";
            }
        });
    }
}
