package com.recse4cloud.pay.wx.wechat.resp;


import com.recse4cloud.pay.wx.wechat.BaseOrderData;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付推送结果信息
 */
@Setter
@Getter
public class BaseResultData extends BaseOrderData {

    private String return_code; //SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private String return_msg;  //返回信息，如非空，为错误原因

    private String result_code; //SUCCESS/FAIL
    private String err_code;  //错误返回的信息描述
    private String err_code_des; //错误返回的信息描述
    private String trade_type;

    public BaseResultData() {

    }

}
