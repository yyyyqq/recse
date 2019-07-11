package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderQueryResultData extends BaseResultData {
    private String openid;
    /**
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销（刷卡支付）
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    private String trade_state;//交易状态
    private String bank_type;//银行类型，采用字符串类型的银行标识
    private int total_fee;//订单总金额，单位为分
    private int cash_fee;//现金支付金额订单现金支付金额
    private String transaction_id;//微信支付订单号
    private String out_trade_no;//商户系统的订单号，与请求一致。
    private String time_end;//订单支付时间，格式为yyyyMMddHHmmss
    private String trade_state_desc;//交易状态描述:对当前查询订单状态的描述和下一步操作的指引

}
