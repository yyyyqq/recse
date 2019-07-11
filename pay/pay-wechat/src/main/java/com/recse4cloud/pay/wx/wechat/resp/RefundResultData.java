package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款申请
 */
@Setter
@Getter
public class RefundResultData extends BaseResultData {

    private String transaction_id;//微信订单号
    private String out_trade_no;//商户系统内部的订单号
    private String out_refund_no;//商户退款单号
    private String refund_id;//微信退款单号
    private int refund_fee;//退款总金额,单位为分,可以做部分退款
    private int total_fee;//订单总金额，单位为分，只能为整数
    private int cash_fee;//现金支付金额，单位为分，只能为整数

}
