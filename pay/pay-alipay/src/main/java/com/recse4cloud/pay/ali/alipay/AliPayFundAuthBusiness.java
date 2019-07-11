package com.recse4cloud.pay.ali.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.common.core.ServiceResultHelper;
import com.recse4cloud.pay.api.service.IAccountInfo;
import org.springframework.stereotype.Component;

import static com.recse4cloud.pay.ali.alipay.Constants.*;

/**
 * 预授权支付工具类
 */
@Component
public class AliPayFundAuthBusiness {
    /**
     * 创建支付宝授权订单并完成资金冻结。
     * 适用于线上场景完成资金授权,
     * 例如从商户APP端拉起支付宝收银台完成冻结。
     *
     * @param info        生活号信息
     * @param title       商品名称
     * @param orderNo     订单编号
     * @param payeeUserId 阿里系统商户ID
     * @param amount      金额单位：元、 2位小数
     * @param notifyUrl   授权回调地址
     * @throws AlipayApiException
     */
    public RestfullData fundAuthOrderAppFreeze(IAccountInfo info, String title, String orderNo, String payeeUserId, double amount, String notifyUrl) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
        AlipayFundAuthOrderAppFreezeRequest request = new AlipayFundAuthOrderAppFreezeRequest();
        AlipayFundAuthOrderAppFreezeModel model = new AlipayFundAuthOrderAppFreezeModel();
        model.setOrderTitle(title);
        model.setAmount(String.valueOf(amount));
        model.setOutOrderNo(orderNo);
        model.setOutRequestNo("freezeRequestNo" + orderNo);
        model.setPayeeUserId(payeeUserId);
        model.setProductCode(DEFAULT_PRODUCT_CODE);
        model.setExtraParam(DEFAULT_CATEGORY);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        AlipayFundAuthOrderAppFreezeResponse response = client.sdkExecute(request);
        return result(response);
//        if (response.isSuccess()) {
//            return new RestfullData(RestfullData.SUCCESS_CODE, RestfullData.SUCCESS_MSG, response.getBody());
//        }
//        throw new AlipayApiException(response.getBody());
    }

    /**
     * 用于用户手动进行提现的功能
     * <p>
     * 当资金授权发生之后一段时间内，
     * 由于买家或者商家等其他原因需要要解冻资金，
     * 商家可通过资金授权解冻接口将授权资金进行解冻，
     * 支付宝将在收到解冻请求并验证成功后，
     * 按解冻规则将冻结资金按原路进行解冻。
     *
     * @param info
     * @param authNo    授权单号
     * @param orderNo   解冻单号
     * @param amount    解冻金额
     * @param notifyUrl 解冻结果推送地址
     * @throws AlipayApiException
     */
    public RestfullData fundAuthOrderUnFreeze(IAccountInfo info, String authNo, String orderNo, double amount, String notifyUrl) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
        AlipayFundAuthOrderUnfreezeRequest request = new AlipayFundAuthOrderUnfreezeRequest();
        AlipayFundAuthOrderUnfreezeModel model = new AlipayFundAuthOrderUnfreezeModel();
        model.setAuthNo(authNo);
        model.setOutRequestNo("UnfreezeRequestNo" + orderNo);
        model.setAmount(String.valueOf(amount));
        //选填字段，信用授权订单，针对0元订单，传入该值完结信用订单，形成芝麻履约记录
        model.setExtraParam("{\"unfreezeBizInfo\":\"{\\\"bizComplete\\\":\\\"true\\\"}\"}");
        model.setRemark("预授权解冻"); // 商户对本次解冻操作的附言描述，长度不超过100个字母或50个汉字
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        AlipayFundAuthOrderUnfreezeResponse response = client.execute(request);
        return result(response);
//        if (response.isSuccess()) {
////            return response.getBody();
//            return new RestfullData(RestfullData.SUCCESS_CODE, RestfullData.SUCCESS_MSG, response.getBody());
//        }
//        throw new AlipayApiException(response.getBody());
    }

    /**
     * 扣除费用并把剩余金额返回给用户
     *
     * @param info
     * @param authNo        预授权单号
     * @param refundOrderNo 退款单号
     * @param payUserId     支付用户编号，授权推送
     * @param amount
     * @param notifyUrl
     * @throws AlipayApiException
     */
    public RestfullData tradePay(IAccountInfo info, String authNo, String refundOrderNo, String payUserId, double amount, String notifyUrl) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(refundOrderNo); // 预授权转支付商户订单号，为新的商户交易流水号
        model.setProductCode(DEFAULT_PRODUCT_CODE); // 固定值PRE_AUTH_ONLINE
        model.setAuthNo(authNo); // 填写预授权冻结交易号
        model.setSubject("支付租赁费用"); // 解冻转支付标题，用于展示在支付宝账单中
        model.setTotalAmount(String.valueOf(amount)); // 结算支付金额
        model.setSellerId(info.mchNo()); // 填写卖家支付宝账户pid
        model.setBuyerId(payUserId); // 填写预授权用户uid，通过预授权冻结接口返回的payer_user_id字段获取
        model.setBody("支付租赁费用"); // 可填写备注信息
        model.setAuthConfirmMode("COMPLETE");//必须使用COMPLETE,传入该值用户剩余金额会自动解冻
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);//异步通知地址，必填，该接口只通过该参数进行异步通知
        AlipayTradePayResponse response = client.execute(request);
        return result(response);
//        if (response.isSuccess()) {
////            return response.getBody();
//            return new RestfullData(RestfullData.SUCCESS_CODE, RestfullData.SUCCESS_MSG, response.getBody());
//        }
//        throw new AlipayApiException(response.getBody());
    }

    /**
     * 如果授权转支付扣下租赁费用，并退还用户押金剩余部分后。
     * 如果管理员后台修改租赁费用时，把退掉的钱一并返还用户。
     *
     * @param info
     * @param refundOrderNo
     * @param amount
     * @return
     * @throws AlipayApiException
     */
    public RestfullData tradeRefund(IAccountInfo info, String refundOrderNo, double amount) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundOrderNo); //与预授权转支付商户订单号相同，代表对该笔交易退款
        model.setRefundAmount(String.valueOf(amount));
        model.setRefundReason("修改租车费用");
        model.setOutRequestNo("tradeRefund" + refundOrderNo);//标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        AlipayTradeRefundResponse response = client.execute(request);
        return result(response);
//        if (response.isSuccess()) {
////            return response.getBody();
//            return new RestfullData(RestfullData.SUCCESS_CODE, RestfullData.SUCCESS_MSG, response.getBody());
//        }
//        throw new AlipayApiException(response.getBody());
    }

    /**
     * 预授权订单查询
     *
     * @param info
     * @param authOrderNo
     * @return
     * @throws AlipayApiException
     */
    public RestfullData fundAuthQuery(IAccountInfo info, String authOrderNo, String ourRequestNo) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(ALIPAY_GATEWAY, info.appId(), info.privateKey(), DEFAULT_FORMAT, DEFAULT_CHARSET, info.platformPublicKey(), DEFAULT_SIGN_TYPE);
        AlipayFundAuthOperationDetailQueryRequest request = new AlipayFundAuthOperationDetailQueryRequest();
        AlipayFundAuthOperationDetailQueryModel model = new AlipayFundAuthOperationDetailQueryModel();
        model.setAuthNo(authOrderNo); // 支付宝资金授权订单号，在授权冻结成功时返回参数中获得
        request.setBizModel(model);
        model.setOutRequestNo(ourRequestNo);
        AlipayFundAuthOperationDetailQueryResponse response = client.execute(request);
        return result(response);
//        if (response.isSuccess()) {
////            return response.getBody();
//            return new RestfullData(RestfullData.SUCCESS_CODE, RestfullData.SUCCESS_MSG, response.getBody());
//        }
//        throw new AlipayApiException(response.getBody());
    }

    /**
     * 解析返回值
     *
     * @param response
     * @return
     */
    private RestfullData result(AlipayResponse response) {
        if (response.isSuccess()) {
            return ServiceResultHelper.success(response.getBody());
        } else {
            return new RestfullData("99999", response.getBody());
        }
    }
}
