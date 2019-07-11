package com.recse4cloud.core.service;

import com.recse4cloud.common.core.Logger;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * redisson实现的分布式锁
 */
@Service
public class RedissonSupport implements ILock, RedisUtil {

    @Autowired
    private RedissonClient client;

    /**
     * 尝试加锁
     *
     * @param key
     * @param waitTime  等待时间
     * @param leaseTime 加锁时长
     * @param unit      时间单位
     */
    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return client.getLock(key).tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Logger.error(e, getClass());
        }
        return false;
    }

    /**
     * 无等待时间，尝试加锁
     *
     * @param key
     * @param leaseTime
     * @param unit
     * @return
     */
    @Override
    public boolean tryLock(String key, long leaseTime, TimeUnit unit) {
        try {
            return client.getLock(key).tryLock(leaseTime, unit);
        } catch (InterruptedException e) {
            Logger.error(e, getClass());
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     */
    @Override
    public void unlock(String key) {
        client.getLock(key).unlock();
    }

    /**
     * 获取自增数列
     *
     * @param key
     * @return
     */
    @Override
    public long increment(String key) {
        return client.getAtomicLong(key).incrementAndGet();
    }

    /**
     * 添加key-value缓存
     *
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    @Override
    public void set(String key, Object value, long time, TimeUnit unit) {
        client.getBucket(key, new JsonJacksonCodec()).set(value, time, unit);
    }

    @Override
    public void set(String key, String value) {
        client.getBucket(key, new JsonJacksonCodec()).set(value);
    }

    /**
     * 查询key
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return client.getBucket(key, new JsonJacksonCodec()).get();
    }

    /**
     * 删除key对应的值
     *
     * @param key
     * @return
     */
    @Override
    public boolean delete(String key) {
        return client.getBucket(key, new JsonJacksonCodec()).delete();
    }

    /**
     * 添加数据到list
     *
     * @param key
     * @param value
     */
    @Override
    public boolean addList(String key, Object value) {
        return client.getList(key, new JsonJacksonCodec()).add(value);
    }

    /**
     * list数据分页
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Object> getListValues(String key, int start, int end) {
        return client.getList(key, new JsonJacksonCodec()).subList(start, end);
    }

    /**
     * 查询所有
     *
     * @param key
     * @return
     */
    @Override
    public List<Object> getListValues(String key) {
        return client.getList(key, new JsonJacksonCodec()).readAll();
    }

    /**
     * 删除数据
     *
     * @param key
     * @param values
     * @return
     */
    @Override
    public boolean removeListValues(String key, Collection values) {
        return client.getList(key, new JsonJacksonCodec()).removeAll(values);
    }

    /**
     * 查询符合规则的数据
     *
     * @param key
     * @param predicate
     * @return
     */
    @Override
    public List<Object> getListValues(String key, Predicate<? super Object> predicate) {
        return client.getList(key, new JsonJacksonCodec()).stream().filter(predicate).limit(100).collect(Collectors.toList());
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean addSet(String key, Object value) {
        return client.getSet(key, new JsonJacksonCodec()).add(value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean removeSetValue(String key, Object value) {
        return client.getSet(key, new JsonJacksonCodec()).remove(value);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    @Override
    public boolean removeSetValue(String key, Collection values) {
        return client.getSet(key, new JsonJacksonCodec()).removeAll(values);
    }

    /**
     * 查询符合条件的数据
     *
     * @param key
     * @param predicate
     * @return
     */
    @Override
    public Set<Object> getSetValues(String key, Predicate<? super Object> predicate) {
        return client.getSet(key, new JsonJacksonCodec()).stream().filter(predicate).limit(100).collect(Collectors.toSet());
    }

    /**
     * @param key
     * @param hk
     * @param value
     * @return
     */
    @Override
    public boolean addMap(String key, String hk, Object value) {
        return client.getMap(key, new JsonJacksonCodec()).put(hk, value) != null;
    }

    /**
     * @param key
     * @param hk
     * @return
     */
    @Override
    public Object getMapValue(String key, String hk) {
        return client.getMap(key, new JsonJacksonCodec()).get(hk);
    }

    /**
     * 查询key的所有数据
     *
     * @param key
     * @return
     */
    @Override
    public Collection<Object> getMapValues(String key) {
        return client.getMap(key, new JsonJacksonCodec()).values();
    }

    /**
     * 判断map中是否包含相应的key
     *
     * @param key
     * @param hk
     * @return
     */
    @Override
    public boolean mapContainsKey(String key, String hk) {
        return client.getMap(key, new JsonJacksonCodec()).containsKey(hk);
    }

    /**
     * 批量删除
     *
     * @param key
     * @param hks
     * @return
     */
    @Override
    public boolean removeMapValues(String key, Collection hks) {
        return client.getMap(key).fastRemove(hks.toArray()) > 0;
    }

    /**
     * 单个删除
     *
     * @param key
     * @param hk
     * @return
     */
    @Override
    public boolean removeMapValue(String key, String hk) {
        return client.getMap(key).remove(hk) != null;
    }

}
