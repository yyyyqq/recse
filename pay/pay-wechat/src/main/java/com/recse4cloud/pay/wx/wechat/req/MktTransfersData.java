package com.recse4cloud.pay.wx.wechat.req;

import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.IUrls;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 微信企业付款请求数据: 零钱包
 *
 */
@Setter
@Getter
public class MktTransfersData {
    /**
     * 微信分配的账号ID（企业号corpid即为此appId）
     */
    private String mch_appid;
    /**
     * 微信支付分配的商户号
     */
    private String mchid;

    /**
     * 商户订单号，需保持唯一性
     * (只能是字母或者数字，不能包含有符号)
     */
    private String partner_trade_no;
    /**
     * 商户appid下，某用户的openid
     */
    private String openid;
    /**
     * NO_CHECK：不校验真实姓名
     * FORCE_CHECK：强校验真实姓名
     */
    private String check_name = "FORCE_CHECK";
    /**
     * 收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     */
    private String re_user_name;
    /**
     * 企业付款金额，单位为分
     */
    private int amount;
    /**
     * 企业付款操作说明信息
     */
    private String desc;
    /**
     * 调用接口的机器Ip地址
     */
    private String spbill_create_ip;

    /**
     * 随机字符串，不长于32位
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;

    public MktTransfersData() {
        this.nonce_str = RandomStringUtils.random(32, IUrls.chars);
    }

    public MktTransfersData(IAccountInfo account) {
        this();
        this.mch_appid = account.appId();
        this.mchid = account.mchNo();
    }

}
