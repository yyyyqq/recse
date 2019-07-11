package com.recse4cloud.web.util;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.ServiceResultHelper;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import java.io.IOException;

public class HttpResponseUtil {

    public static void responseJson(ServletResponse response, Object o) throws IOException {
        String json = JSON.toJSONString(o);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    public static void responseJson(ServletResponse response, String i18nCode) throws IOException {
       responseJson(response, ServiceResultHelper.result(i18nCode));
    }
}
