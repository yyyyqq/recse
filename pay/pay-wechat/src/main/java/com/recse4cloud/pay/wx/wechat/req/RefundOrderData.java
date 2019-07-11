package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.BaseOrderData;
import lombok.Getter;
import lombok.Setter;

/**
 * 退款订单
 */
@Setter
@Getter
public class RefundOrderData extends BaseOrderData {

    private String transaction_id; //微信的订单号，优先使用
    private String out_refund_no; //商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
    private int total_fee;      //订单总金额，单位为分，只能为整数
    private int refund_fee;  //退款总金额，订单总金额，单位为分，只能为整数
    private String op_user_id; //操作员帐号, 默认为商户号

    public RefundOrderData(IAccountInfo account) {
        super(account);
    }

}
