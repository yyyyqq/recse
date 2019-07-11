package com.recse4cloud.pay.wx.wechat.req;


import com.recse4cloud.pay.wx.wechat.IUrls;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 微信公众号的订单
 */
@Setter
@Getter
public class JSAPIOrderData {

    private String appId; //商户注册具有支付权限的公众号成功后即可获得
    private String timeStamp; //时间戳 : 标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。
    private String nonceStr; //随机字符串，不长于32位
    private String prepay_id; //统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
    private String signType = "MD5"; //签名算法，暂支持MD5
    private String paySign; //签名算法,https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3

    public JSAPIOrderData() {
        this.nonceStr = RandomStringUtils.random(32, IUrls.chars);
        this.timeStamp = String.valueOf(String.valueOf(System.currentTimeMillis() / 1000));
    }

}
