package com.recse4cloud.common.util.log;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.METHOD})  //注解范围
@Retention(RetentionPolicy.RUNTIME) //用于描述注解的生命周期
@Documented
public @interface Log {
    /**
     * @return
     */
    String name() default "";
}
