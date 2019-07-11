package com.recse4cloud.cloud.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

/**
 * 服务注册实例配置
 */
public class EurekaInstanceProperty extends EurekaInstanceConfigBean {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.cloud.client.hostname}")
    private String hostName;
    @Value("${server.port}")
    private int serverPort;

    private final String buildInstanceId() {
        return hostName + ":" + appName + ":" + serverPort;
    }

    public EurekaInstanceProperty(InetUtils inetUtils) {
        super(inetUtils);
        setAppname(appName);
        setAppGroupName(appName);
        setInstanceId(buildInstanceId());
    }


    @Override
    public String getInstanceId() {
        return buildInstanceId();
    }

    @Override
    public String getAppname() {
        return this.appName;
    }

    @Override
    public String getAppGroupName() {
        return this.appName;
    }
}
