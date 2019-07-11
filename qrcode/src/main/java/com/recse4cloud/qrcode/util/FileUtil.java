package com.recse4cloud.qrcode.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 */
public class FileUtil {
    public static String fileToZip(String sourceFilePath, String zipFilePath) {
        String zipPath = zipFilePath + "/" + System.currentTimeMillis() + ".zip";
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis;
        BufferedInputStream bis;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        if (!sourceFile.exists()) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在");
            return null;
        }
        try {
            File warPath = new File(zipFilePath);
            if (!warPath.exists()) {
                warPath.mkdirs();
            }
            File zipFile = new File(zipPath);
            if (zipFile.exists()) {
                System.out.println(zipFilePath + "目录下存在名字为:" + System.currentTimeMillis() + ".zip" + "打包文件.");
                return null;
            }
            File[] sourceFiles = sourceFile.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
                System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                return null;
            }
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < sourceFiles.length; i++) {
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                zos.putNextEntry(zipEntry);
                //读取待压缩的文件并写进压缩包里
                fis = new FileInputStream(sourceFiles[i]);
                bis = new BufferedInputStream(fis, 1024 * 10);
                int read;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
                fis.close();
                bis.close();
            }
            return zipPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != zos) zos.close();
                if (null != fos) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //删除所有文件
    public static boolean delAllFile(String path) {
        return delAllFile(path, null);
    }

    /**
     * 文件删除
     *
     * @param path
     * @return
     */
    public static boolean deleteDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return true;
        }

        if (dir.isFile()) {
            return dir.delete();
        }
        boolean flag = true;
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteDir(f.getAbsolutePath());
            } else {
                flag &= f.delete();
            }
        }
        return flag && dir.delete();
    }


    //删除给定的文件名的文件
    public static boolean delAllFile(String path, List<String> fileNameList) {
        boolean flag = true;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        File[] tempList = file.listFiles();
        for (File f : tempList) {
            //仅仅删除文件名列表里面的文件,防止全部删除是路径错误删除了不该删除的文件
            if (fileNameList != null && !fileNameList.isEmpty()) {
                if (fileNameList.contains(f.getName())) {
                    flag &= f.delete();
                }
            } else {
                flag &= f.delete();
            }
        }
        System.out.println(flag);
        return flag;
    }
}
