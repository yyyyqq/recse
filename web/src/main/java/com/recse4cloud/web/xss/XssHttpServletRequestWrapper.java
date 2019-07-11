package com.recse4cloud.web.xss;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS过滤规则
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] params = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(params)) {
            return params;
        }
        String[] temp = new String[params.length];
        int i = 0;
        for (String p : params) {
            temp[i++] = cleanXSS(p);
        }
        return temp;
    }

    @Override
    public String getParameter(String name) {
        String param = super.getParameter(name);
        if (!StringUtils.isBlank(param)) {
            return cleanXSS(param);
        }
        return param;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (!StringUtils.isBlank(header)) {
            return cleanXSS(header);
        }
        return header;
    }

    private final String cleanXSS(String value) {
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

}
