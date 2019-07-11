package com.recse4cloud.pay.wx.account;

import com.recse4cloud.pay.wx.account.loader.AccessTokenLoader;
import com.recse4cloud.pay.wx.account.loader.JsApiTicketLoader;
import com.recse4cloud.pay.wx.account.loader.OpenIdLoader;
import com.recse4cloud.pay.wx.account.loader.UserLoader;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({JsApiTicketLoader.class, AccessTokenLoader.class, OpenIdLoader.class, UserLoader.class})
public @interface EnableWXAuthorize {
}
