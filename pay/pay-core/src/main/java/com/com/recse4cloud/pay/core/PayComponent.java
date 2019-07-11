package com.com.recse4cloud.pay.core;

import com.alipay.api.AlipayApiException;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.pay.ali.alipay.AliPayAppBusiness;
import com.recse4cloud.pay.ali.alipay.AliPayFundAuthBusiness;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.IUrls;
import com.recse4cloud.pay.wx.wechat.Signature;
import com.recse4cloud.pay.wx.wechat.WXPayBusiness;
import com.recse4cloud.pay.wx.wechat.resp.UnifiedOrderResultData;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;

import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;

@Component
public class PayComponent {

    @Autowired(required = false)
    private WXPayBusiness wxPayBusiness;
    @Autowired(required = false)
    private AliPayFundAuthBusiness aliPayFundAuthBusiness;
    @Autowired(required = false)
    private AliPayAppBusiness aliPayAppBusiness;

    /**
     * 支付类型：微信支付
     */
    public static final int PAY_TPYE_WECHAT = 0;

    /**
     * 支付类型：阿里预授权支付
     */
    public static final int PAY_TPYE_ALIPAY = 1;

    /**
     * 支付类型：微信小程序
     */
    public static final int PAY_TPYE_WECHAT_SMALL_PROGRAM = 2;
    /**
     * 支付类型：阿里APP支付
     */
    public static final int PAY_TPYE_ALIPAY_APP = 3;

    final String TRADE_TYPE_NATIVE = "NATIVE";

    /**
     * @param info
     * @param money
     * @param outTradeNo
     * @param tradeType
     * @param title
     * @param payeeUserId
     * @param payType
     * @return
     */
    public RestfullData doPay(IAccountInfo info, String notifyUrl, double money, String outTradeNo, String tradeType, String title, String payeeUserId, int payType) {
        switch (payType) {
            case PAY_TPYE_WECHAT:
            case PAY_TPYE_WECHAT_SMALL_PROGRAM:
                Assert.notNull(wxPayBusiness, "暂不支持微信支付，请尝试其他支付方式");
                if (TRADE_TYPE_NATIVE.equals(tradeType)) {
                    return wxPayBusiness.unifiedOrderNative(info, notifyUrl, money, outTradeNo, title, null);
                }
                RestfullData<UnifiedOrderResultData> data = wxPayBusiness.unifiedOrder(info, notifyUrl, money, outTradeNo, tradeType, title, payeeUserId, null);

                Logger.info(data, getClass());
                Logger.info("payType = " + payType, getClass());

                if (SUCCESS_CODE.equals(data.getCode()) && PAY_TPYE_WECHAT_SMALL_PROGRAM == payType) {
                    return ServiceResultHelper.success(wechatSign(data.getData(), info));
                }
                return data;
            case PAY_TPYE_ALIPAY:
                Assert.notNull(aliPayFundAuthBusiness, "暂不支持支付宝支付，请尝试其他支付方式");
                try {
                    return aliPayFundAuthBusiness.fundAuthOrderAppFreeze(info, title, outTradeNo, payeeUserId, money, notifyUrl);
                } catch (AlipayApiException e) {
                    Logger.error(e, getClass());
                }
            case PAY_TPYE_ALIPAY_APP:
                Assert.notNull(aliPayAppBusiness, "暂不支持支付宝APP支付，请尝试其他支付方式");
                try {
                    return aliPayAppBusiness.tradeAppPay(info, title, "支付", outTradeNo, money, notifyUrl);
                } catch (AlipayApiException e) {
                    return new RestfullData<>("99998", "支付接口异常");
                }
        }
        return new RestfullData<>("99999", "暂不支持的支付方式");
    }

    /**
     * 提现退款
     *
     * @param accountInfo 支付账户信息
     * @param outTradeNo  支付订单号
     * @param outRefundNo 提现订单号
     * @param totalFee    订单总金额
     * @param refundFee   提现金额
     * @param payType     支付方式：0 微信 、1 支付宝
     * @return
     */
    public RestfullData doRefund(IAccountInfo accountInfo, String outTradeNo, String outRefundNo, double totalFee, double refundFee, int payType) {
        switch (payType) {
            case PAY_TPYE_WECHAT:
                return wxPayBusiness.refund(accountInfo, outTradeNo, outRefundNo, totalFee, refundFee);
            case PAY_TPYE_ALIPAY_APP:
                try {
                    return aliPayAppBusiness.tradeRefund(accountInfo, outTradeNo, outRefundNo, refundFee);
                } catch (AlipayApiException e) {
                    Logger.error(e, getClass());
                    return new RestfullData<>("99997", "退款交易异常");
                }
        }
        return new RestfullData<>("99999", "暂不支持的退款方式");
    }

    /**
     * @param accountInfo
     * @param outTradeNo
     * @param payType
     * @return
     */
    public RestfullData query(IAccountInfo accountInfo, String outTradeNo, int payType) {
        switch (payType) {
            case PAY_TPYE_WECHAT:
                return wxPayBusiness.orderQuery(accountInfo, null, outTradeNo);
            case PAY_TPYE_ALIPAY_APP:
                try {
                    return aliPayAppBusiness.tradeQuery(accountInfo, outTradeNo);
                } catch (AlipayApiException e) {
                    Logger.error(e, getClass());
                    return new RestfullData<>("99996", "查询订单发生异常");
                }
        }
        return new RestfullData<>("99999", "暂不支持的交易方式");
    }

    /**
     * 微信小程序需要进行组装支付参数
     *
     * @param data
     * @param info
     * @return
     */
    protected HashMap<String, Object> wechatSign(UnifiedOrderResultData data, IAccountInfo info) {
        HashMap<String, Object> params = new HashMap();
        params.put("appId", info.appId());
        params.put("timeStamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        params.put("nonceStr", RandomStringUtils.random(32, IUrls.chars));
        params.put("package", "prepay_id=" + data.getPrepay_id());
        params.put("signType", "MD5");
        params.put("paySign", Signature.sign(params, info.publicKey()));
        Logger.info(params, getClass());
        return params;
    }
}
