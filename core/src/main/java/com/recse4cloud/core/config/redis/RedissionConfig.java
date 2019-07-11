package com.recse4cloud.core.config.redis;

import com.recse4cloud.common.core.Logger;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.util.Arrays;

/**
 * redis连接配置
 */
@Configuration
public class RedissionConfig {
    @Autowired
    RedissionProperty redisProperty;

    final String redisPrefix = "redis://";

    @Bean
    public RedisConnectionFactory redissonConnectionFactory() {
        return new RedissonConnectionFactory(redissonClient());
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        return Redisson.create(config());
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * redis连接配置信息
     *
     * @return
     */
    public Config config() {
        Logger.info("REDIS_CONFIG----------------", getClass());
        Logger.info(redisProperty, getClass());
        //单机
        if (redisProperty.isUseCluster()) {
            return clusterServerConfig();
        }
        return singleServerConfig();
    }

    /**
     * 单机
     *
     * @return
     */
    private Config singleServerConfig() {
        Config config = new Config();
        config.useSingleServer()
                .setTimeout(redisProperty.getTimeout())
                .setConnectTimeout(redisProperty.getConnectTimeout())
                .setIdleConnectionTimeout(redisProperty.getIdleConnectionTimeout())
                .setConnectionMinimumIdleSize(redisProperty.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(redisProperty.getConnectionPoolSize())
                .setDatabase(redisProperty.getDatabase())
                .setAddress(redisPrefix + redisProperty.getNodeAddresses());
        return config;
    }

    /**
     * 集群
     *
     * @return
     */
    private Config clusterServerConfig() {
        Config config = new Config();
        config.useClusterServers()
                .setTimeout(redisProperty.getTimeout())
                .setConnectTimeout(redisProperty.getConnectTimeout())
                .setIdleConnectionTimeout(redisProperty.getIdleConnectionTimeout())
                .setScanInterval(redisProperty.getScanInterval())
                .setMasterConnectionMinimumIdleSize(redisProperty.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(redisProperty.getMasterConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(redisProperty.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(redisProperty.getSlaveConnectionPoolSize())
                .setPassword(redisProperty.getPassword())
                .addNodeAddress(getNodeAddress());
        return config;
    }

    /**
     * 集群节点
     *
     * @return
     */
    private String[] getNodeAddress() {
        if (StringUtils.isBlank(redisProperty.getNodeAddresses())) {
            throw new RedisException("redis node addresses is emptry");
        }
        String[] adressArray = redisProperty.getNodeAddresses().split(",");
        return (String[]) Arrays.stream(adressArray).filter(a -> !StringUtils.isBlank(a)).map(a -> redisPrefix + a).toArray();
    }
}
