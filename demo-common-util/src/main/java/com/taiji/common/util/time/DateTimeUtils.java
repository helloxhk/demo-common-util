/**
 * 
 */
package com.taiji.common.util.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taiji.common.util.exception.SystemException;

/**
 * @title 日期/时间工具类
 * @description 提供有关日期/时间的常用静态操作方法
 * @author Lincoln
 */

public class DateTimeUtils {

	/**
	 * 日期格式:完整日期/时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat FULL_DATE_TIME_FORMAT_TAB = new SimpleDateFormat("yyyy-MM-dd	HH:mm:ss");

	public static SimpleDateFormat FULL_DATE_TIME_FORMAT_NUM = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static SimpleDateFormat SFULL_DATE_TIME_FORMAT_NUM = new SimpleDateFormat("yyMMddHHmmssSSS");
	
	/**
	 * 日期格式:完整日期/时间格式(yyyy-MM-dd)
	 */
	public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 日期格式:完整日期/时间格式(yyyy-MM-dd HH:mm:ss,S)
	 */
	public static SimpleDateFormat EXAC_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S");
	
	/**
	 * 日期格式:完整日期/时间格式(yyyyMMdd)
	 */
	public static SimpleDateFormat FULL_DATE_FORMAT_NUM = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 日期格式:完整时间/时间格式(HHmmss)
	 */
	public static SimpleDateFormat FULL_TIME_FORMAT_NUM = new SimpleDateFormat("HHmmss");
	
	/**
	 * 日期格式:完整时间/时间格式(yyMM)
	 */
	public static SimpleDateFormat YYMM_FORMAT_NUM=new SimpleDateFormat("yyMM");
	
	public static long dateSecond = 24*60*60*1000;
	
	/**
	 * 日期格式:完整时间/时间格式(yyMM)
	 */
	public static String getYYMM(){
		return YYMM_FORMAT_NUM.format(new Date());
	}
	
	/**
	 * 取得当前完整日期/时间字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public static String getCurrentFullDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	public static String getCurrentFullDateTimeByTab() {
		return FULL_DATE_TIME_FORMAT_TAB.format(new Date());
	}
	/**
	 * 将日期字符转换为 日期对象
	 * 
	 * @param dateStr(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static Date getToDateObject(String dateStr) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	/**
	 * 生成唯一序列 规则：当前时间+6位随机数(yyyyMMddHHmmss123345)
	 * @param table 表名(缩写) 例：uc - user_customer
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getSequence(String shortTableName) throws Exception {
		String currentDate = getCurrentDate("yyyyMMddHHmmssS");
		String random = random(8);
		return shortTableName + currentDate + random;
	}
	
	/**
	 * 获取当前时间
	 * @param formatStr 时间格式
	 * @return
	 * @throws Exception 
	 */
	public static String getCurrentDate(String formatStr) throws Exception{
		try{
			Date date=Calendar.getInstance().getTime();
			SimpleDateFormat sft=new SimpleDateFormat(formatStr);
			return sft.format(date);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 取得当前日期(yyyy-MM-dd)
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	
	
	/**
	 * 取得指定日期完整日期/时间字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static String getFullDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	/**
	 * 日期偏移
	 * 
	 * @param date 当前日期
	 * @param dayNum 偏移天数
	 * @return
	 */
	public static Date getDataTimeForword(Date date,int dayNum){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		return cal.getTime();
	}
	public static Date getDataTimeForword(Date date,int num,String type){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if("天".equals(type)){
		  cal.add(Calendar.DATE, num);
		}
		if("分钟".equals(type)){
		  cal.add(Calendar.MINUTE, num);
		}
		if("月".equals(type)){
			cal.add(Calendar.MONTH, num);
		}
		return cal.getTime();
	}
	
	public synchronized static Date getDataTimeForword(String date,int num,String type){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getToDateObject(date));
		if("天".equals(type)){
		  cal.add(Calendar.DATE, num);
		}
		if("分钟".equals(type)){
		  cal.add(Calendar.MINUTE, num);
		}
		if("月".equals(type)){
			cal.add(Calendar.MONTH, num);
		}
		return cal.getTime();
	}
	/**
	 * 取得当前精确时间("yyyy-MM-dd HH:mm:ss,S")
	 * 
	 * @return
	 */
	public static String getCurrentExacDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date());
	}

	/**
	 * 验证字符串是否符合(yyyy-MM-dd HH:mm:ss)格式
	 * 
	 * @param beginTime
	 * @return
	 */
	public static boolean validateStringForFullTime(String beginTime) {
		try {
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime);
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
	public static boolean validateStringForDate(String beginTime) {
		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(beginTime);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	public static String getCurrentYearMonth() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}
	
	public static String getCurrentDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	public static String getCurrentDateNum(){
		return new Date().getTime()+(new Random(System.currentTimeMillis()).nextInt()+"");
	}
	/**
	 * 求两个日期的天数之差
	 * 
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static int getOddDateNum(String beginTime,String endTime){
		Date dateBegin=getToDateObject(beginTime);
		System.out.println("dateBegin="+dateBegin);
		Date dateEnd =getToDateObject(endTime);
		System.out.println("dateEnd="+dateEnd);
		if((dateEnd.getTime()-dateBegin.getTime())<0){
			return -1;
		}
		int odd=(int)((dateEnd.getTime()-dateBegin.getTime())/(dateSecond));
		return odd;
	}
	
	/**
	 * 把时间范围转为list
	 * @param timeBegin
	 * @param timeEnd
	 * @return
	 * @throws ParseException 
	 */
	public static List<String> getTimeList(String timeBegin,String timeEnd) throws ParseException{
		List<String> timeList=new ArrayList<String>();
		
		//时间不正确
		if(timeBegin.compareTo(timeEnd)>0){
			return timeList;
		}
		
		//两时间相等
		if(timeBegin.compareTo(timeEnd)==0){
			timeList.add(timeBegin);
			return timeList;
		}
		
		Date timeBeginDate=DateTimeUtils.FULL_DATE_FORMAT.parse(timeBegin);
		Date timeEndDate=DateTimeUtils.FULL_DATE_FORMAT.parse(timeEnd);
		do{
			timeList.add(timeBegin);
			timeBeginDate=getDataTimeForword(timeBeginDate, 1, "天");
			timeBegin=DateTimeUtils.formateDateStr(timeBeginDate, "yyyy-MM-dd");
		}while(timeEndDate.after(timeBeginDate));
		
		timeList.add(timeBegin);
		return timeList;
	}
	
	/**
	 * 根据当前日期计算上月月初
	 */
	public static String getFirstDayOfLastMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.MONTH, -1); //上月
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1); //上月首天
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 根据某日期计算某月月初
	 * @param date（某时间）
	 * @param monthNum（该月前/后的某月：正则加，负则减）
	 * @param dayNum（该月的某天：1为该月首天）
	 */
	public static String getFirstDayOfLastMonth(Date date, int monthNum, int dayNum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, monthNum); 
		cal.set(GregorianCalendar.DAY_OF_MONTH, dayNum); 
		return sdf.format(cal.getTime());
	}
	
	public static String getFullDate(){
		return new SimpleDateFormat("yyyyMMdd").format(getDataTimeForword(new Date(),-1));
	}
	
	/**
	 * @return yyyyMMdd 获得今天日期
	 */
	public static String getFullDateToday(){
		return new SimpleDateFormat("yyyyMMdd").format(getDataTimeForword(new Date(),0));
	}
	
	/**
	 * 获取当前分钟
	 */
	public static String getCurrentMinutes(){
		return new SimpleDateFormat("mm").format(new Date());
	}
	
	/**
	 * 获取当前日期时间(yyyyMMddHHmm)
	 * @return
	 */
	public static String getFullDateTimeMinutesNum(){
		return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
	}
	
	/**
	 * 获取当前日期时间(yyyyMMddHHmmss)
	 * @return
	 */
	public static String getFullDateTimeNum(){
		return FULL_DATE_TIME_FORMAT_NUM.format(new Date());
	}
	
	/**
	 * 获取当前日期时间(yyMMddHHmmssSSS)
	 * @return
	 */
	public static String getSFullDateTimeNum(){
		return SFULL_DATE_TIME_FORMAT_NUM.format(new Date());
	}
	
	/**
	 * 获取当前时间(HH:mm:ss)
	 * @return
	 */
	public static String getFullTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前时间(HHmmss)
	 * @return
	 */
	public static String getFullTimeNum(){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}
	public static String getFullDate(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(getDataTimeForword(date,-1));
	}
	public static String gettringForVerifyAlipayDate(String beginTime) {
		try {
			return getFullDate(new SimpleDateFormat("yyyy-MM-dd").parse(beginTime));
		} catch (ParseException e) {
			return "";
		}
	}
	public static boolean validateAlipayStringForDate(String beginTime) {
		try {
			new SimpleDateFormat("yyyyMMdd").parse(beginTime);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	/**
	 * 根据当前日期计算上月月末
	 */
	public static String getLastDayOfLastMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1); //当月首天
		cal.add(GregorianCalendar.DAY_OF_MONTH, -1); //上月末天
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 根据某日期计算某月月末
	 * @param date（某时间）
	 * @param monthNum（该月的某天：1为第一天）
	 * @param dayNum（该天前/后的某天：-1为该天前/后的某天）
	 * @return
	 */
	public static String getLastDayOfLastMonth(Date date, int monthNum, int dayNum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, monthNum); 
		cal.add(GregorianCalendar.DAY_OF_MONTH, dayNum); 
		return sdf.format(cal.getTime());
	}
	
	public static void main(String[] args){
//		int odd=DateTimeUtils.getOddDateNum("2017-03-17 00:00:00","2017-04-17 12:00:00");
//		System.out.println("--int---"+odd);
//		String endTime=DateTimeUtils.getCurrentFullDateTime();
//		String beginTime=DateTimeUtils.getFullDateTime(DateTimeUtils.getDataTimeForword(new Date(), -30));
//		System.out.println("begintime:"+beginTime);
//		System.out.println("endTime:"+endTime);
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		System.out.println(timestampToFullDateTimeStr(timestamp));
		
		String umeJoinEndDate = DateTimeUtils.getCurrentDate();
		Date date01 = DateTimeUtils.getDataTimeForword(umeJoinEndDate, 2, "月");
		System.out.println(getFirstDayOfLastMonth(date01, 0, 1));
		
		Date date02 = DateTimeUtils.getDataTimeForword(umeJoinEndDate, 3, "月");
		System.out.println(getLastDayOfLastMonth(date02, 1, -1));
		
		System.out.println("完整的日期："+getFullDateToday());
		
		System.out.println("yyyy："+getBackDay(2));
		System.out.println("=============="+getNextDay("2017-04-17 18:24:20",1));
		
//		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date time = FULL_DATE_TIME_FORMAT_NUM.parse("20160321160233");
			String timeStr=FULL_DATE_TIME_FORMAT.format(time);
			System.out.println("格式转化之后的时间："+timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			getTimeList("2017-04-01", "2017-04-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("yyyyyyyyyyyyyyyyyyyyyy");
		System.out.println(getYYMM());
		
	}
	
	public static String formateDateStr(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 得到时间字符串的时间戳(字符串格式:yyyy-MM-dd HH:mm:ss)
	 * @throws  SystemException 格式化dataStr失败
	 */
	public static Long getCurrentTimeMillis(String dataStr){
		try {
			return FULL_DATE_TIME_FORMAT.parse(dataStr).getTime();
		} catch (ParseException e) { 
			throw new SystemException("格式化["+dataStr+"]失败",e);
		}
	}
	
	/**
	 * 转换格式
	 * 
	 * @param dateStr
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 */
	public static String convertDateTimeFormat(String dateStr,String oldFormat,String newFormat){
		String newDate = null;
		SimpleDateFormat newSimple = new SimpleDateFormat(newFormat);
		SimpleDateFormat oldSimple = new SimpleDateFormat(oldFormat);
		try {
			Date date = oldSimple.parse(dateStr);
			newDate = newSimple.format(date);
			return newDate;
		} catch (ParseException e) {
			throw new SystemException("格式转换出错,dateStr="+dateStr+",oldFormat="+oldFormat+",newFormat="+newFormat,e);
		}
	}
	/** 判断 当前日期时间  前推24小时 
	 * 
	 * @return
	 */
	public synchronized static String getYesDForFullTime() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.DATE, -1); 
		return sdf.format(cal.getTime())+" "+DateTimeUtils.getFullTime();
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
	/**	 * 判断日期格式:yyyy-mm-dd	 * 	 * @param sDate	 * @return	 */	
	public static boolean isValidDate(String sDate) {		
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";		
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"				
			+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"				
			+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"				
			+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("				
			+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"				
			+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";		
		if ((sDate != null)) {			
			Pattern pattern = Pattern.compile(datePattern1);			
			Matcher match = pattern.matcher(sDate);			
			if (match.matches()) {				
				pattern = Pattern.compile(datePattern2);				
				match = pattern.matcher(sDate);				
				return match.matches();			
		} else {				
			return false;			
			}		
		}		
		return false;	
		}
	/**
	 * 计算时间差
	 * @param beginTime
	 * @param endTime
	 * @param format
	 * @param type （day、hour、min、sec）
	 * @return
	 */
	public static Long dateCompare(String beginTime, String endTime, String format, String type) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long result = 0L;
		
		try {
			//获得两个时间的毫秒时间差异
			result = sd.parse(endTime).getTime() - sd.parse(beginTime).getTime();
			
			if(type.equals("day")){
				result = result/nd;//计算差多少天
			}else if(type.equals("hour")){
				result = result%nd/nh;//计算差多少小时
			}else if(type.equals("min")){
				result = result%nd%nh/nm;//计算差多少分钟
			}else if(type.equals("sec")){
				result = result%nd%nh%nm/ns;//计算差多少秒
			}
		} catch (ParseException e) {
			e.printStackTrace();
			}
		return result;
	}
	/** 判断 当前日期时间 前推小n天
	 * @return
	 */
	public synchronized static String getBackDay(int settleCycle) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.DATE, -settleCycle); 
		return sdf.format(cal.getTime());
	}
	/**
	 * 判断指定时间的前一天
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {		
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(date);		
		calendar.add(Calendar.DAY_OF_MONTH, -2);		
		date = calendar.getTime();		
		return date;	
	}
	/**
	 * 判断指定时间后推n天
	 * @param date
	 * @param settleCycle
	 * @return
	 */
	public static String getNextDay(String date,int settleCycle) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(getToDateObject(date));		
		calendar.add(Calendar.DAY_OF_MONTH, settleCycle);		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 当天开始时间
	 */
	public static Date getStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
	
	
	/**
	 * 当天结束时间
	 * @return
	 */
	public static Date getEndTime() {  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    }  
	
	/**
	 * 获取当天时间的时间差
	 * @throws ParseException 
	 */
	public static long getTimeSeconds() throws ParseException {
		String endDate = DateTimeUtils.getCurrentDate()+" 23:59:59";
		
		Date parse = FULL_DATE_TIME_FORMAT.parse(endDate);
		
		long starttime = new Date().getTime();
		long endtime = parse.getTime();
		long time = ((endtime - starttime) / 1000);
		return time;
	}
	
	/**
	 * 获取N位随机数
	 * @param n
	 * @return
	 */
	public static String random(int n) {
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n
					+ " bit number");
		}
		Random ran = new Random();
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		String tmp_s = "";
		for (int i = 0; i < n; i++) {
			tmp_s += ran.nextInt(10);
		}
		return new String(tmp_s);
	}
	
}

