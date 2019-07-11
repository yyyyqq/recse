package com.recse4cloud.pay.ali.alipay.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 线上资金授权冻结，异步通知
 *
 */
@Setter
@Getter
public class FundAuthFreezeNotifyData {

    private String gmt_create;//操作创建时间
    private String charset;
    private String rest_credit_amount;
    private String operation_type;//资金操作类型，支持【FREEZE，UNFREEZE，PAY】
    private String sign;
    private String rest_fund_amount;
    private String auth_no; //支付宝资金授权订单号
    private String notify_id;
    private String total_freeze_credit_amount;
    private String notify_type;
    private String gmt_trans;//操作处理完成时间
    private String operation_id;//支付宝的资金操作流水号
    private String total_pay_fund_amount;
    private String sign_type;
    private String payer_user_id;//付款方支付宝账号UID
    private String out_request_no;
    private String app_id;
    private String amount;//本次操作冻结的金额，单位为：元（人民币），精确到小数点后两位
    private String rest_amount;//剩余冻结金额
    private String notify_time;
    private String fund_amount;
    private String total_pay_credit_amount;
    private String payee_user_id;//收款方支付宝账号UID
    private String credit_amount;
    private String pre_auth_type;//预授权类型，目前支持 CREDIT_AUTH(信用预授权); 商户可根据该标识来判断该笔预授权的类型，当返回值为"CREDIT_AUTH"表明该笔预授权为信用预授权，没有真实冻结资金；当返回值为空或者不为"CREDIT_AUTH"则表明该笔预授权为普通资金预授权，会冻结用户资金。
    private String out_order_no;//商户的资金授权订单号
    private String total_freeze_fund_amount;//累计冻结金额
    private String version;
    private String payee_logon_id;//收款方支付宝账号登陆号
    private String total_unfreeze_fund_amount;//累计解冻金额
    private String total_pay_amount;//累计支付金额
    private String total_freeze_amount;
    private String total_unfreeze_credit_amount;
    private String auth_app_id;
    private String total_unfreeze_amount;
    private String status;//资金预授权明细的状态 目前支持： INIT：初始 SUCCESS: 成功 CLOSED：关闭
    private String payer_logon_id;//付款方支付宝账号登录号

}
