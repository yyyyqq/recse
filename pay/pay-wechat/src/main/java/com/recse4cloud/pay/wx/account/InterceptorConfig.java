package com.recse4cloud.pay.wx.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private IOpenIdService openIdService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WXOpenIdInterceptor(openIdService)).addPathPatterns(openIdService.includePath()).excludePathPatterns(openIdService.excludePath());
    }
}
