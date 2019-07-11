package com.recse4cloud.pay.wx.account.loader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.api.HttpManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.util.Assert;

import java.io.IOException;

public class JsApiTicketLoader implements IJsApiTicketLoader, Callback {

    String ticket;

    /**
     * 获取js访问票据
     *
     * @param accessToken
     * @return
     */
    @Override
    public String jsApiTicketLoad(String accessToken) {
        Assert.notNull(accessToken, "缺少accessToken");
        String url = String.format(GET_JSAPI_TICKET_URL, accessToken);
        HttpManager.getExecute(url, this);
        return ticket;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Logger.error(e, getClass());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Logger.info(json, getClass());
        JSONObject obj = JSON.parseObject(json);
        int errcode = obj.getInteger("errcode");
        if (errcode == 0) {
            ticket = obj.getString("ticket");
        }
    }
}
