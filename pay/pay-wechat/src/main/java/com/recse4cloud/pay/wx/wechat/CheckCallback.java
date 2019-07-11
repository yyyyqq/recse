package com.recse4cloud.pay.wx.wechat;

import com.recse4cloud.common.core.Logger;
import com.recse4cloud.common.core.RestfullData;
import com.recse4cloud.pay.wx.wechat.resp.BaseResultData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.recse4cloud.common.core.IConstants.SUCCESS_CODE;
import static com.recse4cloud.common.core.IConstants.SUCCESS_MSG;

/**
 * 对接口返回值进行校验
 */
public class CheckCallback<T extends BaseResultData> implements Callback {

    public RestfullData<T> data;
    public Checker<T> checker;

    public CheckCallback(Class<T> cls) {
        checker = new Checker<>(cls);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        data = new RestfullData<>();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String xml = response.body().string();
        onResponse(xml);
    }

    /**
     * @param xml
     * @throws IOException
     */
    public void onResponse(String xml) throws IOException {
        if (StringUtils.isBlank(xml)) {
            return;
        }
        data = new RestfullData<>();
        try {
            boolean bl = checker.check(xml);
            if (bl) {
                data.setData(checker.getData());
                data.setCode(SUCCESS_CODE);
                data.setMsg(SUCCESS_MSG);
            } else if (checker.data != null) {
                if (StringUtils.isNoneBlank(checker.getData().getErr_code()) || StringUtils.isNoneBlank(checker.getData().getErr_code_des())) {
                    data.setCode(checker.getData().getErr_code());
                    data.setMsg(checker.getData().getErr_code_des());
                } else {
                    data.setCode(checker.getData().getReturn_code());
                    data.setMsg(checker.getData().getReturn_msg());
                }
            }
        } catch (Exception e) {
            Logger.error(e, getClass());
        }
    }

}
