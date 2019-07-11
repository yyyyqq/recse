package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.api.service.IAccountInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询退款
 */
@Setter
@Getter
public class RefundQueryData extends RefundOrderData {

    private String refund_id; //微信生成的退款单号，在申请退款接口有返回


    public RefundQueryData(IAccountInfo account) {
        super(account);
    }

}
