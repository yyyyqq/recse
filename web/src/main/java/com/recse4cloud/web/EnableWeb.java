package com.recse4cloud.web;

import com.recse4cloud.core.annotation.EnableConfigs;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigs
@EnableCommonWeb
public @interface EnableWeb {
}
