package com.recse4cloud.pay.ali.alipay;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.pay.api.service.IAccountInfo;

import static com.recse4cloud.common.core.IConstants.FAIL_CODE;
import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;
import static com.recse4cloud.pay.ali.alipay.Constants.*;

/**
 * APP支付工具类
 */
public class AliPayAppBusiness {

    /**
     * 创建支付宝授权订单并完成资金冻结。
     * 适用于线上场景完成资金授权,
     * 例如从商户APP端拉起支付宝收银台完成冻结。
     *
     * @param info      生活号信息
     * @param title     商品名称
     * @param orderNo   订单编号
     * @param subject
     * @param amount    金额单位：元、 2位小数
     * @param notifyUrl 授权回调地址
     * @throws AlipayApiException
     */
    public RestfullData tradeAppPay(IAccountInfo info, String title, String subject, String orderNo, double amount, String notifyUrl) throws AlipayApiException {
        AlipayClient client = getClient(info);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(title);
        model.setSubject(subject);
        model.setTotalAmount(String.valueOf(amount));
        model.setOutTradeNo(orderNo);
        model.setTimeoutExpress("30m");
//        model.setProductCode(APP_TRADE_PRODUCT_CODE);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        AlipayTradeAppPayResponse response = client.sdkExecute(request);
        return result(response);
    }

    /**
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     *
     * @param info
     * @param outTradeNo  订单流水号
     * @param outRefundNo 退款流水号
     * @param refundFee   提现金额
     * @return
     */
    public RestfullData tradeRefund(IAccountInfo info, String outTradeNo, String outRefundNo, double refundFee) throws AlipayApiException {
        AlipayClient client = getClient(info);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setRefundAmount(String.valueOf(refundFee));
        model.setOutRequestNo(outRefundNo);
        request.setBizModel(model);
        AlipayTradeRefundResponse response = client.execute(request);
        return result(response);
    }

    /**
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     *
     * @param info
     * @param outTradeNo
     * @return
     * @throws AlipayApiException
     */
    public RestfullData tradeClose(IAccountInfo info, String outTradeNo) throws AlipayApiException {
        AlipayClient client = getClient(info);
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        return result(client.sdkExecute(request));
    }

    /**
     * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况： 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * 调用支付接口后，返回系统错误或未知交易状态情况； 调用alipay.trade.pay，返回INPROCESS的状态；
     * 调用alipay.trade.cancel之前，需确认支付状态；
     *
     * @param info
     * @param outTradeNo
     * @return
     * @throws AlipayApiException
     */
    public RestfullData tradeQuery(IAccountInfo info, String outTradeNo) throws AlipayApiException {
        AlipayClient client = getClient(info);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        return result(client.sdkExecute(request));
    }

    /**
     * 创建阿里支付客户端
     *
     * @param info
     * @return
     */
    private AlipayClient getClient(IAccountInfo info) {
        return new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
    }

    /**
     * @param client
     * @param request
     * @return
     * @throws AlipayApiException
     */
    private RestfullData result(AlipayClient client, AlipayRequest request) throws AlipayApiException {
        return result(client.sdkExecute(request));
    }

    /**
     * 解析返回值
     *
     * @param response
     * @return
     */
    private RestfullData result(AlipayResponse response) {
        if (response.isSuccess()) {
            return ServiceResultHelper.result(SUCCESS_CODE, response.getBody());
        } else {
            return ServiceResultHelper.result(FAIL_CODE, response.getBody());
        }
    }

}
