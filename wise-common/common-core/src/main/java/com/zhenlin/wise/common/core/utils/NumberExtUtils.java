package com.zhenlin.wise.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字工具
 *
 * @author huanglinwei
 * @date 2021/11/8 10:38 上午
 * @version 1.0.0
 */
@Slf4j
public class NumberExtUtils {

    /**
     * 格式化数字为千分位
     *
     * @param number
     * @return
     */
    public static String format(Number number) {
        return format(number, "###,##0.00");
    }

    public static String format(Number number, String formatStr) {
        return number == null ? null : new DecimalFormat(formatStr).format(number);
    }

    /**
     * 判断输入字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        // 如果输入的字符串是null，那么就不是数字
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 去掉str的前后空格后 转换为Long类型
     *
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        // 如果输入的字符串是null，返回null
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        str = str.trim();
        // 如果输入的字符串去掉前后空格后不是数字，返回null
        if (!isDigit(str)) {
            return null;
        }
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            log.error("字符串转换异常，转换字符串:{}", str);
        }
        return null;
    }
}
