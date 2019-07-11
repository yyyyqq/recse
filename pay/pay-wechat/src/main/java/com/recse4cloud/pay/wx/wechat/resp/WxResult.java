package com.recse4cloud.pay.wx.wechat.resp;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@XmlRootElement(name = "xml")
public class WxResult {

    private String return_code;
    private String return_msg;

    public WxResult() {
        this.return_code = "SUCCESS";
        this.return_msg = "OK";
    }
}
