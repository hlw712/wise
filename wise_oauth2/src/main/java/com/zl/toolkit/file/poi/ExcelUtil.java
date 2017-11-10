package com.zl.toolkit.file.poi;

import com.deyuanyun.pic.common.tool.Globals;
import com.deyuanyun.pic.common.util.StringUtils;
import com.deyuanyun.pic.common.util.converter.BeanConverter;
import com.deyuanyun.pic.common.util.web.RequestResponseContext;
import com.deyuanyun.pic.common.util.web.WebUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * <b>概述</b>：<br/>
 * <p>Excel工具类<p/>
 *
 * <b>职责</b>：<br/>
 * 该类里定义的方法，可以直接通过“类名.方法名”的方式访问到，不用去实例化对象
 * 主要定义了excel的读取和写入两组方法（使用的excel解析技术是：POI）
 * <p><pre></pre><p>
 *
 * @author huanglw [2015-10-9 21:58:22]
 * @version V1.0.0
 * @see org.apache.poi.poifs.filesystem.POIFSFileSystem, org.apache.poi.hssf.usermodel.HSSFWorkbook,org.apache.poi.hssf.usermodel.HSSFSheet, org.apache.poi.hssf.usermodel.HSSFRow, org.apache.poi.hssf.usermodel.HSSFDateUtil
 *
 * @updater
 *  CK：2016-06-27 兼容2003和2007的修改
 *  
 */
public class ExcelUtil {

    protected static Logger logger = LogManager.getLogger(ExcelUtil.class);

    private static final BigDecimal sheetRows = new BigDecimal(60000);

    /**
     * 读取Excel表格表头的内容
     *
     * @param path
     * @param beginRow
     * @return String 表头内容的数组
     */
    public static String[] getExcelHeader(String path, int beginRow) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("excel读取的文件没有找到：" + e);
        }
        Workbook hssfWorkbook = getWorkBook(inputStream);
        Sheet sheet = hssfWorkbook.getSheetAt(0);
        beginRow = beginRow < 0 ? 0 : beginRow;
        Row row = sheet.getRow(beginRow);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }

    /**
     * 读取excel，放入到 List<Map<String, String>>集合中
     *
     * @param path excel文件路径
     * @param resultKeys 返回Map对象的key的名称
     * @param beginRow 在excel中开始读取行数
     * @return
     */
    public static List<Map<String, String>> getExcelContent(String path, String[] resultKeys, int beginRow) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("excel读取的文件没有找到：" + e);
        }
        return readExcel(inputStream, resultKeys, beginRow);
    }

    /**
     * 读取excel，放入到 List<Map<String, String>>集合中
     *
     * @param inputStream excel文件输入流
     * @param resultKeys 返回Map对象的key的名称
     * @param beginRow 在excel中开始读取行数
     * @return
     */
    private static List<Map<String, String>> readExcel(InputStream inputStream, String[] resultKeys, int beginRow) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Workbook hssfWorkbook = getWorkBook(inputStream);
        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
        // 得到总行数
        int rowNum = hssfSheet.getLastRowNum();
        Row hssfRow = hssfSheet.getRow(0);
        int colNum = hssfRow.getPhysicalNumberOfCells();
        if (resultKeys != null && resultKeys.length != colNum){
            return null;
        }

        Map<String, String> map = null;
        // 正文内容应该从第二行开始,第一行为表头的标题
        beginRow = beginRow < 0 ? 1 : beginRow;
        for (int i = beginRow; i <= rowNum; i++) {

            map = new HashMap<String, String>();
            hssfRow = hssfSheet.getRow(i);
            for (int j = 0; j < colNum; j++) {
                String mapKey = (resultKeys==null ? String.valueOf(j) : resultKeys[j]);
                map.put(mapKey, getCellFormatValue(hssfRow.getCell(j)).trim());
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 得到输入流中的 Workbook对象
     *
     * @param inputStream
     * @return
     */
    private static Workbook getWorkBook(InputStream inputStream) {

        Workbook workbook = null;
        try {
            //POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
            if (!inputStream.markSupported()) {
                inputStream = new PushbackInputStream(inputStream, 8);
            }
            if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
                //2003
                workbook = new HSSFWorkbook(inputStream);
            } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
                //2007
                workbook = new XSSFWorkbook(inputStream);
            }else {
                inputStream.close();
                throw new FileNotFoundException("不是正确的excel文件类型");
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return workbook;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        String cellValue = "";
        // 判断当前Cell的Type
        switch (cell.getCellType()) {
            // 如果当前Cell的Type为numeric
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //这样子的data格式是带时分秒的：yyyy-MM-dd HH:mm:ss
                    Date date = cell.getDateCellValue();
                    cellValue = StringUtils.convertDateToString(date, StringUtils.DATE_TIME_DEFAULT);
                }
                // 如果是纯数字, 取得当前Cell的数值
                else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为String ,取得当前的Cell字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            default:
                cellValue = "";
        }

        return cellValue;
    }

    /**
     * 創建Workbook
     *
     * @param list excel内容
     * @param beginRow excel内容的 写入开始行数
     * @param headerNames excel表头
     * @param headerBeginRow excel表头 写入的开始行数
     * @param columnWidths excel 的行宽
     * @param sheetNames excel sheet 名
     * @return HSSFWorkbook
     */
    public static Workbook createWorkbook(List<String[]> list, Integer beginRow, String[] headerNames, Integer headerBeginRow, Integer[] columnWidths, String[] sheetNames){

        if (list == null){
            return null;
        }
        BigDecimal count = new BigDecimal(list.size());
        int sheetTotal = (int) Math.ceil(count.divide(sheetRows, 32 ,BigDecimal.ROUND_HALF_UP).doubleValue());
        // 如果输入的sheet名称为空，那么给sheet名称数组赋值
        if (sheetNames==null){
            sheetNames = new String[]{"Sheet1"};
        }
        headerBeginRow = (headerBeginRow==null || headerBeginRow < 0) ? 0 : headerBeginRow;

        // 第一步，创建一个webbook，对应一个Excel文件
        Workbook workbook = new HSSFWorkbook();

        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        for (int i = 0; i < sheetTotal; i++){

            String sheetName = null;
            if (sheetNames.length-1 >= i){
                sheetName = sheetNames[i];
            } else {
                sheetName = "Sheet"+(i+1);
            }


            Sheet sheet = workbook.createSheet(sheetName);
            // 第三步，在sheet中添加表头第0行
            Row row = sheet.createRow(headerBeginRow);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle style = createContentStyle(workbook);
            Cell cell = null;

            setColumnWidth(sheet, columnWidths);

            // 第五步，创建单元格，添加excel表头数据
            createExcelHeader(cell, row, headerStyle, headerNames);

            int startRow = sheetRows.multiply(new BigDecimal(i)).intValue();
            int endRow = (i==sheetTotal-1) ? list.size() : sheetRows.multiply(new BigDecimal((i + 1))).intValue();
            // 第五步，创建单元格，添加excel的内容
            createExcelContent(cell, sheet, row, style, list, beginRow, startRow, endRow);
        }

        return workbook;
    }

    /**
     * 针对一个Sheet的方法。
     * 
     * @param filePath
     * @param listSheet
     * @param beginRow
     * @return
     * @throws IOException
     */
    public static Workbook initWorkbook(String filePath, List<String[]> listSheet, Integer beginRow) throws IOException{
        
        List<List<String[]>> listSheets = new ArrayList<List<String[]>>();
        listSheets.add(listSheet);
        Integer[] beginRows = new Integer[]{beginRow};
        return initWorkbook(filePath, listSheets, beginRows);
    }

    /**
     * excel 写入单个Sheet的方法。
     *
     * @param filePath 写入的文件路径
     * @param columnKeys 列名
     * @param dataList 数据
     * @param beginRow 从哪行开始写入
     *
     * @return
     * @throws IOException
     */
    public static Workbook initWorkbook(String filePath, String[] columnKeys, List<Map> dataList, Integer beginRow) throws IOException{

        if (dataList == null){
            return null;
        }
        Workbook workbook = null;
        try {
            // TODO 待处理：按照现在的打包方式，如果pic_common打包为jar包，那么这个地方是取不到web项目中的资源文件的。
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(filePath);//new FileInputStream(filePath);
            workbook = getWorkBook(inputStream);
        } catch (Exception e) {
            throw new FileNotFoundException("excel导出模板文件_没有找到："+e);
        }

        Sheet sheet = workbook.getSheetAt(0);
        CellStyle style = createContentStyle(workbook);

        // 写入数据
        createExcelContent(sheet, style, columnKeys, dataList, beginRow);

        return workbook;
    }

    /**
     * excel 写入多个Sheet的方法。
     *
     * @param filePath 写入的文件路径
     * @param sheetList 列名
     *
     * @return
     * @throws IOException
     */
    public static Workbook initWorkbook(String filePath, List<SheetParam> sheetList) throws IOException{

        if (sheetList == null){
            return null;
        }

        Workbook workbook = null;

        try {
            // TODO 待处理：按照现在的打包方式，如果pic_common打包为jar包，那么这个地方是取不到web项目中的资源文件的。
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(filePath);//new FileInputStream(filePath);
            workbook = getWorkBook(inputStream);
        } catch (Exception e) {
            throw new FileNotFoundException("excel导出模板文件_没有找到："+e);
        }

        Sheet sheet = null;
        CellStyle style = createContentStyle(workbook);

        for (int i = 0; i < sheetList.size(); i++){

            sheet = workbook.getSheetAt(i);
            SheetParam sheetParam = sheetList.get(i);

            // 写入数据
            createExcelContent(sheet, style, sheetParam.getColumnKeys(), sheetParam.getDataList(), sheetParam.getBeginRow());
        }

        return workbook;
    }

    /**
     * excel模板导出
     *
     * @param filePath 导出文件路径名称
     * @param tempPath excel模板路径
     * @param beginRow 开始写入行数
     * @param data list<T>数据
     * @param columnKeys 写入文件顺序（map的key顺序）
     * @throws IOException
     */
    public static <T>void export(String filePath, String tempPath, Integer beginRow, List<T> data, String[] columnKeys) throws IOException {
        List<Map> mapList = BeanConverter.toListMap(data);
        Workbook workbook = ExcelUtil.initWorkbook(tempPath, columnKeys, mapList, beginRow);
        WebUtil.setDownHeader(filePath);
        HttpServletResponse response = RequestResponseContext.getResponse();
        workbook.write(response.getOutputStream());
    }

    /**
     * excel模板多个Sheet页签导出
     *
     * @param filePath
     * @param tempPath excel模板路径
     * @param sheetList
     * @throws IOException
     */
    public static void export(String filePath, String tempPath, List<SheetParam> sheetList) throws IOException {
        Workbook workbook = ExcelUtil.initWorkbook(tempPath, sheetList);
        WebUtil.setDownHeader(filePath);
        HttpServletResponse response = RequestResponseContext.getResponse();
        workbook.write(response.getOutputStream());
    }

    public static Workbook initWorkbook(String filePath, List<List<String[]>> listSheets, Integer[] beginRows) throws IOException{

        if (listSheets == null){
            return null;
        }
        Workbook workbook = null;
        try {
            // TODO 待处理：按照现在的打包方式，如果pic_common打包为jar包，那么这个地方是取不到web项目中的资源文件的。
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(filePath);//new FileInputStream(filePath);
            workbook = getWorkBook(inputStream);
        } catch (Exception e) {
            throw new FileNotFoundException("excel导出模板文件_没有找到："+e);
        //} catch (Exception e) {
        //    throw new IOException("excel导出模板文件IO流写入到Workbook错误："+e);
        }

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        CellStyle style = createContentStyle(workbook);

        for (int i = 0; i < listSheets.size(); i++){

            sheet = workbook.getSheetAt(i);
            List<String[]> list = listSheets.get(i);
            // 写入数据
            createExcelContent(cell, sheet, row, style, list, beginRows[i] + i, 0, list.size());
        }

        return workbook;
    }

    private static void setColumnWidth(Sheet sheet, Integer[] columnWidths) {

        if (columnWidths == null){
             return;
        }
        // 设置列宽度（像素）
        for (int i = 0; i < columnWidths.length; i++) {
            sheet.setColumnWidth(i, 32 * columnWidths[i]);
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook){

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);    // 设置垂直居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);               // 设置水平居中
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);             // 下边框
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);               // 左边框
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);                // 上边框
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);              // 右边框
        headerStyle.setFillForegroundColor(HSSFColor.AQUA.index);            // 设置背景色
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);            // 设置背景色展示效果

        Font headerFont = workbook.createFont();                          // 创建字体样式
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);                // 字体加粗
        headerFont.setFontName("微软雅黑");                                 // 设置字体类型
        headerFont.setFontHeightInPoints((short) 10);                       // 设置字体大小
        headerFont.setColor(HSSFColor.WHITE.index);

        headerStyle.setFont(headerFont);                                    // 为标题样式设置字体样式
        return headerStyle;
    }

    private static CellStyle createContentStyle(Workbook workbook){

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);               // 设置水平居中
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);             // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);               // 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);                // 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);              // 右边框
        cellStyle.setWrapText(true);                                       // 设置为自动换行
        return cellStyle;
    }

    /**
     * 写入 excel 表头
     *
     * @param cell
     * @param row
     * @param style
     * @param titles
     * @return
     */
    private static void createExcelHeader(Cell cell, Row row, CellStyle style, String[] titles){
        if (titles == null) {
            return;
        }
        for (int i = 0; i < titles.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
    }

    /**
     * 写入 excel 内容
     *
     * @param cell
     * @param sheet
     * @param row
     * @param style
     * @param list
     * @param beginRow
     */
    private static void createExcelContent(Cell cell, Sheet sheet, Row row, CellStyle style, List<String[]> list, int beginRow, int listStartRow, int listEndRow){

        if (list == null){
            return;
        }

        int n = 0;
        for (int i = listStartRow; i < listEndRow; i++)
        {
            row = sheet.createRow(n + beginRow);
            String[] rowObj = list.get(i);

            for (int j = 0; j < rowObj.length; j++){
                cell = row.createCell(j);
                cell.setCellValue(rowObj[j]);
                cell.setCellStyle(style);
            }
            n++;
        }
    }

    /**
     * 写入 excel 内容
     *
     * @param sheet 写入的sheet
     * @param style excel内容样式
     * @param columnKeys 列名
     * @param dataList 数据
     * @param beginRow 从哪行开始写入
     */
    private static void createExcelContent(Sheet sheet,  CellStyle style, String[] columnKeys, List<Map> dataList, int beginRow){

        if (dataList == null || columnKeys == null){
            return;
        }
        Cell cell = null;
        Row row = null;

        int n = 0;
        for (Map map : dataList) {

            row = sheet.createRow(n + beginRow);
            for (int j = 0; j < columnKeys.length; j++){
                cell = row.createCell(j);
                if (map.containsKey(columnKeys[j])){
                    Object value = map.get(columnKeys[j]);
                    cell.setCellValue(StringUtils.convertObjectToString(String.valueOf(value)));
                }
                cell.setCellStyle(style);
            }
            n++;
        }
    }

    public static class SheetParam {
        // 列名
        private String[] columnKeys;
        // 数据
        private List<Map> dataList;
        // 从哪行开始写入
        private Integer beginRow;

        public SheetParam(String[] columnKeys, List<Map> dataList, Integer beginRow) {
            this.beginRow = beginRow;
            this.columnKeys = columnKeys;
            this.dataList = dataList;
        }

        public String[] getColumnKeys() {
            return columnKeys;
        }

        public void setColumnKeys(String[] columnKeys) {
            this.columnKeys = columnKeys;
        }

        public List<Map> getDataList() {
            return dataList;
        }

        public void setDataList(List<Map> dataList) {
            this.dataList = dataList;
        }

        public Integer getBeginRow() {
            return beginRow;
        }

        public void setBeginRow(Integer beginRow) {
            this.beginRow = beginRow;
        }


    }
    
    /**
     * 填充Excel的头部，比如LOGO、公司名称、文件名
     * 
     * @param workbook
     * @param logo_bytes
     * @param companyName
     * @param fileTitle
     * @return
     */
    public static Workbook fillWorkbookHead(Workbook workbook,byte[] logo_bytes,String companyName,String fileTitle){
        //int numberOfSheets = wordbook.getNumberOfSheets();
        Sheet sheet = workbook.getSheetAt(0);
        int lastCellNum = 0;
        int lastCellNum_T = sheet.getRow(0).getLastCellNum();
        int lastRowNum = sheet.getLastRowNum();
        //此循环用于判断是否为空列，因为Excel判断空列的方式为Delete后的列才是空列。
        for (int i = lastCellNum_T; i > 0 && lastCellNum == 0; i--) {
            for (int j = 0; j < 10 && j <= lastRowNum; j++) {//向下取10行，用于判断此列是否为Excel末尾。
                Row row = sheet.getRow(j);
                if(row != null){
                    Cell cell = sheet.getRow(j).getCell(i - 1);
                    if(cell != null){
                        if(StringUtils.isNotEmpty(cell.getStringCellValue())){
                            lastCellNum = i - 1;
                            break;
                        }
                    }   
                }else{
                    System.out.println();
                }
            }
        }

        sheet.shiftRows(0, sheet.getLastRowNum(), 2,true,false);//向下移两行。
        
        Row row = sheet.createRow(0);//插入第一行
        row.setHeight((short)(85.5 * 20));
        //sheet.setColumnWidth(0, (int)16 * 256);
        Cell cell0 = row.createCell(0);
        if(StringUtils.isEmpty(companyName)){
            companyName = Globals.getProperties("companyName");
            if(StringUtils.isEmpty(companyName)){
                companyName = "四川德源云管道信息技术有限公司";
            }
        }
        row.createCell(1).setCellValue(companyName);
        row.createCell(lastCellNum).setCellValue("日期：" + StringUtils.convertDateToString(new Date(), "yyyy-MM-dd"));;
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,3));//合并第一行空白内容
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,lastCellNum - 1));//合并第一行空白内容
        
        Row row2 = sheet.createRow(1);//插入第二行
        row2.setHeightInPoints(20.25f);
        Font font=workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        CellStyle paramCellStyle = workbook.createCellStyle();
        paramCellStyle.setFont(font);
        paramCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        paramCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        paramCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        paramCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        paramCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
        for (int i = 0; i <= lastCellNum; i++) {
            row2.createCell(i).setCellStyle(paramCellStyle);
        }
        row2.getCell(0).setCellValue(fileTitle);
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,lastCellNum));//合并标题行
        
        for (int i = 0; i <= lastCellNum; i++) {
            //sheet.autoSizeColumn(i);//不要自动缩放宽度，因为他们的测试数据太长，肯定会因此提优化。
        }
        
        //插入Logo，最后来做，因为autoSizeColumn会导致Logo变型。
        try {
            // 插入 PNG 图片至 Excel
            if(logo_bytes == null){
                String fileName1 = Globals.getProperties("logo");
                if(StringUtils.isEmpty(fileName1)){
                    fileName1 = "/template/logo.png";
                }
                InputStream is = ExcelUtil.class.getResourceAsStream(fileName1);//new FileInputStream(fileName1);
                logo_bytes = IOUtils.toByteArray(is);
            }
            /**/
            int pictureIdx = workbook.addPicture(logo_bytes, Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            // 图片插入坐标
            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(1);
            anchor.setRow2(1);
            // 插入图片
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //pict.resize();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return workbook;
    }
 

}
