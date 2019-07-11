package com.recse4cloud.core.utils;

import com.recse4cloud.core.service.RedisUtil;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * 记录编号生成器
 */
public class RecordNoCreator {

    public static RedisUtil redisUtil;

    /**
     * 生产订单编号：
     * 当前时间+自增序列
     *
     * @param type
     * @return
     */
    public static String create(String type, int length) {
        LocalDateTime ldt = LocalDateTime.now();
        DecimalFormat df = new DecimalFormat("000000");
        String no = ldt.format(ofPattern("yyyyMMdd")) + randomNumeric(1) + df.format(redisUtil.increment(ldt.format(ofPattern("yyyyMMdd")) + type));
        return no.length() > length ? no.substring(no.length() - length) : no;
    }

    /**
     * 默认最长长度11位的编号
     *
     * @param type
     * @return
     */
    public static String create(String type) {
        return create(type, 15);
    }

}
