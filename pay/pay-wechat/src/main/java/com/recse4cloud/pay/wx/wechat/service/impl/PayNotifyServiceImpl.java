package com.recse4cloud.pay.wx.wechat.service.impl;

import com.recse4cloud.common.core.Logger;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.pay.api.PayNotifyData;
import com.recse4cloud.pay.api.service.IAccountLoadService;
import com.recse4cloud.pay.api.service.INotifyCallback;
import com.recse4cloud.pay.wx.wechat.Checker;
import com.recse4cloud.pay.wx.wechat.resp.NotifyResultData;
import com.recse4cloud.pay.wx.wechat.service.IPayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;

@Service
public class PayNotifyServiceImpl implements IPayNotifyService {

    @Autowired(required = false)
    private INotifyCallback callBack;
    @Autowired
    private IAccountLoadService accountLoadService;

    /**
     * 支付结果通知
     *
     * @param xml
     * @return
     */
    @Override
    public boolean payNotify(String xml) {

        if (callBack == null || accountLoadService == null) {
            Logger.info("微信回调成功,但项目没有提供数据处理接口", getClass());
            return false;
        }

        try {
            Checker<NotifyResultData> checker = new Checker<>(NotifyResultData.class);
            checker.parser(xml);
            checker.setMchPublicKey(accountLoadService.load(checker.getData().getAppid()).publicKey());
            if (checker.checkReturnCode() && checker.checkSign() && checker.checkResultCode()) { //支付成功
                NotifyResultData data = checker.getData();//解析出推送的数据
                PayNotifyData pnd = new PayNotifyData();
                pnd.setPayUserNo(data.getOpenid());
                LocalDateTime dateTime = LocalDateTime.parse(data.getTime_end(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                pnd.setCheckDate(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                pnd.setTransactionId(data.getTransaction_id());
                pnd.setOutTradeNo(data.getOut_trade_no());
                pnd.setBankType(data.getBank_type());
                pnd.setAmount(String.valueOf(data.getTotal_fee()));
                pnd.setTradeType(data.getTrade_type());
                pnd.setState(1);
                Logger.info(pnd, getClass());
                RestfullData data1 = callBack.callback(pnd);
                return SUCCESS_CODE.equals(data1.getCode());
            }
        } catch (Exception e) {
            Logger.error(e, getClass());
        }
        return false;
    }
}
