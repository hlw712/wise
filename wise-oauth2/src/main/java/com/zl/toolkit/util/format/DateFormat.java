package com.zl.toolkit.util.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by linwei on 2015/11/30.
 */
public class DateFormat {

    public static String format(Date inVal, String formatStr, Locale locale){
        if (inVal == null || formatStr == null){
            return null;
        }
        if (locale == null){
            locale = Locale.CHINESE;
        }
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(inVal);
    }

    public static String format(Date inVal, String formatStr){
        return format(inVal, formatStr, null);
    }

    public static String format(Date inVal){
        return format(inVal, FMT_DEFAULT, null);
    }

    /**
     * 默认日期格式：yyyy-MM-dd
     */
    static public final String FMT_DEFAULT = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy-MM-dd
     */
    static public final String FMT_DATE_DEFAULT = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    static public final String FMT_DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyyMMddHHmmssSSS
     */
    static public final String FMT_DATE_TIMS_SSS = "yyyyMMddHHmmssSSS";

    /**
     * 日期格式：yyyyMMddHHmmss
     */
    static public final String FMT_DATE_TIME_ = "yyyyMMddHHmmss";

    /**
     * 日期格式：yyyy/MM/dd
     */
    static public final String FMT_DATE_ = "yyyy/MM/dd";

    /**
     * 日期格式：yyyyMMdd
     */
    static public final String FMT_DATE = "yyyyMMdd";
}
