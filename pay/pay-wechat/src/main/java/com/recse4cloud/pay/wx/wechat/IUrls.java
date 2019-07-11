package com.recse4cloud.pay.wx.wechat;

public interface IUrls {
    //统一下单 N
    String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //查询订单 N
    String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //申请退款 Y
    String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //查询退款 N
    String REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    //订单关闭 N
    String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //下载对账单 N
    String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //企业付款: 微信零钱包
    String PROMOTION_TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //UnionID 获取
    String UNION_ID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    //批量获取union id
    String BATCH_UNION_ID_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=%s";

    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
}
