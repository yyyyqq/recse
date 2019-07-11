package com.recse4cloud.pay.api.service;

import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.pay.api.PayNotifyData;

public interface INotifyCallback {

    /**
     * 推送回调
     *
     * @param data
     * @return
     */
    RestfullData callback(PayNotifyData data);

}
