package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotifyResultData extends BaseResultData {

    private String openid; //用户在商户appid下的唯一标识
    private String is_subscribe;//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
    private String trade_type; //交易类型 : JSAPI、NATIVE、APP
    private String bank_type; //银行类型，采用字符串类型的银行标识，银行类型见: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
    private int total_fee; //订单总金额，单位为分
    private int cash_fee; //现金支付金额订单现金支付金额，详见
    private String transaction_id; //微信支付订单号
    private String out_trade_no; //商户系统的订单号，与请求一致。
    private String time_end; //支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。

}
