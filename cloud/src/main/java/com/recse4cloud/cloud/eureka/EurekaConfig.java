package com.recse4cloud.cloud.eureka;

import com.alibaba.fastjson.JSON;
import com.netflix.discovery.EurekaClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * eureka连接配置
 */
//@Configuration
public class EurekaConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Bean
//    @ConditionalOnMissingBean(EurekaClientConfig.class)
    public EurekaClientConfig eurekaClientProperty() {
        logger.info("EUREKA_CONFIG----------------");
        EurekaClientProperty eurekaClientProperty = new EurekaClientProperty();
        logger.info(eurekaClientProperty.getServerUrls());
        logger.info(JSON.toJSONString(eurekaClientProperty.getEurekaServerServiceUrls(null)));
        return eurekaClientProperty;
    }

}
