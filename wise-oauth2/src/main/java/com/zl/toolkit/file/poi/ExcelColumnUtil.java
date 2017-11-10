package com.zl.toolkit.file.poi;

/**
 * 主要是Excel字母和索引的转换
 *  如：A转换为1
 * 
 * @author ChenKui
 * @date 2016-06-21
 */
public class ExcelColumnUtil {
    
    /**
     * Excel column index begin 0
     * 
     * @param colStr
     * @return
     */
    public static int excelColStrToNum(String colStr) {
        int length = colStr.length();
        int num = 0;
        int result = 0;
        for (int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int) (ch - 'A');
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 0
     * 
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex < 0) {
            return null;
        }
        String columnStr = "";
        //columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

}
