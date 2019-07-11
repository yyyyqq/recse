package com.recse4cloud.pay.wx.account;

/**
 * 微信接口异常信息
 */
public class WXError {
    /**
     * errcode : 40029
     * errmsg : invalid code
     */
    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
