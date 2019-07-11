package com.recse4cloud.pay.wx.account.loader;

import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXAuth;

/**
 * 基础支持中的access_token,该access_token用于调用其他接口
 */
public interface IAccessTokenLoader {

    /**
     * 获取access token 的url
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    String WX_TOKEN = "UAXNENNwsJHQcuhydFjqxwsJH";

    /**
     * 获取基础支持中的access_token
     * 同步请求
     *
     * @param accountInfo 支付账户信息
     * @return
     */
    WXAuth accessToken(IAccountInfo accountInfo);

}
