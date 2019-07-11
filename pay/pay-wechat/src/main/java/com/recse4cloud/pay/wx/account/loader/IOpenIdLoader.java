package com.recse4cloud.pay.wx.account.loader;

import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXAuth;

public interface IOpenIdLoader {
    /**
     * 组装获取openID的第一步获取Code的url
     */
    String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect";
    /**
     * 第三方微信openId获取
     */
    String CLIENT_AUTHORIZE_URL = "%s/wechat/authorize?appId=%s&back=%s";
    /**
     * 刷新授权的url
     */
    String REFLESH_AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    /**
     * 获取openid的url
     */
    String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 用户同意授权，获取code
     *
     * @param accountInfo 微信支付信息
     * @param redirectUri 授权后微信服务器重定向的业务url ,需要进行urlencode
     * @param state
     * @return
     */
    String authorizeUrl(IAccountInfo accountInfo, String redirectUri, Object state);

    /**
     * 通过Code换取openid等信息
     *
     * @param accountInfo 微信支付信息
     * @param code        code
     * @return
     */
    WXAuth auth(IAccountInfo accountInfo, String code);

    /**
     * 刷新授权,目前不知道在什么具体作用是什么
     *
     * @param appid
     * @param refleshToken auth中返回
     * @return
     */
    WXAuth refleshAuth(String appid, String refleshToken);

}
