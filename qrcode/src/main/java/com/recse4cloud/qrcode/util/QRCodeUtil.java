package com.recse4cloud.qrcode.util;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.recse4cloud.qrcode.config.QRConfig;
import com.recse4cloud.qrcode.config.QRImageTemplate;
import com.recse4cloud.qrcode.config.QRLogoTemplate;
import com.recse4cloud.qrcode.config.QRTemplate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 二维码生产工具
 */
public class QRCodeUtil {

    private static QRConfig config = new QRConfig();

    /**
     * 创建二维码
     *
     * @param text
     * @param storageName
     * @throws WriterException
     * @throws IOException
     */
    public static void create(String text, String storageName) throws WriterException, IOException {
        File storage = new File(config.getStoragePath(), storageName + "." + config.getFormat());
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(config.getForeground(), config.getBackground());
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, config.getType(), config.getWidth(), config.getHeight(), config.getHints());
        MatrixToImageWriter.writeToStream(bitMatrix, config.getFormat(), new FileOutputStream(storage), matrixToImageConfig);
    }

    /**
     * 生成二维码的内存信息
     *
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createBuffered(String text) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, config.getType(), config.getWidth(), config.getHeight(), config.getHints());
        return configBitMatrix(bitMatrix);
    }

    /**
     * 生成二维码的内存信息
     *
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createBuffered(String text, QRTemplate templateConfig) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, config.getType(), templateConfig.getWidth(), templateConfig.getHeight(), config.getHints());
        return configBitMatrix(bitMatrix);
    }

    private static BufferedImage configBitMatrix(BitMatrix bitMatrix) {
        // 问题：生成二维码正常,生成带logo的二维码logo变成黑白
        //            原因：MatrixToImageConfig默认黑白，需要设置BLACK、WHITE
        //            解决：https://ququjioulai.iteye.com/blog/2254382
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
        return MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
    }

    public static QRConfig getConfig() {
        return config;
    }

    public static void setConfig(QRConfig config) {
        QRCodeUtil.config = config;
    }

    public static QRTemplate baseConfig() {
        QRImageTemplate qrModuleEntity = new QRImageTemplate<String>();
        qrModuleEntity.setXOffset(0);
        qrModuleEntity.setYOffset(160);
        qrModuleEntity.setFontName("微软雅黑");
        qrModuleEntity.setFontSize(40);
        qrModuleEntity.setFontXOffset(45);
        qrModuleEntity.setTemplatePath("/yyq/二维码模板/JinHua.png");
//        qrModuleEntity.setLogoPath("/yyq/二维码模板/NanYang.png");
        qrModuleEntity.setUrl("http://rock.xingoxing.com/i/m?q=");
        qrModuleEntity.setSuffix("png");
        qrModuleEntity.setRed(0);
        qrModuleEntity.setGreen(0);
        qrModuleEntity.setBlue(0);
        qrModuleEntity.setAlpha(255);
        return qrModuleEntity;
    }

    public static void main(String... args) {
//        try {
        System.out.println(new Date());
//            create("https://www.baidu.com/s?word=在线翻译&tn=94115323_s_hao_pg&ie=utf-8&sc=UWY4PjDkPHnzndq1gv99UdqsuzuY5HDYP1RsnWR1PjnznH6hm1dCmytknWnhmynqnWTzn1c3n1cdnf&ssl_sample=normal&srcqid=265358282023608355&f=3&rsp=0","行我行");
//            createBuffered("https://www.baidu.com/s?word=在线翻译&tn=94115323_s_hao_pg&ie=utf-8&sc=UWY4PjDkPHnzndq1gv99UdqsuzuY5HDYP1RsnWR1PjnznH6hm1dCmytknWnhmynqnWTzn1c3n1cdnf&ssl_sample=normal&srcqid=265358282023608355&f=3&rsp=0");
//            System.out.println(new Date() + "" + Font.getDefault());
        List<String> contents = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        contents.add("1234");
        contents.add("12434");
        contents.add("12334");
        fileNames.add("1");
        fileNames.add("2");
        fileNames.add("3");
        QRLogoTemplate qrLogoTemplate = new QRLogoTemplate();
        qrLogoTemplate.setContents(contents);
        qrLogoTemplate.setFileNames(fileNames);
        qrLogoTemplate.setWidth(400);
        qrLogoTemplate.setHeight(400);
        qrLogoTemplate.setStoragePath("/yyq/二维码/qrcode");
        qrLogoTemplate.setLogoPath("/yyq/二维码模板/NanYang.png");
//        qrLogoTemplate.setFileName(System.currentTimeMillis() + "");
        List<QRTemplate> list = new ArrayList<>();
        list.add(qrLogoTemplate);

//        list.add(baseConfig());
        System.out.println(CreateQRCode.createQRCode(qrLogoTemplate));
//        CreateQRCode.createQRCode("明天10点要开会", System.currentTimeMillis() + "", baseConfig(), list);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
