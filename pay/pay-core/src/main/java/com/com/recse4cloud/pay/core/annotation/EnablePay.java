package com.com.recse4cloud.pay.core.annotation;


import com.com.recse4cloud.pay.core.PayComponent;
import com.recse4cloud.pay.ali.alipay.annotation.EnableAlipay;
import com.recse4cloud.pay.wx.wechat.annotation.EnableWechatPay;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableAlipay
@EnableWechatPay
@Import({
        PayComponent.class
})
public @interface EnablePay {

}
