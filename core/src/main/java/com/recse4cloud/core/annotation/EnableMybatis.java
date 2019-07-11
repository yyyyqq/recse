package com.recse4cloud.core.annotation;

import com.recse4cloud.core.config.jdbc.DataSourceConfig;
import com.recse4cloud.core.config.jdbc.DataSourceProperty;
import com.recse4cloud.core.config.jdbc.MyBatisMapperConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({DataSourceProperty.class, DataSourceConfig.class, MyBatisMapperConfig.class})
public @interface EnableMybatis {

}
