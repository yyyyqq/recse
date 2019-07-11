package com.recse4cloud.core.annotation;

import com.recse4cloud.core.config.CommonConfig;
import com.recse4cloud.core.config.redis.RedissionConfig;
import com.recse4cloud.core.config.redis.RedissionProperty;
import com.recse4cloud.core.service.RedissonSupport;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用锁机制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({RedissonSupport.class, RedissionProperty.class, RedissionConfig.class, CommonConfig.class})
@EnableRedisHttpSession
public @interface EnableRedis {
}

