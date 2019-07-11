package com.recse4cloud.web.service.service;

import com.github.pagehelper.PageInfo;
import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.common.util.excel.ExcelUtil;
import com.recse4cloud.common.util.excel.ExcleTitleEntity;
import com.recse4cloud.common.util.log.Log;
import com.recse4cloud.web.service.ManageData;
import com.recse4cloud.web.service.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class BaseService<T, M extends BaseMapper<T>> implements ManageService<T> {
    @Autowired
    protected M baseMapper;

    @Log
    @Override
    public Object insert(ManageData<T> manageData) {
        if (baseMapper.insert(manageData.getData()) == 1)
            return ServiceResultHelper.success();
        return ServiceResultHelper.fail();
    }

    @Log
    @Override
    public Object update(ManageData<T> manageData) {
        if (baseMapper.update(manageData.getData()) == 1)
            return ServiceResultHelper.success();
        return ServiceResultHelper.fail();
    }

    @Log
    @Override
    public Object delete(ManageData<T> manageData) {
        return update(manageData);
    }

    @Log
    @Override
    public Object detail(ManageData<T> manageData) {
        T t = baseMapper.detail(manageData.getData());
        if (t == null) {
            return ServiceResultHelper.fail();
        }
        return ServiceResultHelper.success(t);
    }

    @Log
    @Override
    public Object listPage(ManageData<T> manageData) {
        return ServiceResultHelper.success(new PageInfo<>(baseMapper.list(manageData.getData())));
    }

    @Log
    @Override
    public Object listSelect(ManageData<T> manageData) {
        return ServiceResultHelper.success(baseMapper.listSelect(manageData.getData()));
    }

    @Override
    public File export(ManageData<T> manageData) {
        List<Object> dataList = exportData(manageData.getData());
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        List<ExcleTitleEntity> exportTitles = exportTitles();
        if (exportTitles == null || exportTitles.isEmpty()) {
            return null;
        }
        return ExcelUtil.excleExport(exportTitles, dataList);
    }

    protected List<Object> exportData(T t) {
        return (List<Object>) baseMapper.list(t);
    }

    protected List<ExcleTitleEntity> exportTitles() {
        return null;
    }

}
