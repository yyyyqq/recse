package com.recse4cloud.pay.wx.account;

import com.recse4cloud.common.core.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信拦截器,如果交易来自微信则需要获取access token 以及 openId
 */
public class WXOpenIdInterceptor extends HandlerInterceptorAdapter {

    private IOpenIdService openIdService;

    public WXOpenIdInterceptor(IOpenIdService openIdService) {
        this.openIdService = openIdService;
    }

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hasOpenId(request, response)) {
            return true;
        }
        if (hasCode(request) && loadOpenId(request, response)) {
            return true;
        }
        return openIdService.getOpenIdFail(response);
    }

    /**
     * 1.判断是否基于微信客户端发起的请求
     *
     * @param request
     * @return
     */
    public boolean isBaseWxRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        boolean bl = !StringUtils.isBlank(userAgent) && userAgent.contains("micromessenger");
        Logger.info(request.getServletPath() + " - " + bl, getClass());
        return bl;
    }

    /**
     * 判断是否有微信转发回来的,附带code
     *
     * @param request
     * @return
     */
    public boolean hasCode(HttpServletRequest request) {
        String code = request.getParameter("code");
        Logger.info("code ----" + code, getClass());
        return !StringUtils.isBlank(code);
    }

    /**
     * 第三方认证直接回复openId
     *
     * @param request
     * @param response
     * @return
     */
    public boolean hasOpenId(HttpServletRequest request, HttpServletResponse response) {
        String openId = request.getParameter("openId");
        if (StringUtils.isBlank(openId)) {
            return false;
        }
        return true;
    }

    /**
     * 获取微信openId
     *
     * @param request
     * @param response
     */
    public boolean loadOpenId(HttpServletRequest request, HttpServletResponse response) {
        WXAuth wxAuth = openIdService.getWxAuth(request);
        if (wxAuth != null && !StringUtils.isBlank(wxAuth.getOpenid())) {
            request.setAttribute("openId", wxAuth.getOpenid());
            request.removeAttribute("code");//删除code参数
            return true;
        }
        return false;
    }
}
