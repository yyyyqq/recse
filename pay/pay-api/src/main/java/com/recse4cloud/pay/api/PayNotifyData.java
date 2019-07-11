package com.recse4cloud.pay.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayNotifyData {
    private String payUserNo; //用户在商户appid下的唯一标识
    //    private String is_subscribe;//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
    private String tradeType; //交易类型 : JSAPI、NATIVE、APP
    private String bankType; //银行类型，采用字符串类型的银行标识，银行类型见: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
    private String amount; //订单总金额，单位为分
    //    private int cashFee; //现金支付金额订单现金支付金额，详见
    private String transactionId; //微信支付订单号
    private String outTradeNo; //商户系统的订单号，与请求一致。
    private String checkDate; //支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
    private int state; //结果：0失败 1 成功
    private String desc;


    /**
     * 支付交易类型
     */
    public enum TradeType {

        JSAPI("JSAPI"),//微信网页支付
        APP("APP"),//微信APP
        FUND_AUTH("fund_auth"),//支付宝预授权
        NATIVE("NATIVE"), //小程序
        ALI_APP("ALI_APP"); //阿里APP支付

        private String name;

        TradeType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
