package com.recse4cloud.pay.wx.wechat.resp;


import com.recse4cloud.pay.wx.wechat.WechatUnionID;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BatchUnionID {

    private List<WechatUnionID> user_info_list;

}
