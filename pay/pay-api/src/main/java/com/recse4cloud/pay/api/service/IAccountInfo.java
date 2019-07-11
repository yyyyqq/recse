package com.recse4cloud.pay.api.service;

/**
 * 支付平台商户信息
 *
 */
public interface IAccountInfo {

    /**
     * 商户名
     *
     * @return
     */
    String name();

    /**
     * 微信商户
     *
     * @return
     */
    String mchNo();

    /**
     * 微信公众号：appId
     *
     * @return
     */
    String appId();

    /**
     * 微信支付：退款证书存储路径
     *
     * @return
     */
    String privateKey();

    /**
     * 微信支付：API 秘钥
     *
     * @return
     */
    String publicKey();

    /**
     *  微信公众号：APP secret
     *
     * @return
     */
    String platformPublicKey();

    /**
     * 类型：type=0，默认；type=1，第三方认证
     *
     * @return
     */
    int type();

}
