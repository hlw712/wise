package com.zhenlin.wise.common.core.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatter {

    /**
     * 默认时区
     */
    public static final String TIME_ZONE_DEFAULT = "Etc/GMT-4";
    /**
     * 默认格式：yyyy-MM-dd
     */
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public static final String FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

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

    /**
     * 格式日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        return format(date, format, null) ;
    }

    /**
     * 格式日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format, TimeZone timeZone) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isEmpty(format)) {
            format = FORMAT_YYYYMMDD;
        }
        if (timeZone == null) {
            timeZone = TimeZone.getTimeZone(TIME_ZONE_DEFAULT);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据时区格式日期
     * 如果时区没有获取到，默认使用阿联酋迪拜（东4区）时区
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format, String timezoneCode) {
        timezoneCode = StringUtils.isEmpty(timezoneCode) ? TIME_ZONE_DEFAULT : timezoneCode;
        return format(date, format, TimeZone.getTimeZone(timezoneCode));
    }
}
