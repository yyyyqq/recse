package com.recse4cloud.pay.wx.wechat;


import com.recse4cloud.common.core.Logger;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Signature {

    /**
     * 计算签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String sign(Map<String, Object> params, String key) {
        params.remove("sign");
        StringBuffer sb = new StringBuffer();
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        String[] sort = list.toArray(new String[list.size()]);
        Arrays.sort(sort, String.CASE_INSENSITIVE_ORDER);
        for (String s : sort) {
            sb.append(s);
        }
        sb.append("key=" + key);
        String strSign = sb.toString();
        Logger.info("before sign : " + strSign, Signature.class);
        strSign = DigestUtils.md5Hex(strSign).toUpperCase();
        return strSign;
    }

}
