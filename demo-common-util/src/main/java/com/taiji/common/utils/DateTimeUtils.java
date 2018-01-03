package com.taiji.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 日期/时间工具类
 * @author wanjingjing
 *
 */
public class DateTimeUtils {
	private static Logger log = Logger.getLogger(DateTimeUtils.class);

	/**
	 * 日期格式:完整时间格式
	 */
	public static SimpleDateFormat FULL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 日期格式:完整日期/时间格式
	 */
	public static SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat FULL_DATE_TIME_NUMFORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 支付宝专用tab分隔
	 */
	public static SimpleDateFormat FULL_DATE_TIME_FORMAT_BYTAB = new SimpleDateFormat("yyyy-MM-dd	HH:mm:ss");

	/**
	 * 日期格式:完整日期/时间格式
	 */
	public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 日期格式:完整日期/时间格式
	 */
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 日期格式:完整日期/时间格式
	 */
	public static SimpleDateFormat EXAC_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S");

	public static long dateSecond = 24 * 60 * 60 * 1000;

	/**
	 * 获取当前完整的时间字符串(HH:mm:ss)
	 * 
	 * @return
	 */
	public synchronized static String getCurrentFullTime() {
		return FULL_TIME_FORMAT.format(new Date());
	}

	/**
	 * 取得当前完整日期/时间字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public synchronized static String getCurrentFullDateTime() {
		return FULL_DATE_TIME_FORMAT.format(new Date());
	}
	/**
	 * 取得当前完整日期/时间字符串(yyyyMMddHHmmss)
	 * 
	 * @return
	 */
	public synchronized static String getCurrentFullDateNumTime() {
		return FULL_DATE_TIME_NUMFORMAT.format(new Date());
	}

	public synchronized static String getCurrentFullDateTimeBYTAB() {
		return FULL_DATE_TIME_FORMAT_BYTAB.format(new Date());
	}

	public synchronized static String getCurrentDateTime() {
		return DATE_FORMAT.format(new Date());
	}

	/**
	 * 将日期字符转换为 日期对象
	 * 
	 * @param dateStr
	 *            (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static Date getToDateObject(String dateStr) {
		try {
			SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return FULL_DATE_TIME_FORMAT.parse(dateStr);
		} catch (Exception e) {
			log.error("解析["+dateStr+"]失败" ,e);
		}
		return null;
	}

	/**
	 * 取得指定日期完整日期/时间字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public synchronized static String getFullDateTime(Date date) {
		return FULL_DATE_TIME_FORMAT.format(date);
	}

	/**
	 * 取得当前精确时间("yyyy-MM-dd HH:mm:ss,S")
	 * 
	 * @return
	 */
	public synchronized static String getCurrentExacDateTime() {
		return EXAC_DATE_TIME_FORMAT.format(new Date());
	}

	/**
	 * 验证字符串是否符合(yyyy-MM-dd HH:mm:ss)格式
	 * 
	 * @param beginTime
	 * @return
	 */
	public synchronized static boolean validateStringForFullTime(String beginTime) {
		try {
			FULL_DATE_TIME_FORMAT.parse(beginTime);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 验证字符串是否符合(yyyy-MM-dd)格式
	 * 
	 * @param beginTime
	 * @return
	 */
	public synchronized static boolean validateStringForDate(String beginTime) {
		try {
			FULL_DATE_FORMAT.parse(beginTime);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 取得当前日期(yyyy-MM-dd)
	 * 
	 * @return
	 */
	public synchronized static String getCurrentDate() {
		return FULL_DATE_FORMAT.format(new Date());
	}

	public synchronized static String getCurrentDate(Date date) {
		return FULL_DATE_FORMAT.format(date);
	}

	public synchronized static String getCurrentDateNum() {
		return new Date().getTime()	+ (new Random(System.currentTimeMillis()).nextInt() + "");
	}

	/**
	 * 将日期转换成指定格式
	 * @param date
	 * @param format
	 * @return
	 */
	public synchronized static String getDateByFormat(Date date,String format){
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(date);
	}
	
	public synchronized static String convertDateByFormat(String format, String oldFormat,String dateStr) {
		SimpleDateFormat simple1 = new SimpleDateFormat(format);
		SimpleDateFormat simple2 = new SimpleDateFormat(oldFormat);
		String newDate = null;
		try {
			Date date = simple2.parse(dateStr);
			newDate = simple1.format(date);
			return newDate;
		} catch (ParseException e) {
			log.error("getDateByFormat" ,e);
		}
		return null;
	}

	public static String getCurrentExacDateTime2() {
		return new SimpleDateFormat("MMddHHmmss").format(new Date());
	}
	public static String getCurrentExacDateTime3() {
		return new SimpleDateFormat("mmss").format(new Date());
	}
	public static String getCurrentDateTimeWithOutSplit() {
		return new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
	}
	public static String getCurrentDateTimeWithOutSplit(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	/**
	 * 数据库timestamp类型转换为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param timestamp
	 * @return
	 */
	public synchronized static String timestampToFullDateTimeStr(Timestamp timestamp) { 
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp.getTime());
		return getFullDateTime(c.getTime());
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrentExacDateTime3());
		System.out.println(StringUtils.leftFillZero(DateTimeUtils.getCurrentExacDateTime3(), 6));
	}
}
