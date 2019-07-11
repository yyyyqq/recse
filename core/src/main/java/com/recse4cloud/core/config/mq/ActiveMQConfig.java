package com.recse4cloud.core.config.mq;

import com.recse4cloud.common.core.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Created by YYQ on 2017/11/14.
 */
//@Configuration
public class ActiveMQConfig {
    @Autowired
    private com.recse4cloud.core.config.mq.ActiveMQProperties properties;

    @Primary
    @Bean
    public ActiveMQProperties activeMQProperties() {

        Logger.info("ActiveMQ Config--------------", getClass());
        Logger.info(properties, getClass());

        ActiveMQProperties activeMQProperties = new ActiveMQProperties();
        activeMQProperties.setBrokerUrl(properties.getBrokerUrl());
        activeMQProperties.setInMemory(properties.isInMemory());
        if (!StringUtils.isBlank(properties.getUser())) {
            activeMQProperties.setUser(properties.getUser());
        }
        if (!StringUtils.isBlank(properties.getPassword())) {
            activeMQProperties.setUser(properties.getPassword());
        }

//        JmsPoolConnectionFactoryProperties pool = new JmsPoolConnectionFactoryProperties();
//        pool.setEnabled(properties.isPoolEnable());
//        if (properties.isPoolEnable()) {
//            pool.set(properties.getPoolExpiryTimeout());
//            pool.setIdleTimeout(properties.getPoolIdleTimeout());
//            pool.setMaxConnections(properties.getPoolMaxConnections());
//        }
//        activeMQProperties.setPool(pool);
        return activeMQProperties;
    }
}
