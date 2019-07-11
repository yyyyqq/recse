package com.recse4cloud.common.util.excel;

import com.recse4cloud.common.core.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by YYQ on 2017/8/21.
 */
public class ExcelUtil {
    /**
     * 导出Excel 97(.xls)格式 ，少量数据
     * 每个sheet页不能超过65536条
     *
     * @param titles
     * @param objs
     */
    public static String FILE_TEMP = System.getProperty("user.home") + "/resource/files/temp/excle/";


    public static <T> File excleExport(List<ExcleTitleEntity> titles, List<T> objs) {
        String filePath = FILE_TEMP + System.currentTimeMillis() + ".xls";
        return excleExport(titles, objs, filePath);
    }

    /**
     * 导出Excel 97(.xls)格式 ，少量数据
     * 每个sheet页不能超过65536条
     *
     * @param titles
     * @param objs
     * @param filePath
     */
    public static <T> File excleExport(List<ExcleTitleEntity> titles, List<T> objs, String filePath) {
        // 声明一个工作薄
        HSSFWorkbook workbook = createHSSFWorkbook();
        // 生成一个(带标题)表格
        HSSFSheet sheet = createHSSFSheet(workbook);
        //导出
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdir();
                }
                file = new File(filePath);
            }
            //生成T
            importTitle(sheet, titles, objs);
            //数据填充
            importData(sheet, titles, objs);
            workbook.write(file);
        } catch (Exception e) {
            Logger.error(e, ExcelUtil.class);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    Logger.error(e, ExcelUtil.class);
                }
            }
        }
        return file;
    }


    /**
     * 生成Title
     *
     * @param sheet
     * @param titles
     * @param objs
     */
    private static <T> void importTitle(HSSFSheet sheet, List<ExcleTitleEntity> titles, List<T> objs) {
        // 遍历集合数据，产生数据行
        HSSFRow titlerRow = sheet.createRow(0);
        HSSFCell cell;
        int titlesLen = titles.size();
        int objsLen = objs.size() + 1;
        //生成title
        for (int j = 0; j < titlesLen; j++) {
            cell = titlerRow.createCell(j);
            cell.setCellValue(titles.get(j).getName());
        }
    }

    /**
     * 填充数据
     *
     * @param sheet
     * @param titles
     * @param objs
     * @param <T>
     */
    private static <T> void importData(HSSFSheet sheet, List<ExcleTitleEntity> titles, List<T> objs) {
        // 遍历集合数据，产生数据行
        HSSFRow row;
        HSSFCell cell;
        int titlesLen = titles.size();
        int objsLen = objs.size();
        Logger.info("titlesLen=" + titlesLen, ExcelUtil.class);
        Logger.info("objsLen=" + objsLen, ExcelUtil.class);
        //生成数据
        for (int i = 0; i < objsLen; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < titlesLen; j++) {
                cell = row.createCell(j);
                cell.setCellValue(getFieldValue(objs.get(i), titles.get(j).getField()));
                Logger.info("cell.value=" + cell.getStringCellValue(), ExcelUtil.class);
            }
        }
    }

    /**
     * 获取字段值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    private static String getFieldValue(Object obj, String fieldName) {
        StringBuilder name = new StringBuilder("get");
        name.append(fieldName.substring(0, 1).toUpperCase());
        name.append(fieldName.substring(1));
        Method method;
        try {
            method = obj.getClass().getMethod(name.toString());
        } catch (NoSuchMethodException e) {
            Logger.error("方法：【" + name + "】不存在", ExcelUtil.class);
            return null;
        }
        try {
            String result = String.valueOf(method.invoke(obj));
            return "null".equals(result) ? null : result;
        } catch (Exception e) {
            Logger.error(e.getLocalizedMessage(), ExcelUtil.class);
            return null;
        }
    }

    /**
     * 创建表格
     *
     * @param workbook
     * @return
     */
    private static HSSFSheet createHSSFSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet();
//        // 声明一个画图的顶级管理器
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//        // 定义注释的大小和位置,详见文档
//        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//                0, 0, 0, (short) 4, 2, (short) 6, 5));
//        // 设置注释内容
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("JACK");
        return sheet;
    }

    /**
     * 创建工作簿
     *
     * @return
     */
    private static HSSFWorkbook createHSSFWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
//        workbook.createInformationProperties();
//        workbook.getDocumentSummaryInformation().setCompany("行啊行网络科技有限公司");
//        SummaryInformation si = workbook.getSummaryInformation();
//        si.setAuthor("JACK");  //填加xls文件作者信息
//        si.setApplicationName("导出程序"); //填加xls文件创建程序信息
//        si.setLastAuthor("最后保存者信息"); //填加xls文件最后保存者信息
//        si.setComments("JACK is a programmer!"); //填加xls文件作者信息
//        si.setTitle("POI导出Excel"); //填加xls文件标题信息
//        si.setSubject("POI导出Excel");//填加文件主题信息
//        si.setCreateDateTime(new Date());
//        //表头样式
//        HSSFCellStyle titleStyle = workbook.createCellStyle();
//        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        HSSFFont titleFont = workbook.createFont();
//        titleFont.setFontHeightInPoints((short) 20);
//        titleFont.setBoldweight((short) 700);
//        titleStyle.setFont(titleFont);
//        // 列头样式
//        HSSFCellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        HSSFFont headerFont = workbook.createFont();
//        headerFont.setFontHeightInPoints((short) 12);
//        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        headerStyle.setFont(headerFont);
//        // 单元格样式
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        HSSFFont cellFont = workbook.createFont();
//        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        cellStyle.setFont(cellFont);
        return workbook;
    }
}
