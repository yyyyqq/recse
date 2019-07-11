package com.recse4cloud.cloud.annotation;

import com.recse4cloud.cloud.IConstants;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableApollo {
    /**
     * Apollo namespaces to inject configuration into Spring Property Sources.
     */
    @AliasFor(annotation = EnableApollo.class, attribute = "value")
    String[] value() default {IConstants.APOLLO_NAMESPACE};

    /**
     * The order of the apollo config, default is {@link Ordered#LOWEST_PRECEDENCE}, which is Integer.MAX_VALUE.
     * If there are properties with the same name in different apollo configs, the apollo config with smaller order wins.
     *
     * @return
     */
    @AliasFor(annotation = EnableApollo.class, attribute = "order")
    int order() default Ordered.LOWEST_PRECEDENCE;
}
