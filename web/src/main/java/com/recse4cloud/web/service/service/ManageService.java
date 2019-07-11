package com.recse4cloud.web.service.service;

import com.recse4cloud.web.service.ManageData;

import java.io.File;

/**
 * 数据表管理接口
 */
public interface ManageService<T> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    Object insert(ManageData<T> data);

    /**
     * 更新数据
     *
     * @param data
     * @return
     */
    Object update(ManageData<T> data);

    /**
     * 删除数据
     *
     * @param data
     * @return
     */
    Object delete(ManageData<T> data);

    /**
     * 数据详情
     *
     * @param data
     * @return
     */
    Object detail(ManageData<T> data);

    /**
     * 分页列表
     *
     * @param data
     * @return
     */
    Object listPage(ManageData<T> data);

    /**
     * 下拉列表，不分页
     *
     * @param data
     * @return
     */
    Object listSelect(ManageData<T> data);

    /**
     * 导出列表，不分页
     *
     * @param data
     * @return
     */
    File export(ManageData<T> data);
}
