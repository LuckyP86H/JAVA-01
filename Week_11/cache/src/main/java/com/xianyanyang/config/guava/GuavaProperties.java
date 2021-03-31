package com.xianyanyang.config.guava;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "guava.cache.config")
public class GuavaProperties {

    /**
     * 同时写缓存的线程数
     */
    private int concurrencyLevel;

    /**
     * 设置缓存容量
     */
    private int initialCapacity;

    /**
     * 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
     * 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
     * expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准。
     */
    private long expireAfterWrite;

    /**
     * 定时回收（Timed Eviction）
     * CacheBuilder提供两种定时回收的方法：缓存项在给定时间内没有被读/写访问，则回收。
     * 请注意这种缓存的回收顺序和基于大小回收一样。
     * expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准。
     */
    private long expireAfterAccess;

    /**
     * 基于容量的回收
     * 如果要规定缓存项的数目不超过固定值，只需使用CacheBuilder.maximumSize(long)。
     * 缓存将尝试回收最近没有使用或总体上很少使用的缓存项。
     * 警告：在缓存项的数目达到限定值之前，缓存就可能进行回收操作。
     * 通常来说，这种情况发生在缓存项的数目逼近限定值时。
     * maximumSize和maximumWeight不可以同时使用
     */
    private long maximumSize;

    /**
     * 不同的缓存项有不同的权重,例如，如果你的缓存值，占据完全不同的内存空间，你可以使用CacheBuilder.weigher(Weigher)指定一个权重函数，
     * 并且用CacheBuilder.maximumWeight(long)指定最大总重。
     * 在权重限定场景中，除了要注意回收也是在重量逼近限定值时就进行了，
     * 还要知道重量是在缓存创建时计算的，因此要考虑重量计算的复杂度。
     * maximumSize和maximumWeight不可以同时使用
     */
    private long maximumWeight;

    /**
     * 当缓存项上一次更新操作之后的多久会被刷新
     */
    private long refreshAfterWrite;

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public long getExpireAfterWrite() {
        return expireAfterWrite;
    }

    public void setExpireAfterWrite(long expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
    }

    public long getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public void setExpireAfterAccess(long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
    }

    public long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public long getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(long maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public long getRefreshAfterWrite() {
        return refreshAfterWrite;
    }

    public void setRefreshAfterWrite(long refreshAfterWrite) {
        this.refreshAfterWrite = refreshAfterWrite;
    }
}
