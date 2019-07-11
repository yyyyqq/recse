package com.recse4cloud.pay.ali.alipay.service;

import java.util.Map;

public interface IAliNotifyService {

    /**
     * 支付推送数据处理
     *
     * @param params
     * @return
     */
    String onNotify(Map<String, String> params);
}
