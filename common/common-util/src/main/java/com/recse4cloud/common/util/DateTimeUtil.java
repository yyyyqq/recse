package com.recse4cloud.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private static final ThreadLocal<SimpleDateFormat> LOCAL_SimpleDateFormat = new ThreadLocal<>();

    static final String TO_DATE = "yyyy-MM-dd";
    static final String TO_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化，使用线程副本
     *
     * @return
     */
    public static SimpleDateFormat getLocalSimpleDateFormat() {
        SimpleDateFormat localSDF = LOCAL_SimpleDateFormat.get();
        if (localSDF == null) {
            localSDF = new SimpleDateFormat();
        }
        LOCAL_SimpleDateFormat.set(localSDF);
        return localSDF;
    }

    /**
     * String 转 date
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date str2Date(String str, String pattern) {
        SimpleDateFormat localSDF = getLocalSimpleDateFormat();
        localSDF.applyLocalizedPattern(pattern);
        try {
            return localSDF.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * String 转 date
     *
     * @param str
     * @return
     */
    public static Date str2DateDay(String str) {
        return str2Date(str, TO_DATE);
    }

    /**
     * Date 转 String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String date2Str(Date date, String pattern) {
        SimpleDateFormat localSDF = getLocalSimpleDateFormat();
        localSDF.applyLocalizedPattern(pattern);
        return localSDF.format(date);
    }
}
