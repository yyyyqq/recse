package com.recse4cloud.cloud.eureka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.recse4cloud.cloud.IConstants.EUREKA_PROPERTY;

/**
 * eureka连接配置信息
 */
@ConfigurationProperties(prefix = "eureka")
@Setter
@Getter
@PropertySource(value = EUREKA_PROPERTY)
public class EurekaClientProperty extends EurekaClientConfigBean {
    /**
     * 注册中心地址
     */
    private String serverUrls;
    /**
     * 注册信息拉取间隔时间，单位：秒
     */
    private int registryFetchIntervalSeconds = 30;
    /**
     * 注册中心连接超时时间，单位：秒
     */
    int eurekaServerConnectTimeoutSeconds = 5;

    @Override
    public List<String> getEurekaServerServiceUrls(String var1) {
        List<String> list = new ArrayList<>();
        list.add(serverUrls);
        return list;
    }

    @Override
    public int getRegistryFetchIntervalSeconds() {
        return this.registryFetchIntervalSeconds;
    }

    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        return this.eurekaServerConnectTimeoutSeconds;
    }

}
