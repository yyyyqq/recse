package com.recse4cloud.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * redis操作
 */
public interface RedisUtil {

    /**
     * 获取自增数列
     *
     * @param key
     * @return
     */
    long increment(String key);

    /**
     * 添加key-value缓存
     *
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    void set(String key, Object value, long time, TimeUnit unit);

    /**
     * 添加key-value缓存
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 查询key
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除key对应的值
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * 添加数据到list
     *
     * @param key
     * @param value
     */
    boolean addList(String key, Object value);

    /**
     * 分页查询list
     *
     * @param key
     * @param start 开始
     * @param end   结束
     * @return
     */
    List<Object> getListValues(String key, int start, int end);

    /**
     * 查询所有
     *
     * @param key
     * @return
     */
    List<Object> getListValues(String key);

    /**
     * 删除数据
     *
     * @param key
     * @param values
     * @return
     */
    boolean removeListValues(String key, Collection values);

    /**
     * 查询符合规则的数据
     *
     * @param key
     * @param predicate 开始
     * @return
     */
    List<Object> getListValues(String key, Predicate<? super Object> predicate);

    /**
     * @param key
     * @param value
     * @return
     */
    boolean addSet(String key, Object value);

    /**
     * @param key
     * @param value
     * @return
     */
    boolean removeSetValue(String key, Object value);

    /**
     * @param key
     * @param values
     * @return
     */
    boolean removeSetValue(String key, Collection values);

    /**
     * 查询符合条件的数据
     *
     * @param key
     * @param predicate
     * @return
     */
    Set<Object> getSetValues(String key, Predicate<? super Object> predicate);

    /**
     * @param key
     * @param hk
     * @param value
     * @return
     */
    boolean addMap(String key, String hk, Object value);

    /**
     * @param key
     * @param hk
     * @return
     */
    Object getMapValue(String key, String hk);

    /**
     * 查询key的所有数据
     *
     * @param key
     * @return
     */
    Collection<Object> getMapValues(String key);

    /**
     * 判断map中是否包含相应的key
     *
     * @param key
     * @param hk
     * @return
     */
    boolean mapContainsKey(String key, String hk);

    /**
     * 批量删除
     *
     * @param key
     * @param hks
     * @return
     */
    boolean removeMapValues(String key, Collection hks);

    /**
     * 单个删除
     *
     * @param key
     * @param hk
     * @return
     */
    boolean removeMapValue(String key, String hk);


}

