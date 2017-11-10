package com.zl.toolkit.file.poi;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>概述</b>：<br/>
 * <p>Excel工具类<p/>
 *
 * <b>职责</b>：<br/>
 * 该类里定义的方法，可以直接通过“类名.方法名”的方式访问到，不用去实例化对象
 * 主要定义了excel的导出方法（使用的excel解析技术是：POI）
 * <p><pre></pre><p>
 *
 * @author huanglw [2015-10-9 21:58:22]
 * @version V1.0.0
 * @see javax.servlet.http.HttpServletResponse, java.io.FileOutputStream,org.apache.poi.hssf.usermodel.HSSFWorkbook
 *
 * @updater
 */
public class ExportExcelUtil {

    private Logger logger = LogManager.getLogger(ExportExcelUtil.class);

    private String filePath;
    private List<String[]> list;
    private Integer beginRow;
    private String[] headerNames;
    private Integer headerBeginRow;
    private Integer[] columnWidths;
    private String[] sheetNames;
    private String outExcelName;
    private HttpServletResponse response;

    public ExportExcelUtil(){}

    /**
     * 不带excel表头 的导出
     *
     * @param filePath 导出的文件路径
     * @param list excel内容
     * @param beginRow excel内容的 写入开始行数
     */
    public ExportExcelUtil(String filePath, List<String[]> list, Integer beginRow){
        this.filePath = filePath;
        this.list = list;
        this.beginRow = beginRow;
        this.headerNames = null;
        this.headerBeginRow = null;
        this.columnWidths = null;
        this.sheetNames = null;
        this.response = null;
        this.outExcelName = null;
    }

    /**
     * 带excel表头 的导出
     *
     * @param filePath 导出的文件路径
     * @param list excel内容
     * @param beginRow excel内容的 写入开始行数
     * @param columnWidths excel 的行宽
     * @param headerNames excel表头 if 已有模板，then null
     * @param headerBeginRow excel表头 if 已有模板，then null
     * @param sheetNames excel sheet 名 if 已有模板，then null
     */
    public ExportExcelUtil(String filePath, List<String[]> list, Integer beginRow, Integer[] columnWidths, String[] headerNames, Integer headerBeginRow, String[] sheetNames){
        this.filePath = filePath;
        this.list = list;
        this.beginRow = beginRow;
        this.headerNames = headerNames;
        this.headerBeginRow = headerBeginRow;
        this.columnWidths = columnWidths;
        this.sheetNames = sheetNames;
        this.response = null;
        this.outExcelName = null;
    }

    /**
     *  网页导出（不带excel表头）
     *
     * @param filePath 导出的文件路径
     * @param list excel内容
     * @param beginRow excel内容的 写入开始行数
     * @param response 返回对象
     * @param outExcelName 网页导出excel文件名
     */
    public ExportExcelUtil(String filePath, List<String[]> list, Integer beginRow, HttpServletResponse response, String outExcelName){
        this.filePath = filePath;
        this.list = list;
        this.beginRow = beginRow;
        this.columnWidths = null;
        this.headerNames = null;
        this.headerBeginRow = null;
        this.sheetNames = null;
        this.response = response;
        this.outExcelName = outExcelName;
    }

    /**
     * 网页导出（带excel表头）
     *
     * @param filePath 导出的文件路径
     * @param list excel内容
     * @param beginRow excel内容的 写入开始行数
     * @param columnWidths excel 的行宽
     * @param headerNames excel表头 如果是已有模板，请不需要填写该插参数
     * @param headerBeginRow excel表头 写入的开始行数 如果是已有模板，请不需要填写该插参数
     * @param sheetNames excel sheet 名 如果是已有模板，请不需要填写该插参数
     * @param response 返回对象
     * @param outExcelName 网页导出excel文件名
     */
    public ExportExcelUtil(String filePath, List<String[]> list, Integer beginRow, Integer[] columnWidths, String[] headerNames, Integer headerBeginRow, String[] sheetNames, HttpServletResponse response, String outExcelName){
        this.filePath = filePath;
        this.list = list;
        this.beginRow = beginRow;
        this.headerNames = headerNames;
        this.headerBeginRow = headerBeginRow;
        this.columnWidths = columnWidths;
        this.sheetNames = sheetNames;
        this.response = response;
        this.outExcelName = outExcelName;
    }


    public void importExcel(String filePath, String sheetName, String[] titles, List<String[]> list, int beginRow){

    }

    /**
     * 导出无模板的 Excel
     */
    public void exportExcel(){

        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try
        {
            fileOutputStream = new FileOutputStream(filePath);
            workbook = ExcelUtil.createWorkbook(list, beginRow, headerNames, headerBeginRow, columnWidths, sheetNames);
            workbook.write(fileOutputStream);
            if (response != null){
                response.setHeader("Content-disposition", "attachment; filename=" + new String(outExcelName.getBytes(), "iso-8859-1"));
                response.setContentType("application/msexcel");
            }
        } catch (FileNotFoundException e) {
            logger.error("------------------>Excel导出_文件没有找到报错，具体如下：\n" + e);
        } catch (IOException e) {
            logger.error("------------------>Excel导出_写入HSSFWorkbook时候报IO异常，具体如下：\n" + e);
        } finally {
            try {
                fileOutputStream.close();
                workbook.close();
            } catch (IOException e) {
                logger.error("------------------>Excel导出_关闭输出流报错，具体如下：\n" + e);
            }
        }
    }

    /**
     * 导出有模板的 Excel 数据
     *
     * @param filePath 文件名
     * @param listSheets
     * @param beginRows
     * @param response
     * @param outExcelName
     */
    public void exportExcel(String filePath, List<List<String[]>> listSheets, Integer[] beginRows, HttpServletResponse response, String outExcelName){

        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;

        try
        {
            fileOutputStream = new FileOutputStream(filePath);
            workbook = ExcelUtil.initWorkbook(filePath, listSheets, beginRows);
            workbook.write(fileOutputStream);
            if (response != null){
                response.setHeader("Content-disposition", "attachment; filename=" + new String(outExcelName.getBytes(), "iso-8859-1"));
                response.setContentType("application/msexcel");
            }
        } catch (FileNotFoundException e) {
            logger.error("------------------>Excel导出_[模板文件没有找到]报错，具体如下：\n" + e);
        } catch (IOException e) {
            logger.error("------------------>Excel导出_写入HSSFWorkbook时候报IO异常，具体如下：\n" + e);
        } finally {
            try {
                workbook.close();
                fileOutputStream.close();
            } catch (IOException e) {
                logger.error("------------------>Excel导出_关闭输出流报错，具体如下：\n" + e);
            }
        }
    }


    public static void main(String[] args) {

        String filePath = "E:\\aaaadd.xls";

        List<String[]> list1 = new ArrayList<String[]>();
        String[] s1 = {"hlw","123","321"};
        String[] s = {"mzz","33333","2222"};
        String[] s2 = {"lph","000","3332"};
        String[] s3 = {"lph","","12121212"};
        list1.add(s);
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);
//        Integer[] columnWidths = {120, 150, 100};
//        String[] headerNames = {"姓名","用户名","密码"};
//        String[] sheetNames = {"222","33"};
//
//        ExportExcelUtil excelUtils = new ExportExcelUtil(filePath, list1, 1, columnWidths, headerNames, 0, sheetNames);
//        excelUtils.exportExcel();

        List<List<String[]>> list2 = new ArrayList<List<String[]>>();
        list2.add(list1);
        Integer[] beginRows = {2, 2, 2};
        ExportExcelUtil excelUtils = new ExportExcelUtil();
        excelUtils.exportExcel(filePath, list2, beginRows, null, null);

        // 对读取Excel表格标题测试
        String[] title = ExcelUtil.getExcelHeader(filePath, 0);
        System.out.println("获得Excel表格的标题:");
        for (String str : title) {
            System.out.print(str + " ");
        }

        // 对读取Excel表格内容测试
        List<Map<String, String>> list = ExcelUtil.getExcelContent(filePath, null, 1);
        System.out.println("\n获得Excel表格的内容:");
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> temp = list.get(i);
            for (int j = 0; j<temp.size(); j++){
                System.out.print(temp.get(String.valueOf(j))+",");
            }
            System.out.println("");
        }

    }
}
