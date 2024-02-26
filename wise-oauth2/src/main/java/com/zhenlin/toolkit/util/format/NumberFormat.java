package com.zhenlin.toolkit.util.format;

import com.deyuanyun.pic.common.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * 数字格式化通用类
 *
 * @author huanglw
 * @date 2015-11-30.
 *
 * History: [修改人][时间][修改描述]
 */
public class NumberFormat {
    /**
     * 格式化数字
     *
     * @param inVal 输入数字
     * @param formatStr 格式化字符串
     * @param locale 数字本地化
     * @return
     */
    public static String format(BigDecimal inVal, String formatStr, Locale locale){
        if (inVal == null || formatStr == null){
            return null;
        }
        if (locale == null){
            locale = Locale.CHINESE;
        }
        DecimalFormat decimalFormat = (DecimalFormat) java.text.NumberFormat.getInstance(locale);
        decimalFormat.applyPattern(formatStr);
        return decimalFormat.format(inVal);
    }

    public static String format(BigDecimal inVal, String formatStr){
        return format(inVal, formatStr, Locale.CHINESE);
    }

    /**
     * 格式化数字，默认保留2位
     *
     * @param inVal 输入数字
     * @return
     */
    public static String format(BigDecimal inVal){
        return format(inVal, FMT_DEFAULT, Locale.CHINESE);
    }

    /**
     * 格式化数字，默认保留2位
     *
     * @param inVal 输入数字
     * @return
     */
    public static BigDecimal formatBigDecimal(BigDecimal inVal){
        String value = format(inVal, FMT_DEFAULT, Locale.CHINESE);
        return StringUtils.isNullEmpty(value) ? null : new BigDecimal(value);
    }

    /**
     * 默认格式化:保留2位小数
     */
    static public final String FMT_DEFAULT = "###,##0.##";

    /**
     * 必须保留2位小数，如果没有小数，在小数点后会用 .00，替换
     */
    static public final String FMT_PLACES_MUST = "###,##0.00";

    /**
     * 百分比
     */
    static public final String FMT_PERCENT = "###,##0.##%";

    /**
     * 保留三位小数（用于描述距离 m）
     */
    static public final String FMT_PLACES3 = "###,##0.###";

    /**
     * 必须保留三位小数：如果没有小数，在小数点后会用 .000（用于描述距离 m）
     */
    static public final String FMT_PLACES3_MUST = "###,##0.000";

    /**
     * 描述货币，保留4位小数
     */
    static public final String FMT_PLACES4_CURRENCY = "$####,###.####";

    /**
     * 描述货币，保留2位小数
     */
    static public final String FMT_PLACES2_CURRENCY = "$####,###.##";
}
