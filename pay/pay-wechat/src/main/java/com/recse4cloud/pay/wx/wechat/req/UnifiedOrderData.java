package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.BaseOrderData;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一下单
 */
@Setter
@Getter
public class UnifiedOrderData extends BaseOrderData {

    private String body; //商品简单描述，该字段须严格按照规范传递，具体请见:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
    private String detail; //商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来
    private int total_fee;
    private String spbill_create_ip;//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
    private String notify_url;
    private String trade_type; //交易类型,取值如下：JSAPI，NATIVE，APP
    private String openid;

    public UnifiedOrderData() {
        super();
    }

    public UnifiedOrderData(IAccountInfo account) {
        super(account);
        this.spbill_create_ip = "115.236.22.224";
    }

}
