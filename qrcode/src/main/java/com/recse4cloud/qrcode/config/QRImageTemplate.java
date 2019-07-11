package com.recse4cloud.qrcode.config;

import com.recse4cloud.qrcode.util.DrawImage;
import com.recse4cloud.qrcode.util.DrawQRCode;
import lombok.Data;

import java.awt.*;

@Data
public class QRImageTemplate<T> extends QRTemplate {
    /**
     * X轴，偏移量
     */
    private Integer xOffset = 0;
    /**
     * X轴，偏移量
     */
    private Integer yOffset = 0;
    /**
     * Y比例尺
     */
    private Integer xScale = 2;
    /**
     * X比例尺
     */
    private Integer yScale = 2;
    /**
     * 文字Y轴偏移量
     */
    private Integer fontYOffset = 0;
    /**
     * 文字X轴偏移量
     */
    private Integer fontXOffset = 0;
    /**
     * 字体名称
     */
    private String fontName = "宋体";
    /**
     * 字体大小
     */
    private Integer fontSize = 20;
    /**
     * 三原色，默认黑色
     */
    private Integer red = 255;
    private Integer green = 255;
    private Integer blue = 255;
    /**
     * 透明度，默认不透明
     */
    private Integer alpha = 255;
    /**
     * 字体
     */
    private Font font;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 模版文件存储地址
     */
    private String templatePath;
    /**
     * 模板上的文字
     */
    private T msg;

    public Font getFont() {
        if (this.font == null)
            this.font = new Font(getFontName(), Font.BOLD, getFontSize());
        return font;
    }

    public Color getFontColor() {
        if (fontColor == null)
            this.fontColor = new Color(getRed(), getGreen(), getBlue(), getAlpha());
        return fontColor;
    }

    @Override
    public DrawQRCode draw(DrawQRCode drawQRCode) {
        return new DrawImage(drawQRCode, this);
    }
}
