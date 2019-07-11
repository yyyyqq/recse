package com.recse4cloud.pay.wx.wechat.annotation;

import com.recse4cloud.pay.wx.PayNotifyController;
import com.recse4cloud.pay.wx.wechat.UserInfoLoader;
import com.recse4cloud.pay.wx.wechat.WXPayBusiness;
import com.recse4cloud.pay.wx.wechat.service.impl.PayNotifyServiceImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        PayNotifyServiceImpl.class,
        PayNotifyController.class,
        UserInfoLoader.class,
        WXPayBusiness.class
})
public @interface EnableWechatPay {

}
