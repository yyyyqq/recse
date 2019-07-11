package com.recse4cloud.pay.wx.wechat;


import java.util.List;

/**
 * 微信用户信息加载回调
 */
public interface OnWechatUnionIDCallBack {

    /**
     * 加载成功
     *
     * @param unionID
     */
    void onSuccess(WechatUnionID unionID);

    /**
     * 批量加载成功
     *
     * @param unionIDs
     */
    void onBatchSuccess(List<WechatUnionID> unionIDs);

    /**
     * @param e
     */
    void onFail(Exception e);
}
