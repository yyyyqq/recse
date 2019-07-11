package com.recse4cloud.pay.wx.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IOpenIdService {
    /**
     * 获取授权信息
     * reques中有授权code
     *
     * @param request
     * @return
     */
    WXAuth getWxAuth(HttpServletRequest request);

    /**
     * 获取openid失败处理
     *
     * @param response
     * @return true 可请求接口，false 不可继续请求接口
     */
    boolean getOpenIdFail(HttpServletResponse response) throws IOException;

    /**
     * 加入拦截的接口
     *
     * @return
     */
    List<String> includePath();

    /**
     * 不必拦截的接口
     *
     * @return
     */
    List<String> excludePath();


}
