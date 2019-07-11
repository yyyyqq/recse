package com.recse4cloud.pay.api;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

public class ApiSignature {
    private static final Logger log = LoggerFactory
            .getLogger(ApiSignature.class);

    /**
     * 请求签名
     *
     * @param secret
     * @param params
     * @return
     */
    public static String signTopRequest(String secret, Map<String, String> params) {
        params.remove("sign");
        if (StringUtils.isBlank(secret)) {
            throw new NullPointerException("secret 不能为空");
        }

        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            Object value = params.get(key);
            query.append(key).append(value);
        }
        byte[] bytes;
        try {

            log.info("原始参数 - " + query);

            bytes = encryptMD5(query.toString());
        } catch (IOException e) {
            bytes = null;
            e.printStackTrace();
        }
        return byte2hex(bytes);
    }

    private static byte[] encryptMD5(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 计算签名
     *
     * @param secret
     * @param obj
     * @return
     */
    public static String signTopRequest(String secret, Object obj) {
        String s = JSON.toJSONString(obj);
        Map<String, String> map = JSON.parseObject(s, Map.class);
        return signTopRequest(secret, map);
    }
}
