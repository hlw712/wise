package com.zhenlin.toolkit.file.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示为Excel的列
 * 
 * indexChar为列号，用Excel中的字母来标记。
 * columnTitle为列标题。
 * 
 * @author ChenKui
 * @date 2016-02-21
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelReadColumn {

    /**
     * Excel中的列号，比如A、AZ、B
     * @return
     */
    public String indexChar();
    
    /**
     * Excel中的列名称（业务名称）
     * @return
     */
    public String columnTitle();
    
    /**
     * 标题行号
     * @return
     */
    public int columnTitleRowNumber() default 0;
    

    /**
     * 标题列号
     * @return
     */
    public String columnTitleCellNumber() default "";
    
    /**
     * 是否从Excel中读取
     * @return
     */
    public boolean read() default true;
    
}
