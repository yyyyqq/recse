package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 扫码支付统一下单返回值
 */
@Setter
@Getter
public class NativeUnifiedOrderResultData extends UnifiedOrderResultData {

    private String sub_mch_id;
    private String code_url;

    public NativeUnifiedOrderResultData() {
    }

}
