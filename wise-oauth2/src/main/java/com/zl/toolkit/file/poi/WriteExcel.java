package com.zl.toolkit.file.poi;

import com.deyuanyun.pic.common.util.ObjectUtil;
import com.deyuanyun.pic.common.util.web.Asserts;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import sun.reflect.misc.FieldUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xu on 2016/1/12.
 * 生成excel
 */
public class WriteExcel {
    private static Logger logger=Logger.getLogger(WriteExcel.class);

    /**
     *
     * @param template  模板
     * @param objList
     * @param rowIndex  从第几行开始
     * @param igronField 要忽略掉的字段名
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Workbook writeExcel(Workbook template, List<T> objList,int rowIndex,List<String> igronField)throws Exception{
        return writeExcel(template,objList,rowIndex,igronField,0);
    }
    public static <T> Workbook writeExcel(Workbook template, List<T> objList,int index,List<String> igronField,int sheetNum)throws Exception{
        if (ObjectUtil.isEmptyAll(objList)){
            return null;
        }
        Asserts.assertTrue(objList.size()<60000,"我靠!什么数据都大于6w了?");
        if (HSSFWorkbook.class.isAssignableFrom(template.getClass())){
            return writeRxcel2003(template,objList,index,igronField,sheetNum);
        }else if (XSSFWorkbook.class.isAssignableFrom(template.getClass())){
            return writeExcel2007(template,objList,index,igronField,sheetNum) ;
        }else {
            return null;
        }
    }

    private static<T> Workbook writeRxcel2003(Workbook template, List<T> objList,int index,List<String> igronField,int sheetNum)throws Exception{
        List<String> fieldNames=new LinkedList<String>();
        T obj=objList.get(0);
        Field[] fields = FieldUtil.getDeclaredFields(obj.getClass());
        for (Field field:fields){
            String name=field.getName();
            if(!"class".equals(name) && BeanUtilsBean.getInstance().getPropertyUtils().isReadable(obj, name)) {
                if (ObjectUtil.isNotNullEmpty(igronField)){
                    if (igronField.contains(name)){
                        continue;
                    }
                }
                fieldNames.add(name);
            }
        }
        HSSFWorkbook workbook= (HSSFWorkbook) template;
        //int sheetNum = workbook.getNumberOfSheets();
        HSSFSheet sheet=workbook.getSheetAt(sheetNum);
        /*if (sheetNum==1){
            sheet=workbook.getSheetAt(0);
        }else {
            sheet=workbook.getSheetAt(1);
        }*/
        //获取到原本的样式
        HSSFRow tRow=sheet.getRow(index);
        HSSFCellStyle style=null;
        if (tRow!=null){
            HSSFCell cell=tRow.getCell(0);
            style= cell.getCellStyle();
        }

        for (int i=0;i<objList.size();i++){
            T o=objList.get(i);
            HSSFRow row = sheet.createRow(i+index);
            for (int j=0;j<fieldNames.size();j++){
                String name=fieldNames.get(j);

                String cv = BeanUtils.getProperty(o,name);
                if (cv==null){
                    cv="";
                }
                HSSFCell genderCell = row.createCell(j);
                if (style!=null){
                    genderCell.setCellStyle(style);
                }
                genderCell.setCellValue(cv);
            }
        }
        return template;

    }

    private static<T> Workbook writeExcel2007(Workbook template, List<T> objList,int index,List<String> igronField,int sheetNum) throws Exception{
        List<String> fieldNames=new LinkedList<String>();
        T obj=objList.get(0);
        Field[] fields = FieldUtil.getDeclaredFields(obj.getClass());
        for (Field field:fields){
            String name=field.getName();
            if(!"class".equals(name) && BeanUtilsBean.getInstance().getPropertyUtils().isReadable(obj, name)) {
                if (ObjectUtil.isNotNullEmpty(igronField)){
                    if (igronField.contains(name)){
                        continue;
                    }
                }
                fieldNames.add(name);
            }
        }

        XSSFWorkbook workbook= (XSSFWorkbook) template;
        //int sheetNum = workbook.getNumberOfSheets();
        XSSFSheet sheet=workbook.getSheetAt(sheetNum);
        /*if (sheetNum==1){
            sheet=workbook.getSheetAt(sheetNum);
        }else {
            sheet=workbook.getSheetAt(1);
        }*/

        //获取到原本的样式
        XSSFRow tRow=sheet.getRow(index);
        XSSFCellStyle style=null;
        if (tRow!=null){
            XSSFCell cell=tRow.getCell(0);
            style= cell.getCellStyle();
        }


        for (int i=0;i<objList.size();i++){
            T o=objList.get(i);
            XSSFRow row = sheet.createRow(i+index);
            for (int j=0;j<fieldNames.size();j++){
                String name=fieldNames.get(j);
                String cv = BeanUtils.getProperty(o,name);
                if (cv==null){
                    cv="";
                }
                XSSFCell genderCell = row.createCell(j);
                if (style!=null){
                    genderCell.setCellStyle(style);
                }

                genderCell.setCellValue(cv);
            }
        }
        return template;
    }
}
