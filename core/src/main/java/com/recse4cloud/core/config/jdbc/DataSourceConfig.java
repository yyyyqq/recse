package com.recse4cloud.core.config.jdbc;

import com.recse4cloud.common.core.Logger;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
public class DataSourceConfig {

    @Autowired
    DataSourceProperty dataSourceProperty;

    /**
     * 数据源
     *
     * @return
     */
    @Bean("dataSource")
    public DataSource dataSource() {
        Logger.info("DATASOURCE_CONFIG---------------", getClass());
        Logger.info(dataSourceProperty, getClass());
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceProperty.getUrl());
        hikariDataSource.setUsername(dataSourceProperty.getUser());
        hikariDataSource.setPassword(dataSourceProperty.getPassword());
        hikariDataSource.setIdleTimeout(dataSourceProperty.getIdleTimeout());
        hikariDataSource.setMaxLifetime(dataSourceProperty.getMaxLiftTime());
        hikariDataSource.setConnectionTimeout(dataSourceProperty.getConnectionTimeout());
        hikariDataSource.setMaximumPoolSize(dataSourceProperty.getMaximumPoolSize());
        hikariDataSource.setMinimumIdle(dataSourceProperty.getMinimumIdle());
        return hikariDataSource;
    }
}
