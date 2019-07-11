package com.recse4cloud.web.service.mapper;

import java.util.List;

public interface BaseMapper<T> {

    /**
     * 新增
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 修改
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 列表
     *
     * @param t
     * @return
     */
    List<T> list(T t);

    /**
     * 详情
     *
     * @param t
     * @return
     */
    T detail(T t);

    /**
     * 下拉列表
     *
     * @param t
     * @return
     */
    List<T> listSelect(T t);
}
