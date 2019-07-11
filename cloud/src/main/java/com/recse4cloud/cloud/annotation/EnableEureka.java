package com.recse4cloud.cloud.annotation;

import com.recse4cloud.cloud.eureka.EurekaClientProperty;
import com.recse4cloud.cloud.eureka.EurekaConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableDiscoveryClient
@EnableFeignClients
@Import({EurekaConfig.class, EurekaClientProperty.class})
public @interface EnableEureka {
}
