package com.recse4cloud.pay.ali.alipay.service.impl;

import com.recse4cloud.common.core.Logger;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.pay.ali.alipay.CheckSign;
import com.recse4cloud.pay.ali.alipay.service.IAliNotifyService;
import com.recse4cloud.pay.api.PayNotifyData;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.api.service.IAccountLoadService;
import com.recse4cloud.pay.api.service.INotifyCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;

@Service
public class AliNotifyServiceImpl implements IAliNotifyService {

    @Autowired
    private IAccountLoadService accountLoadService;
    @Autowired
    private INotifyCallback callback;

    /**
     * 支付推送数据处理
     *
     * @param params
     * @return
     */
    @Override
    public String onNotify(Map<String, String> params) {
        String appId = params.get("app_id");
        IAccountInfo info = accountLoadService.load(appId);
        boolean bl = CheckSign.check(params, info.platformPublicKey());
        Logger.info("CheckSign -> " + bl, getClass());
        if (!bl) {
            return "fail";
        }
//        CompletableFuture.runAsync(() -> parser(params));
        RestfullData data = parser(params);
        return SUCCESS_CODE.equals(data.getCode()) ? "success" : "fail";
    }

    /**
     * @param params
     */
    public RestfullData parser(Map<String, String> params) {
        switch (params.get("notify_type")) {
            case "fund_auth_freeze":
                return onFundAuthFreezeNotify(params);
            case "trade_status_sync":
                return onTradeAppPay(params);
        }
        return new RestfullData();
    }

    /**
     * 预付款冻结押金推送
     *
     * @param params
     */
    public RestfullData onFundAuthFreezeNotify(Map<String, String> params) {
        PayNotifyData data = new PayNotifyData();
        data.setAmount(params.get("amount"));
        data.setOutTradeNo(params.get("out_order_no"));
        data.setTransactionId(params.get("auth_no"));
        data.setCheckDate(params.get("gmt_trans"));
        data.setPayUserNo(params.get("payer_user_id"));
        data.setTradeType(PayNotifyData.TradeType.FUND_AUTH.getName());
        data.setState("SUCCESS".equalsIgnoreCase(params.get("status")) ? 0 : 1);
        data.setDesc(params.get("pre_auth_type"));
        return callback.callback(data);
    }

    /**
     * 阿里APP支付异步通知
     *
     * @param params
     * @return
     */
    public RestfullData onTradeAppPay(Map<String, String> params) {
        PayNotifyData data = new PayNotifyData();
        data.setAmount(params.get("amount"));
        data.setOutTradeNo(params.get("out_trade_no"));
        data.setTransactionId(params.get("trade_no"));
        data.setCheckDate(params.get("gmt_payment"));
        data.setPayUserNo(params.get("buyer_logon_id"));
        data.setTradeType(PayNotifyData.TradeType.ALI_APP.getName());
        data.setState("TRADE_SUCCESS".equalsIgnoreCase(params.get("trade_status")) ? 0 : 1);
        data.setDesc(params.get("fund_bill_list"));
        return callback.callback(data);
    }
}
