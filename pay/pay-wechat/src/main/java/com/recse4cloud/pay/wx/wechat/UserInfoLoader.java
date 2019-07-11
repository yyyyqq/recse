package com.recse4cloud.pay.wx.wechat;

import com.alibaba.fastjson.JSON;
import com.recse4cloud.pay.api.HttpManager;
import com.recse4cloud.pay.wx.wechat.resp.BatchUnionID;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信用户信息加载器
 *
 */
@Component
public class UserInfoLoader {

    /**
     * 获取微信用户UnionID
     *
     * @param accessionToke
     * @param openId
     * @return
     */
    public void getWechatUnionID(String accessionToke, String openId, OnWechatUnionIDCallBack callback) {
        HttpManager.get(String.format(IUrls.UNION_ID_URL, accessionToke, openId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFail(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                WechatUnionID unionID = JSON.parseObject(json, WechatUnionID.class);
                if (callback != null) {
                    callback.onSuccess(unionID);
                }
            }
        });
    }

    /**
     * 批量获取
     *
     * @param accessionToke
     * @param list          WechatUnionID - open id
     * @param callback
     */
    public void batchGetWechatUnionID(String accessionToke, List<WechatUnionID> list, OnWechatUnionIDCallBack callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_list", list);
        HttpManager.post(String.format(IUrls.BATCH_UNION_ID_URL, accessionToke), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFail(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (callback != null) {
                    BatchUnionID unionIDs = JSON.parseObject(json, BatchUnionID.class);
                    final List<WechatUnionID> tempList = unionIDs.getUser_info_list();
                    List<WechatUnionID> userList = tempList.stream()
                            .filter(u -> u.getSubscribe() == 1)
                            .collect(Collectors.toList());
                    callback.onBatchSuccess(userList);
                }
            }
        });
    }
}
