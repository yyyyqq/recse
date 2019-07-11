package com.recse4cloud.pay.wx.wechat.req;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Goods {

    private List<Detail> goods_detail;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
