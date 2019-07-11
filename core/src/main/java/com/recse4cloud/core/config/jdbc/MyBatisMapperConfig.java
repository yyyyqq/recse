package com.recse4cloud.core.config.jdbc;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mybatis配置
 */
@Order(100)
@AutoConfigureAfter(DataSourceConfig.class)
@EnableTransactionManagement
public class MyBatisMapperConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSourceProperty property;

    //    @Bean(name = "druidScannerConfigurer")
//    public static MapperScannerConfigurer scannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.*.db.mapper"); //mybatis接口类存放目录
//        return mapperScannerConfigurer;
//    }
    @Bean
    @Primary
    public MybatisProperties mybatisProperties() {
        MybatisProperties mybatisProperties = new MybatisProperties();
        mybatisProperties.setMapperLocations(new String[]{property.getXmlPackage()});
        mybatisProperties.setTypeAliasesPackage(property.getTypePackage());
        return mybatisProperties;
    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //分页合理化,true 插件会帮忙把越界的页面归为边界值.导致越界依然能查出数据
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        //设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(MybatisProperties properties) {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
//        //添加插件
//        bean.setPlugins(new Interceptor[]{pageHelper()});
//        try {
//            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//            bean.setMapperLocations(resolver.getResources(IConstants.MAPPER_RESOURCES));
//            return bean.getObject();
//        } catch (Exception e) {
//            Logger.error(e, getClass());
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
