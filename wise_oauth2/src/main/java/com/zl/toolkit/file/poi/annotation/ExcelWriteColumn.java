package com.zl.toolkit.file.poi.annotation;

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
public @interface ExcelWriteColumn {

    //TODO 未来：基于现在的业务需求和时间进度考虑没有做写Excel的序号和标题了。现在的顺序在JXL中有体现，就是Class字段上下文顺序。
    /**
     * Excel中的列号，比如A、AZ、B
     * @return
     */
    //public String indexChar();
    
    /**
     * Excel中的列名称（业务名称）
     * @return
     */
    //public String columnTitle();
    
    /**
     * 是否写到Excel中
     * @return
     */
    public boolean write() default true;
    
}
