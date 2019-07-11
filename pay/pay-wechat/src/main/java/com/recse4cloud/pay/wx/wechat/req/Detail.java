package com.recse4cloud.pay.wx.wechat.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情
 */
@Setter
@Getter
public class Detail {

    private String goods_id;//商品的编号
    private String goods_name;//商品名称
    private int quantity;//商品数量
    private int price; //商品单价，单位为分

}
