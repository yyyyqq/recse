package com.recse4cloud.pay.api.service;

/**
 * 支付平台账户查询器
 *
 */
public interface IAccountLoadService {
    /**
     * @param appId
     * @return
     */
    IAccountInfo load(String appId);
}
