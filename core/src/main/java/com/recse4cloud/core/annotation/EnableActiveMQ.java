package com.recse4cloud.core.annotation;

import com.recse4cloud.core.config.mq.ActiveMQConfig;
import com.recse4cloud.core.config.mq.ActiveMQProperties;
import com.recse4cloud.core.config.mq.ProductService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

import java.lang.annotation.*;

/**
 * Created by YYQ on 2017/11/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        ActiveMQConfig.class,
        ProductService.class
})
@EnableJms
@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public @interface EnableActiveMQ {
}
