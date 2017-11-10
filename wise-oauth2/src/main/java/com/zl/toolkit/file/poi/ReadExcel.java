package com.zl.toolkit.file.poi;

import com.deyuanyun.pic.common.cache.TempStoreDataSupport;
import com.deyuanyun.pic.common.util.ApplicationConfigUtil;
import com.deyuanyun.pic.common.util.FileUtils;
import com.deyuanyun.pic.common.util.ObjectUtil;
import com.deyuanyun.pic.common.util.StreamUtil;
import com.deyuanyun.pic.common.util.poi.annotation.ExcelReadColumn;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.FieldUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by xu on 2015/12/26.
 * 将excel封装为实体类
 * 
 * 2016-06-21更新：加了按字段ExcelColumn注解来对应列。
 * 
 * 
 */
public class ReadExcel {
    static Logger logger = Logger.getLogger(ReadExcel.class);

    /**
     * 读取excel文件,转换成对应的bean
     * @param t
     * @param inputStream
     * @param startIndex    数据开始行 从0开始
     * @param <T>
     *     最后一个参数第一个表示exce有多少列，不传的话默认自动统计，有可能会有空列的情况
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcle(Class<? extends T> t, InputStream inputStream, int startIndex,String... cellNum) throws Exception{
        if (inputStream==null){return null;}
        /*bytes=StreamUtil.InputStreamTOByte(is);
        is=new ByteArrayInputStream(bytes);
        byte[] b=ArrayUtils.subarray(bytes,0,6);
        String header= FileUtils.getFileHeader(b);*/

        /*
        List<InputStream> inputStreamList= StreamUtil.copyInputStream(inputStream,2);
        String header= FileUtils.getFileHeader(inputStreamList.get(0));
        if (FileUtils.FILE_HEADER_XLS.equals(header) ){
            return readFromXls2003(t,inputStreamList.get(1),startIndex,cellNum);
        }else if (FileUtils.FILE_HEADER_XLSX.equals(header) ){
            return readFromXls2007(t,inputStreamList.get(1),startIndex,cellNum);
        }else {
            throw new FileNotFoundException("不是正确的excel文件类型");
        }*/
        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }

        //TODO 未来：这个地方是不是只需要一个方法就可以了啊？用不同的Workbook即可。
        if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            //2003
            return readFromXls2003(t,inputStream,startIndex,cellNum);
        }
        else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
            //2007
            return readFromXls2007(t,inputStream,startIndex,cellNum);
        }else {
            inputStream.close();
            throw new FileNotFoundException("不是正确的excel文件类型");
        }
        
    }

    private static <T> List<T> readFromXls2003(Class<? extends T> t, InputStream inputStream, int startIndex,String... cellNum) throws Exception {
        Workbook workbook2003 = new HSSFWorkbook(inputStream);// 创建Excel2003文件对象
        Sheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
        String cellStr="";

        int rowN = sheet.getLastRowNum();
        if (startIndex > rowN) {
            logger.error(">>>你要开始的行数为" + startIndex + ">>实际行数为" + rowN);
            return null;
        }

        Map<String,Field> excel_field_map = new HashMap<String,Field>();

        List<String> fieldNames=new ArrayList<String>();
        Field[] fields = FieldUtil.getDeclaredFields(t);
        for (Field field:fields){
            String name=field.getName();
            ExcelReadColumn excelCell = field.getAnnotation(ExcelReadColumn.class);
            if(excelCell != null){//没有注解的，就按以前的方式添加进去。
                String excel_cell_index = excelCell.indexChar();
                excel_field_map.put(excel_cell_index, field);
                String columnTitle = excelCell.columnTitle();
                int columnTitleRowNumber = excelCell.columnTitleRowNumber();
                String columnTitleCellNumber = excelCell.columnTitleCellNumber();
                if(StringUtils.isNotEmpty(columnTitle) && StringUtils.isNotEmpty(columnTitleCellNumber) && columnTitleRowNumber != 0){
                    //如果有标题行，就验证
                    Cell cell = sheet.getRow(columnTitleRowNumber - 1).getCell(ExcelColumnUtil.excelColStrToNum(columnTitleCellNumber));
                    if(cell != null){
                        String a = cell.getStringCellValue().trim();
                        if(!columnTitle.equals(a)){
                            throw new RuntimeException("Excel模板不符：" + columnTitleCellNumber + columnTitleRowNumber + "单元格预期为“" + columnTitle + "”，实际为“" + a + "”");
                        }
                    }
                }
            }
            else if(!"class".equals(name) && BeanUtilsBean.getInstance().getPropertyUtils().isReadable(t.newInstance(), name)) {
                fieldNames.add(name);
            }

        }

        List<T> cList=new ArrayList<T>();

        for (int i = startIndex; i <= rowN; i++) {
            Row row = sheet.getRow(i);// 获取行对象
            if (row == null) {
                continue;
            }
            T newT =  t.newInstance();

            //遍历单元格

             int rowNumber=row.getLastCellNum();
            if (cellNum!=null&&cellNum.length>0){
                rowNumber=Integer.parseInt(cellNum[0]);
            }
            for (int j = 0; j < rowNumber; j++) {
                Cell cell = row.getCell(j);// 获取单元格对象
                if (cell == null) {
                    cellStr = "";
                }else {
                    if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                        if (DateUtil.isCellDateFormatted(cell)){
                            Date date = cell.getDateCellValue();
                            cellStr= com.deyuanyun.pic.common.util.DateUtil.DateToStringYMd(date);
                        }else {
                            double d = cell.getNumericCellValue();
                            if(d == (int)d){//是整数
                                cellStr = (int)d + "";
                            }else{
                                cellStr = new BigDecimal(d).setScale(6, RoundingMode.HALF_UP).floatValue() + "";
                            }
                            /*
                            double d = cell.getNumericCellValue();
                            cellStr = d + "";
                            if(cellStr != null && ( cellStr.indexOf("0000000") != -1 || cellStr.indexOf("9999999") != -1)){
                                //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                
                            }*/
                            //cell.setCellType(Cell.CELL_TYPE_STRING);
                            //cellStr = cell.getStringCellValue() + "";
                        }//如果有科学计数法，就重新来一遍。如果直接按照下面这个来，很小的小数值的时候，会很长。
                    }else if (cell.getCellType()==Cell.CELL_TYPE_FORMULA){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellStr = cell.getStringCellValue();
                       /* if (DateUtil.isCellDateFormatted(cell)){
                            Date date = cell.getDateCellValue();
                            cellStr= com.deyuanyun.pic.common.util.DateUtil.DateToString(date);
                        }else {//纯数字情况前面已经处理

                        }*/

                    }else if (cell.getCellType()==Cell.CELL_TYPE_STRING){
                        cellStr = cell.getStringCellValue();
                    }else {
                        cellStr=cell.toString();
                    }
                }
                if (cellStr!=null){
                    cellStr=cellStr.trim();
                }
                if(excel_field_map.isEmpty()){//如果无注解，就走原来的set方式
                    if(fieldNames.size() > j){
                        PropertyUtils.setProperty(newT,fieldNames.get(j),cellStr);
                    }
                }else{
                    String excel_col_index = ExcelColumnUtil.excelColIndexToStr(j);
                    Field field = excel_field_map.get(excel_col_index);
                    if(field != null){//实体类中没有对应解析的属性
                        Object val = null;
                        System.out.println(field.getType());
                        if(field.getType() == Date.class){
                            try {
                                val = com.deyuanyun.pic.common.util.DateUtil.allStr2Date(cellStr);
                            } catch (Exception e) {
                                // TODO: CK：待明确、未处理，如果预期Excel上传日期字段，却上传为非日期字段。
                            }
                        }
                        else if(Integer.class == field.getType()){
                            if(StringUtils.isNotBlank(cellStr)){
                                val = Integer.valueOf(cellStr);
                            }
                        }else{
                            val = cellStr;
                        }
                        PropertyUtils.setProperty(newT,field.getName(),val);
                        //BeanUtils.setProperty(newT,fieldName,cellStr);
                    }

                }

            }
            cList.add(newT);
        }
        return cList;
    }


    private static <T> List<T> readFromXls2007(Class<? extends T> t, InputStream inputStream, int startIndex,String...cellNum) throws Exception {
        XSSFWorkbook workbook2003 = new XSSFWorkbook(inputStream);// 创建Excel2003文件对象
        XSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
        String cellStr="";

        int rowN = sheet.getLastRowNum();
        if (startIndex > rowN) {
            logger.error(">>>你要开始的行数为" + startIndex + ">>实际行数为" + rowN);
            return null;
        }

        Map<String,Field> excel_field_map = new HashMap<String,Field>();

        List<String> fieldNames=new ArrayList<String>();
        Field[] fields = FieldUtil.getDeclaredFields(t);
        for (Field field:fields){
            String name=field.getName();
            ExcelReadColumn excelCell = field.getAnnotation(ExcelReadColumn.class);
            if(excelCell != null){//没有注解的，就按以前的方式添加进去。
                String excel_cell_index = excelCell.indexChar();
                excel_field_map.put(excel_cell_index, field);
                String columnTitle = excelCell.columnTitle();
                int columnTitleRowNumber = excelCell.columnTitleRowNumber();
                String columnTitleCellNumber = excelCell.columnTitleCellNumber();
                if(StringUtils.isNotEmpty(columnTitle) && StringUtils.isNotEmpty(columnTitleCellNumber) && columnTitleRowNumber != 0){
                    //如果有标题行，就验证
                    Cell cell = sheet.getRow(columnTitleRowNumber - 1).getCell(ExcelColumnUtil.excelColStrToNum(columnTitleCellNumber));
                    if(cell != null){
                        String a = cell.getStringCellValue().trim();
                        if(!columnTitle.equals(a)){
                            throw new RuntimeException("Excel模板不符：" + columnTitleCellNumber + columnTitleRowNumber + "单元格预期为“" + columnTitle + "”，实际为“" + a + "”");
                        }
                    }
                }
            }
            else if(!"class".equals(name) && BeanUtilsBean.getInstance().getPropertyUtils().isReadable(t.newInstance(), name)) {
                fieldNames.add(name);
            }

        }

        List<T> cList=new ArrayList<T>();

        for (int i = startIndex; i <= rowN; i++) {
            XSSFRow row = sheet.getRow(i);// 获取行对象
            if (row == null) {
                continue;
            }
            T newT =  t.newInstance();

            //遍历单元格
            int rowNumber=row.getLastCellNum();
            if (cellNum!=null&&cellNum.length>0){
                rowNumber=Integer.parseInt(cellNum[0]);
            }
            for (int j = 0; j < rowNumber; j++) {
                XSSFCell cell = row.getCell(j);// 获取单元格对象
                if (cell == null) {
                    cellStr = "";
                }else {
                    if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                        if (DateUtil.isCellDateFormatted(cell)){
                            Date date = cell.getDateCellValue();
                            cellStr= com.deyuanyun.pic.common.util.DateUtil.DateToStringYMd(date);
                        }else {
                            double d = cell.getNumericCellValue();
                            //cellStr = d + "";
                            if(d == (int)d){//是整数
                                cellStr = (int)d + "";
                            }else{
                                cellStr = new BigDecimal(d).setScale(6, RoundingMode.HALF_UP).floatValue() + "";
                            }
                            /*
                            if(cellStr != null && ( cellStr.indexOf("0000000") != -1 || cellStr.indexOf("9999999") != -1)){
                                //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                //double d = cell.getNumericCellValue();
                                
                            }*/
                            //cell.setCellType(Cell.CELL_TYPE_STRING);
                            //cellStr = cell.getStringCellValue() + "";
                        }//如果有科学计数法，就重新来一遍。如果直接按照下面这个来，很小的小数值的时候，会很长。
                        
                    }else if (cell.getCellType()==Cell.CELL_TYPE_FORMULA){
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cellStr = cell.getStringCellValue();
                       /* if (DateUtil.isCellDateFormatted(cell)){
                            // 如果是Date类型则，转化为Data格式
                            // data格式是带时分秒的：2013-7-10 0:00:00
                            // cellvalue = cell.getDateCellValue().toLocaleString();
                            // data格式是不带带时分秒的：2013-7-10
                            Date date = cell.getDateCellValue();
                            cellStr= com.deyuanyun.pic.common.util.DateUtil.DateToString(date);
                        }else {//纯数字情况前面已经处理

                        }*/

                    }else if (cell.getCellType()==Cell.CELL_TYPE_STRING){
                        cellStr = cell.getStringCellValue();
                    }else {
                        cellStr=cell.toString();
                    }
                }

                if (cellStr!=null){
                    cellStr=cellStr.trim();
                }

                if(excel_field_map.isEmpty()){//如果无注解，就走原来的set方式
                    if(fieldNames.size() > j){
                        PropertyUtils.setProperty(newT,fieldNames.get(j),cellStr);
                    }
                }else{
                    String excel_col_index = ExcelColumnUtil.excelColIndexToStr(j);
                    Field field = excel_field_map.get(excel_col_index);
                    if(field != null){//实体类中没有对应解析的属性
                        Object val = null;
                        System.out.println(field.getType());
                        if(field.getType() == Date.class){
                            try {
                                val = com.deyuanyun.pic.common.util.DateUtil.allStr2Date(cellStr);
                            } catch (Exception e) {
                                // TODO: CK：待明确、未处理，如果预期Excel上传日期字段，却上传为非日期字段。
                            }
                            
                        }
                        else if(Integer.class == field.getType()){
                            if(StringUtils.isNotBlank(cellStr)){
                                val = Integer.valueOf(cellStr);    
                            }
                        }else{
                            val = cellStr;
                        }
                        PropertyUtils.setProperty(newT,field.getName(),val);
                        //BeanUtils.setProperty(newT,fieldName,cellStr);
                    }
                    
                }
            }
            cList.add(newT);
        }
        return cList;
    }


    /**
     * 获取模板
     * @param fieName
     * @return
     * @throws Exception
     */
    public static Workbook readExcelTemplate(String fieName) throws Exception {
        byte[] bytes=TempStoreDataSupport.pullData("FILE_TEMPLATE_"+fieName);
        InputStream is = null;
        if (bytes==null||bytes.length==0) {
            String tempPath = ApplicationConfigUtil.getConfigByName("template.dir");
            if (StringUtils.isNotBlank(tempPath)) {
                //tempPath = ReadExcel.class.getClassLoader().getResource("").getPath() + "/template/";
                File file = new File(tempPath + fieName);
                if (!file.exists()) {
                    return null;
                }
                is = new FileInputStream(file);
            }else {
                is = ReadExcel.class.getResourceAsStream("/template/" + fieName);
            }

            if (is==null){
                return null;
            }

            bytes=StreamUtil.InputStreamTOByte(is);
            is=new ByteArrayInputStream(bytes);
            TempStoreDataSupport.pushDataByIdelTimeMinute("FILE_TEMPLATE_"+fieName,bytes,30);
        }else {
            is=new ByteArrayInputStream(bytes);
        }
       // List<InputStream> inputStreamList= StreamUtil.copyInputStream(is,2);
        byte[] b=ArrayUtils.subarray(bytes,0,6);
        String header= FileUtils.getFileHeader(b);
        if (FileUtils.FILE_HEADER_XLS.equals(header) ){
            HSSFWorkbook workbook2003 = new HSSFWorkbook(is);
            //TempStoreDataSupport.pushDataByIdelTimeMinute("FILE_TEMPLATE_"+fieName,inputStreamList.get(2),30);
            return workbook2003;
        }else if (FileUtils.FILE_HEADER_XLSX.equals(header) ){
            XSSFWorkbook workbook2007 = new XSSFWorkbook(is);
            //TempStoreDataSupport.pushDataByIdelTimeMinute("FILE_TEMPLATE_"+fieName,inputStreamList.get(2),30);
            return workbook2007;
        }else {
            throw new FileNotFoundException("不是正确的excel文件类型");
        }
    }


    /**
     * 获取title
     * @param file
     * @return
     * @throws Exception
     */
    public static String[] getTitle(MultipartFile file,String templateName) throws Exception {

        String stuff="_templateTitleArr";
        String[] varr=ReadExcel.templateTitle.get(templateName+stuff);
        if (ObjectUtil.isNotNullEmpty(varr)){
            return varr;
        }

        String[] tInfo = cnNameMap.get(templateName);
        if (ObjectUtil.isEmptyAll(templateName)){
            return null;
        }

        //Workbook workbook2003 = new HSSFWorkbook(file.getInputStream());// 创建Excel2003文件对象
        Workbook workbook2003=readExcelTemplate(templateName);
        Sheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
        List<CellRangeAddress> cellRangeAddresses = getCombineCell(sheet);


        if (ObjectUtil.isNotNullEmpty(tInfo)) {
            int startTitle = Integer.valueOf(tInfo[1]);
            Row row = sheet.getRow(startTitle);
            int rowNumber = row.getLastCellNum();
            varr=new String[rowNumber];
            for (int j = 0; j < rowNumber; j++) {
                Cell cell = row.getCell(j);
                String v=isCombineCell(cellRangeAddresses,cell,sheet);
                if (StringUtils.isNotBlank(v)){
                    v=v.replace("*","");
                }
                varr[j]=v;
            }
            //TempStoreDataSupport.pushDataf(templateName+stuff,varr);
            ReadExcel.templateTitle.put(templateName+stuff,varr);
            return varr;
        }

        return null;
    }


    /**
     * 合并单元格处理,获取合并行
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public static List<CellRangeAddress> getCombineCell(Sheet sheet)
    {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历合并单元格
        for(int i = 0; i<sheetmergerCount;i++)
        {
            //获得合并单元格加入list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }
    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     * @param listCombineCell 存放合并单元格的list
     * @param cell 需要判断的单元格
     * @param sheet sheet
     * @return
     */
    public static String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) throws Exception
    {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        if (ObjectUtil.isEmptyAll(listCombineCell)){
            cellValue=getCellValue(cell);
        }
        for(CellRangeAddress ca:listCombineCell)
        {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if(cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR)
            {
                if(cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC)
                {
                    Row fRow = sheet.getRow(firstR);
                    Cell fCell = fRow.getCell(firstC);
                    cellValue = getCellValue(fCell);
                    break;
                }
            }
            else
            {
                cellValue = getCellValue(cell);
            }
        }
        return cellValue;
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){

        if(cell == null) return "";

        if(cell.getCellType() == Cell.CELL_TYPE_STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){

            return cell.getCellFormula() ;

        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }


    //从数组中获取指定的值
    private static String getV(String[] strings){
        for (String s:strings){
            if (s.startsWith("titleRow")){
                String v=StringUtils.substringBetween(s,"{","}");
                return v;
            }
        }
        return "";
    }




    public static final Map<String,String[]> cnNameMap= Maps.newHashMap();//String[] 中文名称  表头是第几行(从0开始)
    static {
        cnNameMap.put("in/pipe_cross_t.xls",new String[]{"穿跨越导入模板","3"});
        cnNameMap.put("in/identification_t.xls",new String[]{"地面标识导入模板","3"});
        cnNameMap.put("in/track_point_t.xls",new String[]{"定标点导入模板","2"});
        cnNameMap.put("in/pigging_structure_t.xls",new String[]{"收发球装置导入模板","3"});

        cnNameMap.put("in/celbow_t.xls",new String[]{"冷弯导入模板","3"});
        cnNameMap.put("in/elbow_t.xls",new String[]{"热煨弯头导入模板","3"});
        cnNameMap.put("in/pipe_segment_gis_t.xls",new String[]{"管道中心线导入模板","3"});
        cnNameMap.put("in/pipe_segment_t.xls",new String[]{"管道信息导入模板","3"});

        cnNameMap.put("in/valve_t.xls",new String[]{"阀门导入模板","3"});
        cnNameMap.put("in/valve_room_t.xls",new String[]{"场站阀室坐标信息导入模板","3"});
        cnNameMap.put("in/tee_t.xls",new String[]{"三通导入模板","3"});
        cnNameMap.put("in/user_in_templet.xls",new String[]{"用户导入模板","0"});

        cnNameMap.put("in/trackPoint_t.xls", new String[]{"跟踪点导入模板","0"});


        cnNameMap.put("out/elbow_template.xls",new String[]{"弯头",""});
        cnNameMap.put("out/identification_template.xls",new String[]{"地面标识",""});
        cnNameMap.put("out/pigging_structure_template.xls",new String[]{"收发球装置",""});
        cnNameMap.put("out/pipe_cross_template.xls",new String[]{"穿跨越",""});
        cnNameMap.put("out/pipe_segment_template.xls",new String[]{"管道信息",""});
        cnNameMap.put("out/tee_template.xls",new String[]{"三通",""});
        cnNameMap.put("out/trackPoint_out_template.xls",new String[]{"跟踪点",""});
        cnNameMap.put("out/trackPoint_template.xls",new String[]{"mark点",""});
        cnNameMap.put("out/user_out_templet.xls",new String[]{"用户信息",""});
        cnNameMap.put("out/valve_template.xls",new String[]{"阀门",""});
    }

    public static final Map<String,String[]> templateTitle=new HashMap<String,String[]>();


}

