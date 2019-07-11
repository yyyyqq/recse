package com.recse4cloud.pay.wx.account.loader;

import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.account.WXUserInfo;

public interface IUnionIDLoader {

    /**
     * 通过OpenID来获取用户基本信息
     */
    String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 获取关注的微信用户基本信息
     *
     * @param accountInfo 微信账户信息
     * @param openId      用户的openid
     * @return
     */
    WXUserInfo unionIDLoad(IAccountInfo accountInfo, String openId);

}
