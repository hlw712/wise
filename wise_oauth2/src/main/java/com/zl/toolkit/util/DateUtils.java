package com.zl.toolkit.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 时间工具类
 *
 * @author YangQing
 * @version 1.0.0
 */

public class DateUtils extends ObjectUtils {

	public static String FM_DATA_TIME = "yyyy-MM-dd HH:mm:ss";

	// 判断选择的日期是否是本周
	public static boolean isThisWeek(long time) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(new Date(time));
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	// 判断选择的日期是否是今天
	public static boolean isToday(long time) {
		return isThisTime(time, "yyyy-MM-dd");
	}

	// 判断选择的日期是否是本月
	public static boolean isThisMonth(long time) {
		return isThisTime(time, "yyyy-MM");
	}

	private static boolean isThisTime(long time, String pattern) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String param = sdf.format(date);// 参数时间
		String now = sdf.format(new Date());// 当前时间
		if (param.equals(now)) {
			return true;
		}
		return false;
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));

			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 时间转int
	 * 
	 * @param smdate
	 *            时间
	 * @return int型
	 */
	public static int dateFomateYM(Date smdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return Integer.parseInt(sdf.format(smdate));
	}

	/**
	 * 时间格式转换
	 * 
	 * @param smdate
	 *            时间
	 * @return string型
	 */
	public static String dateFomate(Date smdate, String simpleDateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat);
		return sdf.format(smdate);
	}

	/**
	 * 获取当前时间字符串
	 */
	public static String getNowStr() {
		DateFormat format = new SimpleDateFormat("HH:mm");
		String time = format.format(new Date());
		return time;
	}

	/**
	 * 获取今天周几（中国）
	 * 
	 * @return
	 */
	public static int getChineseDayOffsetOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			return 7;
		} else {
			return weekday - 1;
		}
	}

	/**
	 * 获取今天周几（国际）
	 * 
	 * @return
	 */
	public static int getDayOffsetOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		return weekday;
	}

	/**
	 * 获取今天是本月的第几天
	 * 
	 * @return
	 */
	public static int getDayOffsetOfMonth() {
		Calendar c = Calendar.getInstance();
		int datenum = c.get(Calendar.DATE);
		return datenum;
	}

	public static Date strToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToStr(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}


	/**包含了大部分的日期格式*/
	public static final Map<String, String> dateRegFormat = new HashMap<String, String>();
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

	private static final ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			return calendar;
		}
	};
	public static Calendar getCalendar(Date date) {
		Calendar calendar = calendarThreadLocal.get();
		calendar.setTime(date);
		return calendar;
	}


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
	 * 判断日期格式数据是否不为null
	 *
	 * @param date java.util.Date 输入时间
	 * @return boolean 如果date值为null，返回false，否则返回true
	 */
	public static boolean isNotNull(Date date) {
		return !isNull(date);
	}


	/**
	 * 得到系统的当前时间
	 *
	 * @return java.util.Date 返回当前系统时间
	 */
	public static Date getCurrentDate(){
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
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
		return calendar.getTime();
	}

	/**
	 * 根据传入时间获取当月的第一天
	 *
	 * @param date java.util.Date 传入日期
	 * @return java.util.Date 返回当月的第一天
	 */
	public static Date getFirstDayForCurrMonth(Date date){
		SimpleDateFormat df = new SimpleDateFormat(StringUtils.DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		try {
			return df.parse(df.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据传入时间获取当月的最后一天
	 *
	 * @param date java.util.Date 传入日期
	 * @return java.util.Date 返回当月的最后一天
	 */
	public static Date getLastDayForCurrMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期的本周第一天
	 * @param date 传入时期
	 * @return date
	 */
	public static  Date getStartDayOfWeek(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return getStartDayOfWeek(localDateTime.toLocalDate());
	}

	public static Date getStartDayOfWeek(TemporalAccessor date) {
		TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
		LocalDate localDate = LocalDate.from(date);
		localDate.with(fieldISO, 1);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getEndDayOfWeek(TemporalAccessor date) {
		TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
		LocalDate localDate = LocalDate.from(date);
		localDate.with(fieldISO, 7);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
	}

	/**
	 * 获取当前日期的本周最后一天
	 * @param date 传入时期
	 * @return date
	 */
	public static Date getEndDayOfWeek(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return getEndDayOfWeek(localDateTime.toLocalDate());
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
	public static boolean isBetween(Date date, String startDate, String endDate) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat(StringUtils.DATE_TIME_DEFAULT);
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

	public static String getNowDateToString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getDateTimeToString(Date date) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=f.format(date);
		return startTime;
	}
	/**字符串转为日期时间，只接受形如 1985-12-26 23:23:31格式*/
	public static String dateToStringTime(Date date){
		String dt2 =new SimpleDateFormat("HHmmss").format(date);
		return dt2;
	}
	/**字符串转为日期时间，只接受形如 1985-12-26 23:23:31格式*/
	public static Date str2DateTime(String dateStr){
		DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dateStr);
		return dt2.toDate();
	}
	public static Date str2DateTime(String dateStr,String pattern){
		DateTime dt2 = DateTimeFormat.forPattern(pattern).parseDateTime(dateStr);
		return dt2.toDate();
	}
	/**字符串转为日期，只接受形如 1985-12-26格式*/
	public static Date str2Date(String dateStr){
		DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(dateStr);
		return dt2.toDate();
	}

	/**各种字符串日期格式的格式化*/
	public static Date allStr2Date(String dateStr) throws ParseException {
		String curDate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//DateFormat formatter1 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat formatter2;
		String dateReplace;
		// String strSuccess="";
		if(StringUtils.isEmpty(dateStr)){
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





	/**两个日期之间相差多少天 date2-date1*/
	public static int daysBetween(Date date1,Date date2){
		int i= Days.daysBetween(new DateTime(DateUtil.turnToDateStart(date1)), new DateTime(DateUtil.turnToDateStart(date2))).getDays();
		// int i= Weeks.weeksBetween(new DateTime(date1), new DateTime(date2)).getWeeks();
		return i;
	}
	/**两个日期之间相差多少分钟date2-date1*/
	public static int minuteBetween(Date date1,Date date2){
		int i= Minutes.minutesBetween(new DateTime(date1), new DateTime(date2)).getMinutes();
		// int i= Weeks.weeksBetween(new DateTime(date1), new DateTime(date2)).getWeeks();
		return i;
	}

	/**两个日期之间相差多少秒date2-date1*/
	public static int secondsBetween(Date date1,Date date2){
		int i= Seconds.secondsBetween(new DateTime(date1), new DateTime(date2)).getSeconds();
		return i;
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
	public static String DateTimeToString(Date date) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startTime=f.format(date);
		return startTime;
	}
	public static String DateToString(Date date) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=f.format(date);
		return startTime;
	}
	public static String DateToStringYMd(Date date) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		String startTime=f.format(date);
		return startTime;
	}

	/**
	 * 年月日时分秒yyyyMMddHHmmss
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String DateToStringYMDHMS(Date date) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String startTime=f.format(date);
		return startTime;
	}

	/**
	 * 获取某年第一天日期
	 * @param year
	 * @return
	 */
	public static Date getTheFirstDayOfYear(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 一年的最后一天
	 * @param year
	 * @return
	 */
	public static Date getTheLastDayOfYear(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return turnToDateEnd(currYearLast);
	}
	/** 获取指定时间的年份 */
	public static int getFullYear(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	/** 获取指定时间的月份 */
	public static int getMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH)+1;
	}

}
