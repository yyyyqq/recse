package com.recse4cloud.qrcode.util;

import com.google.zxing.WriterException;
import com.recse4cloud.qrcode.config.QRTemplate;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public class QRCode extends DrawQRCode {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QRCode() {
    }

    public QRCode(DrawQRCode drawQRCode, QRTemplate template) {
        super(drawQRCode, template);
    }

    /**
     * 绘画二维码
     */
    @Override
    public BufferedImage draw() throws IOException {
        try {
            QRTemplate template = getTemplate();
            return QRCodeUtil.createBuffered(template.getContents().get(template.getContentIndex()), template);
        } catch (WriterException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
