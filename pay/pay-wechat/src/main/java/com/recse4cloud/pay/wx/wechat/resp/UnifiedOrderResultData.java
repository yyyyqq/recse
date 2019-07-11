package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一下单的回复
 */
@Setter
@Getter
public class UnifiedOrderResultData extends BaseResultData {

    private String prepay_id;

    public UnifiedOrderResultData() {
    }

}
