package com.recse4cloud.pay.wx.account.loader;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.api.ApiSignature;
import com.recse4cloud.pay.api.HttpManager;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXAuth;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenLoader implements IAccessTokenLoader, Callback {

    @Autowired
    IJsApiTicketLoader jsApiTicketLoad;

    /**
     * 微信授权信息
     */
    WXAuth auth;

    /**
     * 获取基础支持中的access_token
     * 同步请求
     *
     * @param accountInfo 支付账户信息
     * @return
     */
    @Override
    public WXAuth accessToken(IAccountInfo accountInfo) {
        Assert.notNull(accountInfo, "缺少支付账户信息");
        String url = String.format(ACCESS_TOKEN_URL, accountInfo.appId(), accountInfo.platformPublicKey());
        Logger.info(url, getClass());
        if (accountInfo.type() == 1) { //第三方认证渠道
            String host = accountInfo.platformPublicKey();
            url = host + "/accessToken/get";
            Map<String, Object> map = new HashMap<>();
            map.put("appId", accountInfo.appId());
            String sign = ApiSignature.signTopRequest(WX_TOKEN, map);
            map.put("sign", sign);
            HttpManager.postFormExecute(url, map, this);
        } else {
            HttpManager.getExecute(url, this);
        }
        return auth;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        auth = new WXAuth();
        auth.setErrcode(1000);
        auth.setErrmsg(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String json = response.body().string();
            Logger.info(json, getClass());
            auth = JSON.parseObject(json, WXAuth.class);
            if (auth != null) {
                if (!StringUtils.isBlank(auth.getAccess_token())) {
                    if (StringUtils.isBlank(auth.getTicket())) {
                        auth.setTicket(jsApiTicketLoad.jsApiTicketLoad(auth.getAccess_token()));
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
