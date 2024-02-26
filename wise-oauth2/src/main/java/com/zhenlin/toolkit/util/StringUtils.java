package com.zhenlin.toolkit.util;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: StringUtils
 *
 * @Description: 字符处理类
 * @author huanglw
 * @date 2015-8-8 15:58:22
 * @version V1.0.0
 * @remarks:
 *
 * @updater 2015-9-7 huanglw  添加方法：
 *        （1）convertObjectToString(Object obj, String resultStr)
 *        （2）convertObjectToString(Object obj)
 *        （3）convertDateToString(Date date, String formatStr, String resultStr)
 *
 */
public class StringUtils {

    private static Logger logger = LogManager.getLogger(StringUtils.class);

    /**
     * 字符串日期格式：yyyyMMddHHmmss
     */
    public static final String DATE_TIME = "yyyyMMddHHmmss";

    /**
     * 字符串日期格式：yyyyMMddHHmmssSSS
     */
    public static final String DATE_TIMS_SSS = "yyyyMMddHHmmssSSS";

    /**
     * 默认字符串日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串日期格式：yyyy-MM-dd
     */
    public static final String DATE = "yyyy-MM-dd";
    
    /**
     * 字符串日期格式：yyyy
     */
    public static final String DATE_YEAR = "yyyy";

    /**
     * 横线：-
     */
    public static final String LINE = "-";


    /**
     * 判断字符串是否为null
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为null，返回true，否则返回false
     */
	public static boolean isNull(String str) {
		return str == null;
	}

    /**
     * 判断字符串是否为空字符（""）
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为空字符（""），返回true，否则返回false
     */
	public static boolean isEmpty(String str) {
		return "".equals(str==null ? "" : str.trim());
	}

    /**
     * 判断字符串是否为空字符（""），或者为 null
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为空字符（""）或者为 null，返回true，否则返回false
     */
	public static boolean isNullEmpty(String str) {
		return isNull(str) || isEmpty(str);
	}

    /**
     * 判断字符串是否不为null
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为null，返回false，否则返回true
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 判断字符串是否不为空字符（""）
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为空字符（""），返回false，否则返回true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为不空字符（""），并且不为 null
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为空字符（""）或者为 null，返回false，否则返回true
     */
    public static boolean isNotNullEmpty(String str) {
        return !isNullEmpty(str);
    }

    /**
     * 判断字符串是否为"null"字符串（不分大小写）
     *
     * @param str java.lang.String 输入字符串
     * @return boolean 如果str为"null"字符串，返回true，否则返回false
     */
	public static boolean isNullStr(String str) {
		return "null".equalsIgnoreCase(str);
	}

    /**
     * 字符串分割为数组
     *
     * @param data java.lang.String 字符串
     * @param regex java.lang.String 分隔符
     * @return java.lang.String[] 返回分割后的字符串数组
     */
    public static String[] split(String data, String regex) {
        if (isNullEmpty(data)) {
            return null;
        }
        return data.split(regex);
    }

    /**
     * 检查输入字符串长度是否在minLen与maxLen之间
     *
     * @param data java.lang.String 输入字符串
     * @param minLen int 最小长度
     * @param maxLen int 最大长度
     * @return boolean 如果data字符串长度在minLen与maxLen之间，或者data为空并且minLen为0，返回true，否则返回false
     */
    public static boolean isMinMaxLengthLimit(String data, int minLen, int maxLen)
    {
        if (isNullEmpty(data))
        {
            return minLen < 1;
        }
        return !(data.length() < minLen || data.length() > maxLen);
    }

    /**
     * 去掉输入字符串的前后空格
     *
     * @param str str java.lang.String 输入字符串
     * @return result java.lang.String 返回去掉前后空格后的字符串，如果字符串为null对象，那么返回空字符（""）
     */
    public static String processTrim(String str) {

        return (null == str) ? "" : str.trim();
    }

    /**
     * 去掉输入字符串的前后空格
     *
     * @param str str java.lang.String 输入字符串
     * @return result java.lang.String 返回去掉前后空格后的字符串，如果字符串为null对象，那么返回空字符null
     */
    public static String trim(String str) {

        return (null == str) ? null : str.trim();
    }

    /**
     * 日期格式转换为字符串格式
     *
     * @param date java.util.Date 需要转换的日期
     * @param formatStr formatStr java.lang.String  格式模板字符串  举例:yyyy-MM-dd
     * @param resultStr java.lang.String 如果对象为空需要返回的字段
     * @return java.lang.String 返回转换后的时间字符串，如果输入时间为空，那么返回为 resultStr
     */
    public static String convertDateToString(Date date, String formatStr, String resultStr) {

        if (date == null){
            return resultStr;
        }
        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        return sf.format(date);
    }

    /**
     * 日期格式转换为字符串格式
     *
     * @param date java.util.Date 需要转换的日期
     * @param formatStr formatStr java.lang.String  格式模板字符串  举例:yyyy-MM-dd
     * @return java.lang.String 返回转换后的时间字符串，如果输入时间为空，那么返回为null
     */
    public static String convertDateToString(Date date, String formatStr) {
        return convertDateToString(date, formatStr, null);
    }

    /**
     * 对象转换为字符串格式
     *
     * @param obj java.lang.Object 输入对象
     * @param resultStr java.lang.String 如果对象为空需要返回的字段
     * @return java.lang.String 返回转换后的字符串，如果输入对象为空，那么返回为 resultStr
     */
    public static String convertObjectToString(Object obj, String resultStr) {
        return (null == obj || "".equals(obj.toString())) ? resultStr : obj.toString();
    }

    /**
     * 对象转换为字符串格式
     *
     * @param obj java.lang.Object 输入对象
     * @return java.lang.String 返回转换后的字符串，如果输入对象为空，那么返回为默认的“-”
     */
    public static String convertObjectToString(Object obj) {
        return (null == obj || "".equals(obj.toString()) || "null".equals(obj.toString())) ? LINE : obj.toString();
    }

    /**
     * 对象日期格式转换为格式化的字符
     *
     * @param obj java.lang.Object 需要转换的日期
     * @param formatStr formatStr java.lang.String  格式模板字符串  举例:yyyy-MM-dd
     * @param resultStr java.lang.String 如果对象为空需要返回的字段
     * @return java.lang.String 返回转换后的时间字符串，如果输入时间为空，那么返回为 resultStr
     */
    public static String convertObjDateToString(Object obj, String formatStr, String resultStr) {

        if (obj == null || "".equals(obj.toString())){
            return resultStr;
        }
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(obj.toString());
        } catch (ParseException e) {
            logger.error("字符串转换为Date对象错误：" + e);
            return resultStr;
        }
        return dateFormat.format(date);
    }

    /**
     * 得到当前系统时间
     *
     * @return java.lang.String 系统当前时间，格式为：yyyyMMddHHmmss
     */
    public static String getCurrentDateTime() {
        return convertDateToString(new Date(), DATE_TIME);
    }

    /**
     * 得到当前系统时间
     *
     * @return java.lang.String 返回系统当前时间，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTimeDefault() {
        return convertDateToString(new Date(), DATE_TIME_DEFAULT);
    }
    
    /**
     * 首字母小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static void main(String[] args) {

	}
}
