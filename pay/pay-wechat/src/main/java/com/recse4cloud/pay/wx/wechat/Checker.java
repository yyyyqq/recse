package com.recse4cloud.pay.wx.wechat;


import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.api.BeanUtil;
import com.recse4cloud.pay.wx.wechat.resp.BaseResultData;

import java.util.Map;

public class Checker<T extends BaseResultData> {

    Map<String, Object> map;
    T data;
    private final Class<T> cls;

    private String mchPublicKey;

//    private IWechatAccountLoader accountLoader;

    public Checker(Class<T> cls) {
        this.cls = cls;
    }

    /**
     * 解析微信返回的xml格式数据,并封装到一个map对象
     *
     * @param xml
     */
    public void parser(String xml) {
        Logger.info(xml, getClass());
        map = BeanUtil.xml2Map(xml);
        Logger.info(map, getClass());
        data = (T) BeanUtil.map2Bean(map, cls);
        Logger.info(data, getClass());
    }

    /**
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     *
     * @return
     */
    public boolean checkReturnCode() {
        boolean bl = data != null && "SUCCESS".equals(data.getReturn_code());
        Logger.info("checkReturnCode - " + bl, getClass());
        return bl;
    }

    /**
     * 业务结果,SUCCESS/FAIL
     *
     * @return
     */
    public boolean checkResultCode() {
        boolean bl = data != null && "SUCCESS".equals(data.getResult_code());
        Logger.info("chechResultCode - " + bl, getClass());
        return bl;
    }

    /**
     * 签名:微信返回的签名
     *
     * @return
     */
    public boolean checkSign() {
        String tempSign = data.getSign();
        String sign = Signature.sign(map, mchPublicKey);
        Logger.info("系统计算的sign : " + sign, getClass());
        Logger.info("微信返回的sign : " + tempSign, getClass());
        boolean bl = sign.equals(tempSign);
        Logger.info("checkSign - " + bl, getClass());
        return bl;
    }

    /**
     * 校验
     *
     * @return
     */
    public boolean check(String xml) throws Exception {
        parser(xml);
        return checkReturnCode() && checkSign() && checkResultCode();
    }

    public T getData() {
        return data;
    }

    public void setMchPublicKey(String mchPublicKey) {
        this.mchPublicKey = mchPublicKey;
    }

}
