package com.recse4cloud.core.config.jdbc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import static com.recse4cloud.core.utils.IConstants.DB_PROPERTY;

/**
 * 数据源配置信息
 */
@ConfigurationProperties(prefix = "db")
@Data
@PropertySource(value = DB_PROPERTY)
public class DataSourceProperty {
    private String url;
    private String user;
    private String password;
    /**
     * mybatis Xml 文件存放包
     */
    private String xmlPackage = "classpath:mapper/*Mapper.xml";
    /**
     * mybatis 实体类存放包
     */
    private String typePackage = "com.*.db.entity";
    /**
     * 空闲连接超时时间
     */
    private int idleTimeout = 10000;
    /**
     * 最大保持时间
     */
    private int maxLiftTime = 1800000;
    /**
     * 连接超时时间
     */
    private int connectionTimeout = 3000;
    /**
     * 最大连接数
     */
    private int maximumPoolSize = 15;
    /**
     * 最下空闲连接数
     */
    private int minimumIdle = 5;
}
