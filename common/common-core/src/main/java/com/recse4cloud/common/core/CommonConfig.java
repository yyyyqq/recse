package com.recse4cloud.common.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class CommonConfig {

    @Autowired
    public CommonConfig(MessageSource messageSource) {
        ServiceResultHelper.source = messageSource;
    }
}
