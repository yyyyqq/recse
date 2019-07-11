package com.recse4cloud.common.util.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        LoggerAspect.class
})
public @interface EnableLog {
}
