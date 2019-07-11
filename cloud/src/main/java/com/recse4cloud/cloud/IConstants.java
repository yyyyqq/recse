package com.recse4cloud.cloud;

/**
 * 常量类
 */
public interface IConstants {
    /**
     * APOLLO默认的命名空间
     */
    String APOLLO_NAMESPACE = "TEST1.emergency";
    String SYSTEM_CONFIG_FILE_PATH = "file:${user.home}/conf/";
    /**
     * 配置文件磁盘路径
     */
    String EUREKA_PROPERTY = SYSTEM_CONFIG_FILE_PATH + "eureka.properties";
}
