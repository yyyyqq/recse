package com.recse4cloud.qrcode.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;

import java.awt.*;
import java.io.File;
import java.util.Hashtable;

/**
 * 二维码生产的配置参数
 */
@Data
public class QRConfig {

    private int width; //二维码宽度
    private int height; //二维码高度
    private int margin = 1; //白边大小，取值范围0~4
    private ErrorCorrectionLevel level; //二维码容错率
    private int background; //背景颜色
    private int foreground; //前景颜色,二维码的颜色
    private String storagePath; //生成的二维码保存路径
    private String format; //保持后缀
    private String charset; //字符集
    private Hashtable<EncodeHintType, Object> hints;
    private BarcodeFormat type;

    public QRConfig(String storagePath) {
        this.storagePath = storagePath;
        File root = new File(storagePath);
        if (!root.exists()) {
            root.mkdirs();
        }
        this.width = 500;
        this.height = 500;
        this.margin = 0;
        this.level = ErrorCorrectionLevel.H;
        this.background = Color.WHITE.getRGB();
        this.foreground = Color.BLACK.getRGB();
        this.format = "jpg";
        this.charset = "UTF-8";
        this.hints = initHints();
        this.type = BarcodeFormat.QR_CODE;
    }

    public Hashtable<EncodeHintType, Object> initHints() {
        Hashtable<EncodeHintType, Object> localHints = new Hashtable<EncodeHintType, Object>();
        localHints.put(EncodeHintType.CHARACTER_SET, charset);
        localHints.put(EncodeHintType.MARGIN, margin);
        localHints.put(EncodeHintType.ERROR_CORRECTION, level);
        return localHints;
    }

    public QRConfig() {
        this(System.getProperty("user.dir"));
    }
}
