package com.recse4cloud.qrcode.util;

import com.recse4cloud.qrcode.config.QRLogoTemplate;
import com.recse4cloud.qrcode.config.QRTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawLogo extends DrawQRCode {

    public DrawLogo() {
    }

    public DrawLogo(DrawQRCode drawQRCode, QRTemplate template) {
        super(drawQRCode, template);
    }

    /**
     * 绘画二维码
     */
    @Override
    public BufferedImage draw() throws IOException {
        QRTemplate template = getTemplate();
        BufferedImage buffer = getDrawQRCode().draw();
        QRLogoTemplate config = (QRLogoTemplate) template;
        Graphics2D graphics = buffer.createGraphics();
        BufferedImage logoBuffer = ImageIO.read(new File(config.getLogoPath()));
        int x = config.getWidth();
        int y = config.getHeight();
        graphics.drawImage(logoBuffer, x / 5 * 2, y / 5 * 2, x / 5, y / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        graphics.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(x / 5 * 2, y / 5 * 2, x / 5, y / 5, 20, 20);
        graphics.setColor(Color.white);
        graphics.draw(round);// 绘制圆弧矩形
        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        graphics.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(x / 5 * 2 + 2, y / 5 * 2 + 2, x / 5 - 4, y / 5 - 4, 20, 20);
        graphics.draw(round2);// 绘制圆弧矩形
        graphics.dispose();
        buffer.flush();
        return buffer;
    }
}
