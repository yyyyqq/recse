package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.BaseOrderData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillData extends BaseOrderData {
    private String bill_date; //下载对账单的日期，格式：20140603
    private String bill_type;

    public BillData(IAccountInfo account) {
        super(account);
        this.bill_type = "ALL";
    }

}
