package com.recse4cloud.qrcode.util;

import com.recse4cloud.qrcode.config.QRTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CreateQRCode {
    private static Logger logger = LoggerFactory.getLogger(CreateQRCode.class);

    /**
     * 线程池
     */
    protected static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
    /**
     * 临时文件目录
     */
    private static final String TEMP_DIR = "temp";
    /**
     * 存储ZIP目录
     */
    private static final String ZIP_DIR = "zip";

    /**
     * 生成二维码
     *
     * @param templateList 二维码的配置信息
     */
    public static void createQRCode(List<QRTemplate> templateList) {
        if (templateList == null || templateList.isEmpty()) return;
        QRTemplate baseTemplate = templateList.get(0);
        if (baseTemplate.getContents() == null || baseTemplate.getContents().isEmpty()) return;
        int contentSize = baseTemplate.getContents().size();
        try {
            for (int i = 0; i < contentSize; i++) {
                baseTemplate.setContentIndex(i);
                DrawQRCode qrCode = baseTemplate.draw();
                for (QRTemplate template : templateList) {
                    qrCode = template.draw(qrCode);
                }
                store(baseTemplate, qrCode.draw());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成二维码，异步
     *
     * @param templateList 二维码的配置信息
     */
    public static void createQRCodeExecutor(List<QRTemplate> templateList) {
        executor.submit(() ->
                createQRCode(templateList));
    }

    public static String createQRCode(QRTemplate template) {
        if (StringUtils.isBlank(template.getTempPath()))
            template.setTempPath(template.getStoragePath() + File.separator + TEMP_DIR + File.separator + System.currentTimeMillis());
        List<QRTemplate> templates = new ArrayList<>();
        templates.add(template);
        createQRCode(templates);
        return zipQrcode(template.getTempPath(), template.getStoragePath());
    }

    /**
     * 存储二维码图片
     *
     * @param config 二维码的配置信息
     * @param buffer 图片缓冲数据
     * @throws IOException
     */
    public static void store(QRTemplate config, BufferedImage buffer) throws IOException {
        String fileName = getFileName(config);
        File store = new File(config.getTempPath(), fileName + "." + config.getSuffix());
        if (!store.getParentFile().exists()) {
            store.getParentFile().mkdirs();
        }
        ImageIO.write(buffer, config.getSuffix().toUpperCase(), store);
        buffer.flush();
    }

    private static String getFileName(QRTemplate config) {
        if (config.getFileNames() == null || config.getFileNames().isEmpty())
            return (config.getContentIndex() + 1) + "";
        return StringUtils.isBlank(config.getFileNames().get(config.getContentIndex())) ? (config.getContentIndex() + 1) + "" : config.getFileNames().get(config.getContentIndex());
    }

    /**
     * 压缩
     *
     * @param sourcePath 源文件路径
     * @param storePath  存储文件路径
     * @return
     */
    private static String zipQrcode(String sourcePath, String storePath) {
        //打包二维码图片
        String zipName = FileUtil.fileToZip(sourcePath, storePath + File.separator + ZIP_DIR);
        FileUtil.deleteDir(sourcePath);
        return zipName;
    }
}
