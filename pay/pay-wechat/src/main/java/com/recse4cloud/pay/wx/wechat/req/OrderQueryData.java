package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.BaseOrderData;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询订单
 */
@Setter
@Getter
public class OrderQueryData extends BaseOrderData {
    private String transaction_id; //微信的订单号，优先使用

    public OrderQueryData() {
        super();
    }

    public OrderQueryData(IAccountInfo account) {
        super(account);
    }

}
