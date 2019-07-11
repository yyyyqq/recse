package com.recse4cloud.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface IHttpSecurityConfigure {
    /**
     * 配置security的认证
     *
     * @param http
     */
    void configure(HttpSecurity http) throws Exception;
}
