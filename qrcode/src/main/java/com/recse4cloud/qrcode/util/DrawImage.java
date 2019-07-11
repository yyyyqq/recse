package com.recse4cloud.qrcode.util;

import com.recse4cloud.qrcode.config.QRImageTemplate;
import com.recse4cloud.qrcode.config.QRTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawImage extends DrawQRCode {
    public DrawImage() {
    }

    public DrawImage(DrawQRCode drawQRCode, QRTemplate template) {
        super(drawQRCode, template);
    }

    /**
     * 绘画二维码
     */
    @Override
    public BufferedImage draw() throws IOException {
        QRTemplate template = getTemplate();
        BufferedImage qrcodeBuffer = getDrawQRCode().draw();
        QRImageTemplate config = (QRImageTemplate<String>) template;
        Image scale = qrcodeBuffer.getScaledInstance(config.getWidth(), config.getHeight(), Image.SCALE_AREA_AVERAGING);
        BufferedImage tempBuffer = ImageIO.read(new File(config.getTemplatePath()));
        Graphics2D graphics = tempBuffer.createGraphics();
        int x = (tempBuffer.getWidth() - config.getWidth()) / config.getXScale() - config.getXOffset();
        int y = tempBuffer.getHeight() / config.getYScale() - config.getYOffset();
        //合并二维码
        graphics.drawImage(scale, x, y, null);
        //绘画msg
        drawMsg(graphics, x, y, config);
        graphics.dispose();
        qrcodeBuffer.flush();
        return tempBuffer;
    }

    protected void drawMsg(Graphics graphics, int x, int y, QRImageTemplate config) {
        if (config.getMsg() == null) {
            return;
        }
        String msg = config.getMsg() + "";
        graphics.setFont(config.getFont());
        graphics.setColor(config.getFontColor());
        //字体所占
        int textWidth = graphics.getFontMetrics().stringWidth(msg);
        int fontX = x + (config.getWidth() - textWidth) / 2;
        int fontY = config.getHeight() + y + config.getFontYOffset();
        graphics.drawString(msg, fontX, fontY);
    }
}
