package com.recse4cloud.pay.wx.wechat.service;


public interface IPayNotifyService {
    /**
     * 支付结果通知
     *
     * @param xml
     * @return
     */
    boolean payNotify(String xml);

}
