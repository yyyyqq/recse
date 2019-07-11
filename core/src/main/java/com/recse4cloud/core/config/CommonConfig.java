package com.recse4cloud.core.config;

import com.recse4cloud.core.service.RedisUtil;
import com.recse4cloud.core.utils.RecordNoCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class CommonConfig {
    @Autowired
    public CommonConfig(RedisUtil redisUtil) {
        RecordNoCreator.redisUtil = redisUtil;
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setCorePoolSize(1);//最小线程池数
        taskExecutor.setMaxPoolSize(20);//最大线程池
        taskExecutor.setQueueCapacity(100);//缓冲队列
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return taskExecutor;
    }

}
