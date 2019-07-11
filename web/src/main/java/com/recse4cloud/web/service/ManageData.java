package com.recse4cloud.web.service;

import com.github.pagehelper.PageHelper;
import com.recse4cloud.web.service.service.ManageService;
import lombok.Data;

import java.io.File;

/**
 * 管理数据
 */
@Data
public class ManageData<T> {
    /**
     * 管理接口
     */
    ManageService manageService;
    /**
     * 每页数量
     */
    int pageSize = 15;
    /**
     * 页码
     */
    int pageNum = 1;
    /**
     * 操作员编号
     */
    String operCode;
    /**
     * 管理数据
     */
    T data;

    /**
     * 解析页码
     *
     * @param pageNum
     * @return
     */
    public int getPageNum(Integer pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }

    /**
     * 分页
     */
    public void startPage() {
        PageHelper.startPage(getPageNum(pageNum), pageSize, true);
    }

    /**
     * 添加
     *
     * @return
     */
    public Object insert() {
        return manageService.insert(this);
    }

    /**
     * 修改
     *
     * @return
     */
    public Object update() {
        return manageService.update(this);
    }

    /**
     * 删除
     *
     * @return
     */
    public Object delete() {
        return manageService.delete(this);
    }

    /**
     * 详情
     *
     * @return
     */
    public Object detail() {
        return manageService.detail(this);
    }

    /**
     * 分页列表
     *
     * @return
     */
    public Object listPage() {
        startPage();
        return manageService.listPage(this);
    }

    /**
     * 下拉列表，不分页
     *
     * @return
     */
    public Object listSelect() {
        return manageService.listSelect(this);
    }

    /**
     * 导出列表，不分页
     *
     * @return
     */
    public File export() {
        return manageService.export(this);
    }
}
