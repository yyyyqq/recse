package com.recse4cloud.pay.wx.account.loader;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.api.HttpManager;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXAuth;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OpenIdLoader implements IOpenIdLoader, Callback {

    WXAuth wxAuth;

    /**
     * 用户同意授权，获取code
     *
     * @param accountInfo 微信支付信息
     * @param redirectUri 授权后微信服务器重定向的业务url ,需要进行urlencode
     * @param state
     * @return
     */
    @Override
    public String authorizeUrl(IAccountInfo accountInfo, String redirectUri, Object state) {
        Assert.notNull(accountInfo, "缺少支付账户信息");
        try {
            redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
            if (accountInfo.type() == 1) { //第三方认证渠道
                String host = accountInfo.platformPublicKey();
                return String.format(CLIENT_AUTHORIZE_URL, host, accountInfo.appId(), redirectUri);
            }
            return String.format(AUTHORIZE_URL, accountInfo.appId(), redirectUri, state);
        } catch (UnsupportedEncodingException e) {
            Logger.error(e, getClass());
        }
        return null;
    }

    /**
     * 通过Code换取openid等信息
     *
     * @param accountInfo 微信支付信息
     * @param code        code
     * @return
     */
    @Override
    public WXAuth auth(IAccountInfo accountInfo, String code) {
        Assert.notNull(accountInfo, "缺少支付账户信息");
        String url = String.format(OPENID_URL, accountInfo.appId(), accountInfo.platformPublicKey(), code);
        return doGet(url);
    }


    /**
     * 刷新授权,目前不知道在什么具体作用是什么
     *
     * @param appid
     * @param refleshToken auth中返回
     * @return
     */
    @Override
    public WXAuth refleshAuth(String appid, String refleshToken) {
        String url = String.format(REFLESH_AUTH_URL, appid, refleshToken);
        return doGet(url);
    }

    /**
     * 发送请求
     *
     * @param url
     * @return
     */
    private WXAuth doGet(String url) {
        HttpManager.getExecute(url, this);
        return wxAuth;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        wxAuth = new WXAuth();
        wxAuth.setErrcode(1000);
        wxAuth.setErrmsg(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Logger.info(json, getClass());
        wxAuth = JSON.parseObject(json, WXAuth.class);
    }

}
