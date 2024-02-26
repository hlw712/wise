package com.zhenlin.wise.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间扩展工具
 *
 * @author huanglinwei
 * @date 2021/8/2 11:50 下午
 * @version PM-1180
 */
@Slf4j
public class DateExtUtils {

    /**
     * Date 转换为 LocalDateTime
     *
     * @param date
     * @return java.time.LocalDateTime
     * @throws Exception
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * LocalDateTime 转换为 Date
     *
     * @param localDateTime
     * @return java.util.Date
     * @throws Exception
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 得到传入日期的开始时间
     *
     * @param date
     * @throws Exception
     */
    public static Date getBeginTimeForDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到今天的开始时间
     *
     * @return
     */
    public static Date getTodayBeginTime() {
        return getBeginTimeForDay(new Date());
    }


    /**
     * 字符串转换为 Date
     *
     * @param dateStr
     * @param format 格式化字符串
     * @return
     */
    public static Date parse(String dateStr, String format) {
        try {
            format = StringUtils.isEmpty(format) ? DateFormatter.FORMAT_YYYYMMDD : format;
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            log.error("转换异常", e);
            return null;
        }
    }

    public static Date parse(Date date, String format) {
        String dateStr = DateFormatter.format(date, format);
        return parse(dateStr, format);
    }
}
