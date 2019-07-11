package com.recse4cloud.core.utils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 常量
 */
public interface IConstants {
    /**
     * 成功代码
     */
    String SUCCESS_CODE = "00000";

    /**
     * 成功信息
     */
    String SUCCESS_MSG = "操作成功";

    /**
     * 失败信息
     */
    String FAIL_MSG = "操作失败";

    /**
     * 系统配置文件的路径
     */
    String SYSTEM_CONFIG_FILE_PATH = "file:${user.home}/conf/";
    /**
     * 配置文件磁盘路径
     */
    String ACTIVE_MQ_PROPERTY = SYSTEM_CONFIG_FILE_PATH + "activeMQ.properties";
    String DB_PROPERTY = SYSTEM_CONFIG_FILE_PATH + "db.properties";
    String REDIS_PROPERTY = SYSTEM_CONFIG_FILE_PATH + "redis.properties";

    String MAPPER_RESOURCES = "classpath:mapper/*Mapper.xml";

    /**
     * 忽略认证的路径ignorePaths
     */
    Set<String> IGNORE_PATHS = new LinkedHashSet();
    /**
     * APOLLO默认的命名空间
     */
    String APOLLO_NAMESPACE = "TEST1.emergency";
}
