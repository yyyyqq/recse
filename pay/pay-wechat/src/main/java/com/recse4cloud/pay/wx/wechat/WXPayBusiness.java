package com.recse4cloud.pay.wx.wechat;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.pay.api.BeanUtil;
import com.recse4cloud.pay.api.HttpManager;
import com.recse4cloud.pay.api.UnifyRefundResult;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.wx.wechat.req.*;
import com.recse4cloud.pay.wx.wechat.resp.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;
import static com.recse4cloud.pay.wx.wechat.IUrls.*;

@Component
public class WXPayBusiness {

    /**
     * 统一下单
     *
     * @param accountInfo 支付账号
     * @param totalFee    金额
     * @param outTradeNo  外部流水号
     * @param tradeType   交易类型 : APP , JSAPI
     * @param details     商品列表
     */
    public RestfullData<UnifiedOrderResultData> unifiedOrder(IAccountInfo accountInfo, String notifyUrl, double totalFee, String outTradeNo, String tradeType, String title, String openId, List<Detail> details) {
        UnifiedOrderData data = new UnifiedOrderData(accountInfo);
        data.setTotal_fee((int) (totalFee * 100));//单位分
        data.setOut_trade_no(outTradeNo);
        data.setTrade_type(tradeType);
        data.setBody(title);
        if (!StringUtils.isBlank(openId)) {
            data.setOpenid(openId);
        }
        if (details != null && details.size() > 0) { //商品列表不是必填项
            data.setDetail(JSON.toJSONString(details));
        }
        String wxPayNotifyUrl = notifyUrl;
        data.setNotify_url(wxPayNotifyUrl);
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        String xml = BeanUtil.bean2XML(data);
        CheckCallback<UnifiedOrderResultData> callback = new CheckCallback<>(UnifiedOrderResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(UNIFIED_ORDER_URL, xml, callback);
        RestfullData<UnifiedOrderResultData> restfullData = parserResponse(callback);
        if (restfullData.getData() != null) {
            UnifiedOrderResultData temp = new UnifiedOrderResultData();
            temp.setPrepay_id(restfullData.getData().getPrepay_id());
//            temp.setAppid(restfullData.getData().getAppid());
//            temp.setMch_id(restfullData.getData().getMch_id());
            temp.setNonce_str(null);
            restfullData.setData(temp);
        }
        return restfullData;
    }

    /**
     * 扫码支付统一下单
     *
     * @param accountInfo
     * @param notifyUrl
     * @param totalFee
     * @param outTradeNo
     * @param title
     * @param details
     * @return
     */
    public RestfullData unifiedOrderNative(IAccountInfo accountInfo, String notifyUrl, double totalFee, String outTradeNo, String title, List<Detail> details) {
        NativeUnifiedOrderData data = new NativeUnifiedOrderData(accountInfo);
        data.setBody(title);
        data.setNotify_url(notifyUrl);
        data.setOut_trade_no(outTradeNo);
        data.setTotal_fee((int) (totalFee * 100));
        if (details != null && details.size() > 0) { //商品列表不是必填项
            data.setDetail(JSON.toJSONString(details));
        }
        data.setProduct_id(String.valueOf(System.currentTimeMillis()));
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        String xml = BeanUtil.bean2XML(data);
        CheckCallback<NativeUnifiedOrderResultData> callback = new CheckCallback<>(NativeUnifiedOrderResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(UNIFIED_ORDER_URL, xml, callback);
        RestfullData<NativeUnifiedOrderResultData> restfullData = parserResponse(callback);
        if (restfullData.getData() != null) {
            NativeUnifiedOrderResultData temp = new NativeUnifiedOrderResultData();
            temp.setCode_url(restfullData.getData().getCode_url());
            temp.setNonce_str(null);
            restfullData.setData(temp);
        }
        return restfullData;
    }


    /**
     * 微信支付订单查询
     *
     * @param accountInfo
     * @param transactionId 微信支付流水号
     * @param outTradeId    本系统流水号
     * @return
     */
    public RestfullData orderQuery(IAccountInfo accountInfo, String transactionId, String outTradeId) {
        OrderQueryData data = new OrderQueryData(accountInfo);
        data.setTransaction_id(transactionId);
        data.setOut_trade_no(outTradeId);
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<OrderQueryResultData> callback = new CheckCallback<>(OrderQueryResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(ORDER_QUERY_URL, BeanUtil.bean2XML(data), callback);
        return parserResponse(callback);
    }

    /**
     * 订单关闭
     *
     * @param accountInfo
     * @param outTradeNo
     * @return
     */
    public RestfullData closeOrder(IAccountInfo accountInfo, String outTradeNo) {
        BaseOrderData data = new BaseOrderData(accountInfo);
        data.setOut_trade_no(outTradeNo);
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<BaseResultData> callback = new CheckCallback<>(BaseResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(CLOSE_ORDER_URL, BeanUtil.bean2XML(data), callback);
        return parserResponse(callback);
    }

    /**
     * 退款申请
     *
     * @param accountInfo
     * @param outTradeNo
     * @param outRefundNo
     * @param totalFee    充值订单金额
     * @param refundFee   提现金额
     * @return
     */
    public RestfullData refund(IAccountInfo accountInfo, String outTradeNo, String outRefundNo, double totalFee, double refundFee) {
        Logger.info(accountInfo, getClass());
        if (accountInfo == null) {
            return new RestfullData();
        }
        RefundOrderData data = new RefundOrderData(accountInfo);
        data.setOut_trade_no(outTradeNo);
        data.setOp_user_id(accountInfo.mchNo());
        data.setOut_refund_no(outRefundNo);
        data.setTotal_fee((int) (totalFee * 100));
        data.setRefund_fee((int) (refundFee * 100));
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<RefundResultData> callback = new CheckCallback<>(RefundResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        try {
            HttpManager.postSSL(REFUND_URL, BeanUtil.bean2XML(data), accountInfo.privateKey(), accountInfo.mchNo(), callback);
        } catch (Exception e) {
            Logger.error(e, getClass());
        }
        RestfullData<RefundResultData> restfullData = parserResponse(callback);
        UnifyRefundResult result = new UnifyRefundResult();
        if (SUCCESS_CODE.equals(restfullData.getCode())) {
            result.setResult(0);
            result.setOutRefundNo(restfullData.getData().getRefund_id());
            result.setRefundNo(restfullData.getData().getOut_trade_no());
        } else {
            result.setResult(1);
        }
        result.setRemark(restfullData.getMsg());
        return ServiceResultHelper.success(result);
    }


    /**
     * 退款查询
     *
     * @param accountInfo
     * @param outTradeNo
     * @return
     */
    public RestfullData refundQuery(IAccountInfo accountInfo, String outTradeNo, String outRefundNo) {
        RefundQueryData data = new RefundQueryData(accountInfo);
        data.setOut_refund_no(outRefundNo);

        Logger.info(data, getClass());

        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<RefundQueryResultData> callback = new CheckCallback<>(RefundQueryResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(REFUND_QUERY_URL, BeanUtil.bean2XML(data), callback);
        return parserResponse(callback);
    }

    /**
     * 下载对账单
     *
     * @param accountInfo
     * @param billDate
     * @return
     */
    public RestfullData downloadBill(IAccountInfo accountInfo, String billDate) {
        BillData data = new BillData(accountInfo);
        data.setBill_date(billDate);
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<BaseResultData> callback = new CheckCallback<>(BaseResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        HttpManager.postXmlExecute(DOWNLOAD_BILL_URL, BeanUtil.bean2XML(data), callback);
        return parserResponse(callback);
    }

    /**
     * 微信企业付款 : 微信零钱包
     *
     * @param accountInfo
     * @param partnerTradeNo
     * @param openId
     * @param reUserName     收款用户姓名
     * @param amount
     * @param desc
     * @return
     */
    public RestfullData promotionTransfers(IAccountInfo accountInfo, String partnerTradeNo, String openId, String reUserName, int amount, String desc) {
        MktTransfersData data = new MktTransfersData(accountInfo);
        data.setAmount(amount * 100); //单位转换为 : 分
        data.setDesc(desc);
        data.setOpenid(openId);
        data.setPartner_trade_no(partnerTradeNo);
//        data.setSpbill_create_ip(acountInfo.getIp());
        data.setRe_user_name(reUserName);
        String sign = Signature.sign(BeanUtil.bean2Map(data), accountInfo.publicKey());
        data.setSign(sign);
        CheckCallback<MktTransfersResultData> callback = new CheckCallback<>(MktTransfersResultData.class);
        callback.checker.setMchPublicKey(accountInfo.publicKey());
        try {
            HttpManager.postSSL(PROMOTION_TRANSFERS_URL, BeanUtil.bean2XML(data), accountInfo.privateKey(), accountInfo.mchNo(), callback);
//            callback.onResponse(respone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parserResponse(callback);
    }

    /**
     * 异步等待结果返回
     *
     * @param callback
     * @return
     */
    private RestfullData parserResponse(final CheckCallback<?> callback) {
        if (callback.data == null) {
            callback.data = new RestfullData<>();
        }
        return callback.data;
    }

}
