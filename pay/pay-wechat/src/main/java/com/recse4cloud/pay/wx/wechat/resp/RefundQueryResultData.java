package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundQueryResultData extends BaseResultData {

    private String transaction_id;
    private String refund_status_0;
    private String out_refund_no_0;
    private String refund_id_0;
    private String cash_fee;
    private String refund_fee_0;
    private String total_fee;
    private String refund_recv_accout_0;
    private String refund_count;
    private String refund_fee;
    private String refund_channel_0;
}
