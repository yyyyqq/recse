package com.recse4cloud.web.service.controller;

import com.recse4cloud.common.util.FileExportUtil;
import com.recse4cloud.web.service.ManageData;
import com.recse4cloud.web.service.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class BaseController<T, S extends BaseService> {

    @Autowired
    protected S manageService;

    protected ManageData manageData(T t) {
        ManageData manageData = new ManageData();
        manageData.setManageService(manageService);
        manageData.setData(t);
        return manageData;
    }

    private final String INSERT = "/insert";
    private final String UPDATE = "/update";
    private final String DELETE = "/delete";
    private final String DETAIL = "/detail";
    private final String LIST = "/list";
    private final String SELECT = "/select";
    private final String EXPORT = "/export";

    /**
     * 添加数据
     *
     * @param t
     * @return
     */
    @PostMapping(INSERT)
    public Object insert(T t) {
        return manageData(t).insert();
    }

    /**
     * 更新数据
     *
     * @param t
     * @return
     */
    @PostMapping(UPDATE)
    public Object update(T t) {
        return manageData(t).update();
    }

    /**
     * 删除数据
     *
     * @param t
     * @return
     */
    @PostMapping(DELETE)
    public Object delete(T t) {
        return manageData(t).delete();
    }

    /**
     * 数据详情
     *
     * @param t
     * @return
     */
    @PostMapping(DETAIL)
    public Object detail(T t) {
        return manageData(t).detail();
    }

    /**
     * 分页列表
     *
     * @param t
     * @return
     */
    @PostMapping(LIST)
    public Object listPage(T t, Integer pageNum, Integer pageSize) {
        ManageData manageData = manageData(t);
        if (pageNum != null && pageNum > 1)
            manageData.setPageNum(pageNum);
        if (pageSize != null && pageSize > 1)
            manageData.setPageSize(pageSize);
        return manageData.listPage();
    }

    /**
     * 下拉列表，不分页
     *
     * @param t
     * @return
     */
    @PostMapping(SELECT)
    public Object listSelect(T t) {
        return manageData(t).listSelect();
    }

    /**
     * 导出列表，不分页
     *
     * @param t
     * @return
     */
    @PostMapping(EXPORT)
    public ResponseEntity<FileSystemResource> export(T t) {
        return FileExportUtil.export(manageData(t).export());
    }
}
