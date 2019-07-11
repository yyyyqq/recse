package com.recse4cloud.pay.ali.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultSignChecker;
import com.alipay.api.SignChecker;
import com.alipay.api.internal.util.AlipaySignature;

import java.util.Map;

import static com.recse4cloud.pay.ali.alipay.Constants.*;

public class CheckSign {

    /**
     * @param map             参数
     * @param alipayPublicKey
     * @return
     */
    public static boolean check(Map<String, String> map, String alipayPublicKey) {
        SignChecker checker = new DefaultSignChecker(alipayPublicKey);
        String sign = map.get("sign");
        return checker.check(AlipaySignature.getSignCheckContentV2(map), sign, map.get("sign_type"), map.get("charset"));
    }


    /**
     * @return
     */
    public static boolean check(Map<String, String> map) {
        return check(map, ALI_PUBLIC_KEY);
    }

    /**
     * @param result
     * @param publicKey
     * @param privateKey
     * @param alipayPublicKey
     * @return
     * @throws AlipayApiException
     */
    public static String checkRespone(boolean result, String publicKey, String privateKey, String alipayPublicKey) throws AlipayApiException {
        StringBuilder builder = new StringBuilder();
        builder.append("<biz_content>");
        builder.append(publicKey);
        builder.append("</biz_content>");
        builder.append("<success>");
        builder.append(result);
        builder.append("</success>");
        return AlipaySignature.encryptAndSign(builder.toString(), alipayPublicKey, privateKey, "GBK", false, true, "RSA2");
    }

    /**
     * 默认key,用于调试用
     *
     * @param result
     * @return
     * @throws AlipayApiException
     */
    public static String checkRespone(boolean result) throws AlipayApiException {
        return checkRespone(result, CUST_PUBLIC_KEY, CUST_PRIVATE_KEY, ALI_PUBLIC_KEY);
    }

}
