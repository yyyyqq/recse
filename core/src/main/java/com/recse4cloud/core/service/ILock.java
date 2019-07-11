package com.recse4cloud.core.service;

import java.util.concurrent.TimeUnit;

/**
 * 锁
 */
public interface ILock {
    /**
     * 尝试加锁
     *
     * @param key
     * @param waitTime  等待时间
     * @param leaseTime 加锁时长
     * @param unit      时间单位
     */
    boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 无等待时间，尝试加锁
     *
     * @param key
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean tryLock(String key, long leaseTime, TimeUnit unit);

    /**
     * 解锁
     *
     * @param key
     */
    void unlock(String key);
}
