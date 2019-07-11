package com.recse4cloud.pay.wx.wechat;


import com.recse4cloud.pay.api.service.IAccountInfo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Setter
@Getter
public class BaseOrderData {
    protected String appid; //微信分配的公众账号ID（企业号corpid即为此appId）
    protected String mch_id; //微信支付分配的商户号
    protected String nonce_str; //随机字符串，不长于32位
    protected String out_trade_no; //商户系统内部的订单号，当没提供transaction_id时需要传这个。
    protected String sign; //签名

    public BaseOrderData() {
        this.nonce_str = RandomStringUtils.random(32, IUrls.chars);
    }

    public BaseOrderData(IAccountInfo account) {
        this();
        this.appid = account.appId();
        this.mch_id = account.mchNo();
    }

}
