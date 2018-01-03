package com.taiji.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.SystemException;

/**
 * 系统工具包
 * 
 */
public class EstockUtilTools implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5154137583561229105L;
	
	/**
	 * 日期格式:完整日期/时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期格式:完整日期/时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static String FULL_DATE_TIME_FORMAT_NUM = "yyyyMMddHHmmss";
	
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
	 * 获取当前时间：格式yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDate() throws Exception{
		
		return getCurrentDate(FULL_DATE_TIME_FORMAT);
	}
	
	/**
	 * 获取当前时间：格式yyyyMMddHHmmss
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDate2() throws Exception{
		
		return getCurrentDate(FULL_DATE_TIME_FORMAT_NUM);
	}

	/**
	 * 将日期字符转换为 日期对象
	 * 
	 * @param dateStr(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static Date getToDateObject(String dateStr) throws Exception{
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 将日期字符转换为某格式的日期对象
	 * @param dateStr
	 * @param convertDate
	 * @return
	 * @throws Exception
	 */
	public static Date getToDateObject(String dateStr, String convertDate) throws Exception{
		try {
			return new SimpleDateFormat(convertDate).parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	/**
	 * 格式化时间
	 * 
	 * @param strDate
	 *            时间
	 * @param convertDate
	 *            原时间格式
	 * @param targetPattern
	 *            转换时间格式
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static String convertStringToDate(String convertDate,String srcParrern, String targetPattern) throws Exception {
		String res = "";
		try {
			res = new SimpleDateFormat(targetPattern).format(new SimpleDateFormat(srcParrern).parse(convertDate));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Exception("时间格式转换异常!" + e);
		}
		return res;
	}
	
	/**
	 * 比较两个时间大小
	 * @return (1：大于；0：等于；-1：小于)
	 * @throws Exception 
	 */
	public static int compareDate(String date1,String date1Format,String date2,String date2Format) throws Exception{
		int flag = 0;
		try {
			Date d1=getToDateObject(date1, date1Format);
			Date d2=getToDateObject(date2, date2Format);
			if(d1.getTime()-d2.getTime() > 0){
				flag = 1;
			}else if(d1.getTime()-d2.getTime() < 0){
				flag = -1;
			}else{
				flag = 0;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return flag;
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
	
	/**
	 * 判断String 值 不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (null != str && !"".equals(str))
			return true;
		else
			return false;
	}
	/**
	 * 判断String 值 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return !isNotNull(str);
	}
	
	/**
	 * 发送短信验证码
	 * 注：依赖于易淘客短信渠道实现
	 * @return 
	 */
	public static String sendSms(){
		
		return "1234";
	}
	
	/**
	 * 密码加密
	 * @param password
	 * @return
	 */
	public static String encrypt(String password,String telephone){
		
		return password;
	}
	/**
	 * 密码解密
	 * @param password
	 * @return
	 */
	public static String decrypt(String password,String telephone){
		
		return password;
	}
	
	/**
	 * md5签名
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String Md5Sign(String str,String charset){
		try {
			
			byte [] strByte=str.getBytes(charset);
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte [] result=md.digest(strByte);
			
			return hexDump(result).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String hexDump(byte bytes[])
    {
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < bytes.length; i++)
            buf.append(hexDump(bytes[i]));

        return buf.toString();
    }
	
	public static String hexDump(byte b)
    {
        int i = b & 255;
        int h = i >> 4;
        int l = i - (h << 4);
        char ch[] = new char[2];
        ch[0] = Character.toUpperCase(Character.forDigit(h, 16));
        ch[1] = Character.toUpperCase(Character.forDigit(l, 16));
        return new String(ch);
    }
	

	/**
	 * 验证是否为数字
	 * @param str
	 * @return （true：是,false：否）
	 */
	public static boolean isNumber(String str) {
		if(str != null){
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher match = pattern.matcher(str);
			if (match.matches() == false) {
				return false;
			} else {
				return true;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 验证是否为合法的手机号码
	 * @param phone
	 * @return
	 */
	public static boolean isMobilePhone(String phone){
		if(isNotNull(phone)){
			Pattern pattern=Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
			Matcher match=pattern.matcher(phone);
			if(match.matches()){
				return true;
			}
		}
		return false;
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
	 * 判断 当前日期时间 前推小n天/分钟/月
	 * @param date 当前时间
	 * @param num （正-后）/（负-前）推数
	 * @param type 前推类型
	 * @return
	 */
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
	
	/**
	 * 得到时间字符串的时间戳(字符串格式:yyyy-MM-dd HH:mm:ss)
	 * @throws Exception 
	 * @throws  SystemException 格式化dataStr失败
	 */
	public static Long getCurrentTimeMillis(String dataStr) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(dataStr).getTime();
		} catch (ParseException e) { 
			throw new Exception("格式化["+dataStr+"]失败",e);
		}
	}
	
	
	public static boolean isNotNullList(List list){
		
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNullList(List list){
		
		return !isNotNullList(list);
	}
	
	/**
	 * 右补字符
	 * @param str 需要补的原字符串
	 * @param length 补足的长度
	 * @param subStr 右补字符
	 * @return
	 */
	public static String rightFillStr(String str, int length, String subStr) {
		if (isNull(str))
			return null;
		int count = length - str.length();
		for (int i = 0; i < count; i++) {
			str = str + subStr;
		}
		return str;
	}

	/**
	 * 左补字符
	 * @param str 需要补的原字符串
	 * @param length 补足的长度
	 * @param subStr 左补字符
	 * @return
	 */
	public static String leftFillStr(String str, int length, String subStr) {
		if (isNull(str))
			return null;
		int count = length - str.length();
		for (int i = 0; i < count; i++) {
			str = subStr + str;
		}
		return str;
	}
	
	/**
	 * 获取规则编码（from系统号+to系统号+常规流水号（时间戳yyMMddHHmmssS+radom[7]））
	 * @param fromSysId -from系统号
	 * @param toSysId -to系统号
	 * @return
	 */
	public static String getCode(String fromSysId,String toSysId){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssS");
		String str = sdf.format(date);
		return fromSysId+toSysId+str+EstockUtilTools.random(7);
	}
	
	public static void main(String[] args) throws Exception {
		
		
		System.out.println(isMobilePhone("1234322345"));
		System.out.println("是不是纯数字："+isNumber("6970043430901XXX"));
	}
}
