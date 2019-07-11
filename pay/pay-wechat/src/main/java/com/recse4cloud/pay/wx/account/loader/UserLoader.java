package com.recse4cloud.pay.wx.account.loader;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.api.HttpManager;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXAuth;
import com.recse4cloud.pay.wx.account.WXUserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.IOException;

public class UserLoader implements IUnionIDLoader, Callback {
    @Autowired
    private IAccessTokenLoader accessTokenLoader;

    WXUserInfo wxUserInfo;

    /**
     * 获取关注的微信用户基本信息
     *
     * @param accountInfo 微信账户信息
     * @param openId      用户的openid
     * @return
     */
    @Override
    public WXUserInfo unionIDLoad(IAccountInfo accountInfo, String openId) {
        Assert.notNull(accountInfo, "缺少支付账户信息");
        WXAuth auth = accessTokenLoader.accessToken(accountInfo);
        if (auth == null) {
            return null;
        }
        String accessToken = auth.getAccess_token();
        Logger.info("accessToken - " + accessToken, getClass());
        HttpManager.getExecute(String.format(USER_INFO_URL, accessToken, openId), this);
        return wxUserInfo;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Logger.error(e, getClass());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        wxUserInfo = JSON.parseObject(json, WXUserInfo.class);
//        if (wxUserInfo.getSubscribe() == 1) {//关注的用户才拉取信息
//        }
        Logger.info("微信用户信息 - " + json, getClass());
    }
}
