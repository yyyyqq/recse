package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 企业付款响应消息: 零钱包
 *
 */
@Setter
@Getter
public class MktTransfersResultData extends BaseResultData {

    public MktTransfersResultData() {

    }

    private String mchid;

    /**
     * 商户订单号，需保持唯一性
     * (只能是字母或者数字，不能包含有符号)
     */
    private String partner_trade_no;

    /**
     * 企业付款成功，返回的微信订单号
     */
    private String payment_no;

    /**
     * 企业付款成功时间 : 2015-05-19 15:26:59
     */
    private String payment_time;

}
