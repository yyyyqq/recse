package com.recse4cloud.core.config.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static com.recse4cloud.core.utils.IConstants.ACTIVE_MQ_PROPERTY;

/**
 * Created by YYQ on 2017/11/14.
 */

@Data
@Component
@ConfigurationProperties(prefix = "activemq")
@PropertySource(value = ACTIVE_MQ_PROPERTY)
public class ActiveMQProperties {
    private String brokerUrl;
    private boolean inMemory;
    private String user;
    private String password;
    private boolean poolEnable;
    private Long poolExpiryTimeout;
    private Integer poolIdleTimeout;
    private Integer poolMaxConnections;
}
