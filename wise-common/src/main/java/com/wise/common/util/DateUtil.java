package com.wise.common.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @ClassName: DateUtil
 *
 * @Description: 日期处理类
 * @author huanglw
 * @date 2017/11/15
 * @version V1.0.0
 *
 * History: [huanglw][2017-11-15][添加新方法：convertStringToDate(String dateStr, String format)]
 */
public class DateUtil {

    /**包含了大部分的日期格式*/
    private static final Map<String, String> dateRegFormat = new HashMap<String, String>();
    static {
        dateRegFormat.put("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$", "yyyy-MM-dd-HH-mm-ss");//2014年3月12日 13时5分34秒，2014-03-12 12:05:34，2014/3/12 12:5:34
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd-HH-mm");//2014-03-12 12:05
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd-HH");//2014-03-12 12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2014-03-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2014-03
        dateRegFormat.put("^\\d{4}$", "yyyy");//2014
        dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");//20140312120534
        dateRegFormat.put("^\\d{12}$", "yyyyMMddHHmm");//201403121205
        dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");//2014031212
        dateRegFormat.put("^\\d{8}$", "yyyyMMdd");//20140312
        dateRegFormat.put("^\\d{6}$", "yyyyMM");//201403
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm-ss");//13:05:34  拼接当前日期
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//13:05  拼接当前日期
        dateRegFormat.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//14.10.18(年.月.日)
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//30.12(日.月) 拼接当前年份
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.21.2013(日.月.年)
        dateRegFormat.put(".*CST.*","EEE MMM dd HH:mm:ss zzz yyyy");
    }

    /**
     * 获得线程内日历
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = calendarThreadLocal.get();
        calendar.setTime(date);
        return calendar;
    }
    private static final ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            return calendar;
        }
    };

    /**
     * 判断日期是否为null
     *
     * @param date java.util.Date 输入时间
     * @return boolean 如果date值为null，返回true，否则返回false
     */
    public static boolean isNull(Date date) {
        return date == null;
    }


    /**
     * 得到系统的当前时间
     *
     * @return java.util.Date 返回当前系统时间
     */
    public static Date getSystemDate(){
        return new Date();
    }

    /**
     * 获得某一个日期偏移 天数 前的日期
     *
     * @param date java.util.Date 传入日期
     * @param days int 天数
     * @return java.util.Date 返回偏移前的日期
     */
    public static Date getDateForBefore(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
        return calendar.getTime();
    }

    /**
     * 获得某一个日期偏移 天数 后的日期
     *
     * @param date java.util.Date 传入日期
     * @param days int 天数
     * @return java.util.Date 返回偏移后的日期
     */
    public static Date getDateForOffset(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        return calendar.getTime();
    }

    /**
     * 验证特定时间是否在一个时间范围内
     *
     * @param date java.util.Date 输入的判断时间
     * @param startDate java.lang.String 范围的开始时间
     * @param endDate java.lang.String 范围的结束时间
     * @return boolean 返回判断后的状态：在范围内返回 true，否则返回 false
     * @throws java.text.ParseException 转换异常
     */
    public static boolean isBetween(Date date, String startDate, String endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTemp = dateFormat.parse(startDate);
        Date endDateTemp = dateFormat.parse(endDate);
        return date.compareTo(startDateTemp)>=0 && date.compareTo(endDateTemp)<=0;
    }

    /**
     * 验证特定时间是否在一个时间范围内
     *
     * @param date java.util.Date 输入的判断时间
     * @param startDate java.util.Date 范围的开始时间
     * @param endDate java.util.Date 范围的结束时间
     * @return boolean 返回判断后的状态：在范围内返回 true，否则返回 false
     */
    public static boolean isBetween(Date date, Date startDate, Date endDate) {
        return date.compareTo(startDate)>=0 && date.compareTo(endDate)<=0;
    }

    /**
     * 字符串转换为日期格式
     *
     * @param dateStr java.lang.String  需要转换的日期字符串
     * @return java.util.Date 返回转换后的时间
     * @throws java.text.ParseException 转换异常
     */
    public static Date convertStringToDate(String dateStr) throws ParseException {
        return convertStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date convertStringToDate(String dateStr, String format) throws ParseException {

        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        return sdf1.parse(dateStr);
    }


    /**各种字符串日期格式的格式化*/
    public static Date strToDate(String dateStr) throws ParseException {
        String curDate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter2;
        String dateReplace;
        if(dateStr == null || "".equals(dateStr)){
            return null;
        }
        try {
            for (Map.Entry<String,String> entry : dateRegFormat.entrySet()){
                String key= entry.getKey();
                String value=entry.getValue();
                if (Pattern.compile(key).matcher(dateStr).matches()){
                    formatter2 = new SimpleDateFormat(value);
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                            || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }else if (key.equals(".*CST.*")){
                        SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                        return  sdf1.parse(dateStr);
                        //SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    // strSuccess = formatter1.format(formatter2.parse(dateReplace));
                    //System.out.println(strSuccess);
                    return formatter2.parse(dateReplace);
                    //break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("日期格式无效>>"+dateStr,0);
        }
        throw new ParseException("日期格式无效>>"+dateStr,0);
    }

    /**
     * 获取两个日期之间相差多少天
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int limitDays(Date startDate, Date endDate){
        return (int)((endDate.getTime() - startDate.getTime()) / (1000*60*60*24));
    }

    /**
     * 获取两个日期之间相差多少分钟
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int limitMinutes(Date startDate, Date endDate){
        return (int)((endDate.getTime() - startDate.getTime()) / (1000*60*24));
    }

    /**
     * 获取两个日期之间相差多少分秒
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int limitSeconds(Date startDate, Date endDate){
        return (int)((endDate.getTime() - startDate.getTime()) / (1000*24));
    }

    /** 时间转为当天 23:59:59 */
    public static Date turnToDateEnd(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /** 时间转为当天 00:00:00 */
    public static Date turnToDateStart(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     *得到某日期当月的第一天
     *
     * @param date java.util.Date 传入日期
     * @return java.util.Date 返回当月的第一天
     */
    public static Date getFirstDayForMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 得到某日期当月的最后一天
     *
     * @param date java.util.Date 传入日期
     * @return java.util.Date 返回当月的最后一天
     */
    public static Date getLastDayForMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 得到某日期本周第一天
     *
     * @param date 传入时期
     * @return date
     */
    public static Date getFirstDayForWeek(Date date){
        Calendar cal =Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        return cal.getTime();
    }

    /**
     * 得到某日期本周后一天
     *
     * @param date 传入时期
     * @return date
     */
    public static Date getLastDayForWeek(Date date){
        Calendar cal =Calendar.getInstance();
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 获取一年的第一天
     *
     * @param year
     * @return
     */
    public static Date getFirstDayForYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获得一年的最后一天
     *
     * @param year
     * @return
     */
    public static Date getLastDateForYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取某年第一天的开始时间
     *
     * @param year
     * @return
     */
    public static Date getFirstDayTimeForYear(int year){
        return turnToDateStart(getFirstDayForYear(year));
    }

    /**
     * 一年的最后一天的结束时间
     *
     * @param year
     * @return
     */
    public static Date getLastDateTimeForYear(int year){
        return turnToDateEnd(getLastDateForYear(year));
    }

    /**
     * 获取指定时间的年份
     */
    public static int getYear(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.MONTH)+1;
    }

}
