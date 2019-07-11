package com.recse4cloud.qrcode.config;

import com.recse4cloud.qrcode.util.DrawLogo;
import com.recse4cloud.qrcode.util.DrawQRCode;
import lombok.Data;

@Data
public class QRLogoTemplate extends QRTemplate {
    /**
     * Logo文件存储路径
     */
    private String logoPath;

    @Override
    public DrawQRCode draw(DrawQRCode drawQRCode) {
        return new DrawLogo(drawQRCode, this);
    }
}
