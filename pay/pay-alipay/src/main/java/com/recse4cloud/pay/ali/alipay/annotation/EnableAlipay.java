package com.recse4cloud.pay.ali.alipay.annotation;

import com.recse4cloud.pay.ali.AliPayController;
import com.recse4cloud.pay.ali.alipay.AliPayAppBusiness;
import com.recse4cloud.pay.ali.alipay.AliPayFundAuthBusiness;
import com.recse4cloud.pay.ali.alipay.service.impl.AliNotifyServiceImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        AliPayController.class,
        AliNotifyServiceImpl.class,
        AliPayFundAuthBusiness.class,
        AliPayAppBusiness.class
})
public @interface EnableAlipay {
}
