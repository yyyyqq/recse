package com.recse4cloud.security;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpResponseUtil {

    public static void responseJson(HttpServletResponse response, Object o) throws IOException {
        String json = JSON.toJSONString(o);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
