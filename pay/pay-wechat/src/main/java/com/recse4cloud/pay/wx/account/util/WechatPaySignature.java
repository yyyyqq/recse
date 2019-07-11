package com.recse4cloud.pay.wx.account.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信订单签名算法
 */
public class WechatPaySignature {

    final static Logger log = LoggerFactory.getLogger(WechatPaySignature.class);

    public static String signature(Map<String, Object> params, String key) {

        ArrayList<String> list = new ArrayList<String>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }

        String[] temp = new String[list.size()];
        list.toArray(temp);
        Arrays.sort(temp, String.CASE_INSENSITIVE_ORDER);
        StringBuffer sb = new StringBuffer();

        for (String s : temp) {
            sb.append(s + "&");
        }

        sb.append("key=" + key);
        String sign = sb.toString();
        log.info("sign - " + sign);
        try {
            String result = sigatures(sign);
            log.info("sign -result- " + result);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看当前应用签名信息
     *
     * @param sign
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String sigatures(String sign)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return MTextUntils.Bytes2HexString(md.digest(sign.getBytes("UTF-8")))
                .toUpperCase();
    }

    /**
     * 微信支付订单签名
     * @param appId
     * @param timeStamp
     * @param noncestr
     * @param seqid
     * @param key
     * @return
     */
    public static String signature(String appId, String timeStamp, String noncestr, String seqid,String signType, String key) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        params.put("timeStamp", timeStamp);
        params.put("nonceStr", noncestr);
        params.put("package", "prepay_id=" + seqid);
        params.put("signType", signType);
        return signature(params,key);
    }

}
