package com.recse4cloud.pay.wx.wechat.req;

import com.recse4cloud.pay.api.service.IAccountInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 扫码支付请求参数
 */
@Setter
@Getter
public class NativeUnifiedOrderData extends UnifiedOrderData {

    //扫码支付支付类型固定为 NATIVE
    private static final String TRADE_TYPE = "NATIVE";

    public NativeUnifiedOrderData() {
        super();
        setTrade_type(TRADE_TYPE);
    }

    public NativeUnifiedOrderData(IAccountInfo account) {
        super(account);
        setTrade_type(TRADE_TYPE);
//        this.sub_mch_id = account.getSubMchId();
    }

    //微信支付分配的子商户号
    private String sub_mch_id;
    //trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
    private String product_id;

//    @Override
//    public void setTrade_type(String trade_type) {
//        super.setTrade_type(TRADE_TYPE);
//    }
}
