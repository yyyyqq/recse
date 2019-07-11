package com.recse4cloud.web.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

///**
// * 自定义国际化处理
// */
//public class DefaultLocaleResolver implements LocaleResolver {
//    @Override
//    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
//        String l = httpServletRequest.getParameter("l");
//        Locale locale = Locale.getDefault();
//        if (!StringUtils.isEmpty(l)) {
//            String[] split = l.split("_");
//            locale = new Locale(split[0], split[1]);
//        }
//        return locale;
//    }
//
//    @Override
//    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
//
//    }
//}
