package com.recse4cloud.pay.wx;

import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.wx.wechat.service.IPayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 微信支付成功回调
 *
 */
@RestController
public class PayNotifyController {

    @Autowired
    private IPayNotifyService notifyService;

    /**
     * 微信支付结果通知
     *
     * @param request
     * @return
     */
    @PostMapping("/pay/wxpayNotify")
    public void wxAppPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xml = parserRequest(request);
            boolean bl = notifyService.payNotify(xml);
            xml = "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
            if (!bl) {
                xml = "<xml>\n" +
                        "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                        "  <return_msg><![CDATA[处理失败]]></return_msg>\n" +
                        "</xml>";
            }
            try {
                response.addHeader("Content-Type", "application/xml;charset=UTF-8");
                response.getWriter().write(xml);
            } catch (IOException e) {
                Logger.error(e, getClass());
            }
        } catch (IOException e) {
            Logger.error(e, getClass());
        }
    }

    /**
     * 读取请求中的数据流
     *
     * @param request
     * @return
     * @throws IOException
     */
    public String parserRequest(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream in = request.getInputStream();
        byte[] temp = new byte[2048];
        int len;
        while ((len = in.read(temp)) > 0) {
            sb.append(new String(temp, 0, len, request.getCharacterEncoding()));
        }
        in.close();
        return sb.toString();
    }
}
