package com.recse4cloud.qrcode.util;

import com.recse4cloud.qrcode.config.QRTemplate;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public abstract class DrawQRCode {
    private DrawQRCode drawQRCode;

    private QRTemplate template;

    public DrawQRCode() {
    }

    public DrawQRCode(DrawQRCode drawQRCode, QRTemplate template) {
        this.drawQRCode = drawQRCode;
        this.template = template;
    }

    /**
     * 绘画二维码
     */
    public abstract BufferedImage draw() throws IOException;

}
