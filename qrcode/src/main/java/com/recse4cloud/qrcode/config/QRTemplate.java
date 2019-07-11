package com.recse4cloud.qrcode.config;

import com.recse4cloud.qrcode.util.DrawQRCode;
import com.recse4cloud.qrcode.util.QRCode;
import lombok.Data;

import java.util.List;

@Data
public class QRTemplate {
    /**
     * 二维码url
     */
    private String url;
    /**
     * 二维码内容
     */
    private List<String> contents;
    /**
     * 文件名
     */
    private List<String> fileNames;
    /**
     * 二维码内容序号
     */
    private int contentIndex;
    /**
     * 图片宽度
     */
    private Integer width = 500;
    /**
     * 高度
     */
    private Integer height = 500;
    /**
     * 文件存储地址
     */
    private String storagePath = System.getProperty("user.home") + "/resource/file/qrcode";
    /**
     * 临时存放目录
     */
    private String tempPath;
    /**
     * 后缀名
     */
    private String suffix = "PNG";

    public DrawQRCode draw(DrawQRCode drawQRCode) {
        return new QRCode(null, this);
    }

    public DrawQRCode draw() {
        return new QRCode(null, this);
    }
}