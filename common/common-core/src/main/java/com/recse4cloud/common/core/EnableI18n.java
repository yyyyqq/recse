package com.recse4cloud.common.core;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CommonConfig.class)
public @interface EnableI18n {
}
