package com.recse4cloud.common.core;

import com.alibaba.fastjson.JSON;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * 日志
 */
public class Logger {

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param t the exception (throwable) to log
     */
    public static void error(@NonNull Throwable t, @NonNull Class<?> cls) {
        LoggerFactory.getLogger(cls).error(t.getMessage(), t);
    }

    public static void error(@NonNull String msg, @NonNull Class<?> cls) {
        LoggerFactory.getLogger(cls).error(msg);
    }

    /**
     * @param obj
     * @param cls
     */
    public static void info(@NonNull Object obj, @NonNull Class<?> cls) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(cls);
        if (obj instanceof String) {
            logger.info(obj.toString());
        } else {
            logger.info(JSON.toJSONString(obj));
        }
    }

    /**
     * @param obj
     * @param cls
     */
    public static void debug(@NonNull Object obj, @NonNull Class<?> cls) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(cls);
        if (obj instanceof String) {
            logger.debug(obj.toString());
        } else {
            logger.debug(JSON.toJSONString(obj));
        }
    }

}
