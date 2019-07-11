package com.recse4cloud.pay.wx.account;

import com.recse4cloud.pay.wx.wechat.IUrls;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class WxJsApiPayInfo {

    /**
     * appid
     */
    private String appId;
    /**
     * openid
     */
    private String openId;
    /**
     * 时间戳
     */
    private String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    /**
     * 随机串
     */
    private String nonceStr = RandomStringUtils.random(32, IUrls.chars);
    /**
     * 预支付订单号
     */
    private String package1;
    /**
     * 加密类型，默认MD5
     */
    private String signType = "MD5";
    /**
     * js签名
     */
    private String signature;
    /**
     * 支付签名
     */
    private String paySign;
    /**
     * js票据
     */
    private String jsapiTicket;
}
