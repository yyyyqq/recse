package com.recse4cloud.common.core;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class ServiceResultHelper {

    public static MessageSource source;

    /**
     * @return
     */
    public static RestfullData success() {
        return success(null);
    }

    /**
     * 返回成功
     *
     * @param data
     * @return
     */
    public static RestfullData success(Object data) {
        RestfullData result = new RestfullData();
        result.setCode(IConstants.SUCCESS_CODE);
        result.setMsg(IConstants.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    /**
     * 返回结果
     *
     * @param code
     * @param msg
     * @return
     */
    public static RestfullData result(String code, String msg, Object data) {
        RestfullData result = new RestfullData();
        result.setCode(code);
        result.setData(data);
        if (StringUtils.isEmpty(msg)) {
            result.setMsg(getMessage(IConstants.SUCCESS_CODE));
        } else {
            result.setMsg(msg);
        }
        return result;
    }

    public static RestfullData result(String code, String msg) {
        return result(code, msg, null);
    }

    /**
     * 返回结果
     *
     * @param code
     * @return
     */
    public static RestfullData result(String code, Object data) {
        return result(code, getMessage(code), data);
    }

    /**
     * 返回结果
     *
     * @param code
     * @return
     */
    public static RestfullData result(String code) {
        return result(code, getMessage(code), null);
    }

    /**
     * 返回失败
     *
     * @return
     */
    public static RestfullData fail() {
        return result(IConstants.FAIL_CODE);
    }

    /**
     * 获取国际化信息
     *
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        if (source == null) {
            return IConstants.SUCCESS_CODE.equals(key) ? IConstants.SUCCESS_MSG : IConstants.FAIL_MSG;
        }
        return source.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化信息
     *
     * @param key
     * @return
     */
    public static String getMessage(String key, Object[] objects) {
        if (source == null) {
            return IConstants.SUCCESS_CODE.equals(key) ? IConstants.SUCCESS_MSG : IConstants.FAIL_MSG;
        }
        return source.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}
