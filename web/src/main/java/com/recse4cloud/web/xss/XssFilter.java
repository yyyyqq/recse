package com.recse4cloud.web.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS攻击过滤器
 */
public class XssFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        if (path.contains("/css") || path.contains("/js") || path.contains("/images") || path.contains("/fonts")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletRequest request = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
