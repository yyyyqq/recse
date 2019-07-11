package com.recse4cloud.web;

import com.recse4cloud.common.core.EnableI18n;
import com.recse4cloud.common.util.log.EnableLog;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableLog
@Import(CommonConfig.class)
@ServletComponentScan
@EnableI18n
public @interface EnableCommonWeb {
}
