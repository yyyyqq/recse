package com.recse4cloud.pay.ali;

import com.alipay.api.AlipayApiException;
import com.recse4cloud.common.core.Logger;
import com.recse4cloud.pay.ali.alipay.CheckSign;
import com.recse4cloud.pay.ali.alipay.service.IAliNotifyService;
import com.recse4cloud.pay.api.BeanUtil;
import com.recse4cloud.pay.api.service.IAccountInfo;
import com.recse4cloud.pay.api.service.IAccountLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付接口
 */
@RequestMapping("/ali")
@Controller
public class AliPayController {

    @Autowired
    private IAccountLoadService service;
    @Autowired
    private IAliNotifyService notifyService;

    @RequestMapping("/gateway")
    public void gateway(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = parseRequest(request);
        Logger.info(params, getClass());
        PrintWriter writer = null;
        try {
            String appId = (String) BeanUtil.xml2Map(params.get("biz_content")).get("AppId");
            IAccountInfo info = service.load(appId);
            String content = CheckSign.checkRespone(CheckSign.check(params, info.platformPublicKey()), info.publicKey(), info.privateKey(), info.platformPublicKey());
            writer = response.getWriter();
            writer.write(content);
            writer.flush();
        } catch (AlipayApiException e) {
            Logger.error(e, getClass());
        } catch (IOException e) {
            Logger.error(e, getClass());
        } catch (NullPointerException e) {
            Logger.error(e, getClass());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @RequestMapping("/payNotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = parseRequest(request);
        try {
            response.getWriter().write(notifyService.onNotify(params));
        } catch (IOException e) {
            Logger.error(e, getClass());
        }
    }

    /**
     * 提取请求参数
     *
     * @param request
     * @return
     */
    protected Map<String, String> parseRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        String key;
        String[] values;
        Map<String, String[]> temps = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : temps.entrySet()) {
            StringBuffer sb = new StringBuffer();
            key = entry.getKey();
            values = entry.getValue();
            for (String v : values) {
                sb.append(v + ",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            params.put(key, sb.toString());
        }
        return params;
    }

}
