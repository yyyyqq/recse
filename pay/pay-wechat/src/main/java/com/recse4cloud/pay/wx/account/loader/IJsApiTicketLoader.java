package com.recse4cloud.pay.wx.account.loader;

public interface IJsApiTicketLoader {
    // 获取jsapi_ticket
    String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    /**
     * ticket有效时长， 单位秒
     */
    int JS_TICKECT_EXPIRE_TIME = 7200;

    /**
     * 获取js访问票据
     *
     * @param accessToken
     * @return
     */
    String jsApiTicketLoad(String accessToken);
}
