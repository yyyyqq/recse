package com.recse4cloud.pay.api;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一提现结果
 *
 */
@Setter
@Getter
public class UnifyRefundResult {
    //提现结果：0 成功 1 失败
    private int result;
    //系统提现单号
    private String refundNo;
    //支付平台提现单号
    private String outRefundNo;
    //备注
    private String remark;
}
