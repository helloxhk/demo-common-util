package com.taiji.common.util.string;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taiji.common.util.exception.SystemException;


/**
 * @title 字符串工具类
 * @description 提供操作字符串的常用工具方法
 * @author Lincoln
 */
public class StringUtils {

	/**
	 * 信息摘要工具[SHA-1]算法
	 */
	static MessageDigest messageDigestBySHA1;
	static {
		try {
			messageDigestBySHA1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new SystemException(e);
		}
	}
	static Map<String,String> weekName=new HashMap<String,String>();
    static{
    	weekName.put("2", "星期一");
    	weekName.put("3", "星期二");
    	weekName.put("4", "星期三");
    	weekName.put("5", "星期四");
    	weekName.put("6", "星期五");
    	weekName.put("7", "星期六");
    	weekName.put("1", "星期日");
    }
   
    /**
	 * 数组对象toString
	 * @param args
	 * @return
	 */
	public synchronized static String arrayToString(Object[] args){
		StringBuffer bf = new StringBuffer();
		if(args!=null){
			int i = 0;
			for(Object obj:args){
				if(i==0){
					bf.append(obj);
				}else{
					bf.append(","+obj);
				}
				i++;
			}
		}
		return bf.toString();
	}
    
	/**
	 * 将对象数组转换为可显字符串
	 * 
	 * @param objArr
	 * @return
	 */
	public static String toString(Object[] objArr) {
		if (objArr == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer("[");
		for (int i = 0; i < objArr.length; i++) {
			buf.append((i > 0 ? "," : "") + objArr[i]);
		}
		buf.append("]");
		return buf.toString();
	}
	/**
	 * 获取星期几的名称
	 * 
	 * @param str
	 * @return
	 */
	public static String getWeekName(String str){
		return weekName.get(str);
	}
	/**
	 * 将单个对象转换为可显字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj instanceof String) {
			return "\"" + obj + "\"";
		}
		if (obj instanceof Object[]) {
			return toString((Object[]) obj);
		} else {
			return String.valueOf(obj);
		}
	}

	/**
	 * 使用正则表达式验证字符串格式是否合法
	 * 
	 * @param piNoPattern
	 * @param str
	 * @return
	 */
	public static boolean patternValidate(String pattern, String str) {
		if (pattern == null || str == null) {
			throw new SystemException("参数格式不合法[patternValidate(String " + pattern + ", String " + str + ")]");
		}
		return Pattern.matches(pattern, str);
	}

	/**
	 * 验证字符串是否为空字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().equals("") || str.trim().toLowerCase().equals("null");
	}

	public static String[] trimToNull(String[] strArray){
		String[] tmpArray = null;
		if(strArray!=null && strArray.length!=0){
			tmpArray = new String[strArray.length];
			int i = 0;
			for(String s : strArray){
				tmpArray[i++] = trimToNull(s);
			}
		}
		return tmpArray;
	}
	
	/**
	 * 如果为空,将字符串转换为NULL
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToNull(String str) {
		String s = null;
		if (isBlank(str)) {
			return s;
		}
		s = str.trim();
		return s;
	}

	/**
	 * 如果为空,将字符串转换为""
	 * 
	 * @param str
	 * @return
	 */
	public synchronized static String trimToBlank(String str) {
		boolean boo = str == null || str.trim().equals("")|| str.trim().toLowerCase().equals("null");
		if (boo) {
			return "";
		}
		return str.trim();
	}
	
	/**
	 * 字符编码转换器
	 * 
	 * @param str
	 * @param newCharset
	 * @return
	 * @throws Exception
	 */
	public static String changeCharset(String str, String newCharset) throws Exception {
		if (str != null) {
			byte[] bs = str.getBytes();
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * 判断一个字符串是否为boolean信息
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBooleanStr(String str) {
		try {
			Boolean.parseBoolean(str);
			return true;
		} catch (Throwable t) {
			return false;

		}
	}
	
	/**
	 * 生成一个指定长度的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String createRandomStr(int length){
		Random random = new Random();
		String str = "";
		for(int i=0;i<length;i++){
			str += random.nextInt(9);
		}
		return str;
	}
	
	/**
	 * 把字节数组转换为base64编码(转换前先将bytes长度补足为3的倍数)
	 * 
	 * @param bytes
	 * @param charsetName
	 * @return
	 */
	public synchronized static String encodeBASE64Fill3(String content, String charsetName){
		String str = null;
		try{
			byte[] bytes = content.getBytes(charsetName);
			int length = bytes.length;
			if(length%3!=0){
				int k = 3-(length%3);
				byte[] tmp = new byte[length+k];
				System.arraycopy(bytes, 0, tmp, 0, length);
				int j = 0;
				for(int i=0;i<k;i++){
					j = length+i;
					tmp[j] = " ".getBytes()[0];
				}
				str = encodeBASE64(tmp,charsetName);
			}else{
				str = encodeBASE64(bytes,charsetName);
			}
			return str.replaceAll(System.getProperty("line.separator"), "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 把字节数组转换为base64编码
	 * 
	 * @param bytes
	 *            字节数组
	 * @param charsetName
	 *            字符集名称
	 * @return 转换后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static synchronized String encodeBASE64(byte[] bytes, String charsetName)throws UnsupportedEncodingException {
		byte[] data = Base64.encodeBase64(bytes);
		String s = charsetName == null ? new String(data) : new String(data,charsetName);
		return s;
	}

	/**
	 * 解base64编码的字符串
	 * 
	 * @param content
	 * @param charsetName
	 * @return
	 */
	public synchronized static String decodeBASE64Str(String content, String charsetName) {
		String dataStr = null;
		try{
			byte[] bytes = charsetName == null ? content.getBytes() : content.getBytes(charsetName);
			byte[] datas = Base64.decodeBase64(bytes);
			dataStr = charsetName == null ? new String(datas) : new String(datas,charsetName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataStr;
	}
	
	/**
	 * 将base64编码的字符串转为字节数组
	 * 
	 * @param text
	 *            字符串
	 * @param charsetName
	 *            字符集名称
	 * @return 字节数组
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] decodeBASE64(String content, String charsetName)throws UnsupportedEncodingException {
		byte[] bytes = charsetName == null ? content.getBytes() : content.getBytes(charsetName);
		return Base64.decodeBase64(bytes);
	}
	
	/**
	 * 将字节数组转化为十六进制字符串表示
	 * 
	 * @param bytes
	 * @return
	 */
	public static final String bytes2Hex(byte[] bytes) {
		char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append(bcdLookup[(bytes[i] >>> 4) & 0x0f]);
			sb.append(bcdLookup[bytes[i] & 0x0f]);
		}
		
		return sb.toString();
	}
	
	/**
	 * 将十六进制字符串转化为字节数组表示
	 * 
	 * @param s
	 * @return
	 */
	public static final byte[] hex2Bytes(String s) {
		byte[] bytes = new byte[s.length() / 2];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),16);
		}
		
		return bytes;
	}
	
	/**
	 * 左补零方法
	 */
	public static String leftFillZero(String str, int length) {
		if (str == null)
			return null;
		int count = length - str.length();
		for (int i = 0; i < count; i++) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * 前补空格
	 */
	public static String leftFillSpace(String str, int length){
		if(str == null){
			return null;
		}
		int count = length - str.length();
		for(int i=0;i<count;i++){
			str = " " + str;
		}
		return str;
	}
	
	public static String getString(String str) {
		return str.replaceAll(new String(new byte[]{0x00}), "");
	}
	
	/**
	 * 验证str是否在array数组中
	 * @param array
	 * @param str
	 * @return
	 */
	public static boolean valiArgInArray(Object[] array,Object str){
		if(array!=null&&array.length>0){
			if(!array[0].getClass().equals(str.getClass())){
				return false;
			}
			for(Object obj:array){
				if(str.equals(obj)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * request.getParameterMap to Map
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public synchronized static Map<String, String> servletParameterMapToMap(Map paramMap){
		Map<String, String> map = new HashMap<String, String>();
		if(paramMap!=null){
			Iterator entrys = paramMap.entrySet().iterator();
			Map.Entry entry;
			String key = null;
			String value = null;
			while(entrys.hasNext()){
				entry = (Map.Entry)entrys.next();
				
				key = (String)entry.getKey();
				Object valueObj = entry.getValue();
				if(valueObj == null){
					value = "";
				}else if(valueObj instanceof String[]){
					String values[] = (String[])valueObj;
					for(String tval:values){
						value = tval + ",";
					}
					value = value.substring(0,value.length()-1);
				}else{
					value = valueObj.toString();
				}
				map.put(key, value);
			}
		}
		return map;
	}
	/** 
	 * 读取Excel表Cell值（String/int/date） 转 String
	 */
	public static String getCellValue(HSSFCell cell){
		String value = "";
		if(cell == null){
			return value;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#");
		int type = cell.getCellType();
		switch (type) {
			case HSSFCell.CELL_TYPE_STRING : 
				value = cell.getStringCellValue(); 
				break;
			case HSSFCell.CELL_TYPE_NUMERIC : 
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())); 
				} else {
					value = df.format(cell.getNumericCellValue());
				}
				break;
		}
		return value;
	}
	
	/** 
	 * 读取Excel模板文件 
	 */
	public synchronized static HSSFWorkbook readTemplateFile(String fileName) {
		//模板全路径
		String fullName = fileName;
		//读取模板文件
		FileInputStream in = null;
		HSSFWorkbook workbook = null;
		//文件存在校验
		try {
			in = new FileInputStream(fullName);
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			workbook = new HSSFWorkbook(pfs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 结果返回
		return workbook;
	}
	
	/** 
	 * 生成导出的报表文件名
	 */
	public synchronized static String getFileFullName(){
		//毫秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmmSSS");
		String fileName = sdf.format(new Date());
		//随机数生成
		double random = Math.random();
		int rand = (int)(random*10000);
		//文件全名返回
		return fileName + rand + ".xlt";
	}
	
	/** 
	 * 生成导出的报表文件名
	 * @param suffix 文件名后缀
	 * @return
	 */
	public synchronized static String getFileFullName(String suffix){
		//毫秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmmSSS");
		String fileName = sdf.format(new Date());
		//随机数生成
		double random = Math.random();
		int rand = (int)(random*10000);
		//文件全名返回
		return fileName + rand + suffix;
	}
	
	/**
	 * String to Map
	 * @param mapStr key=value&key=value
	 * @return
	 */
	public synchronized static Map<String,String> stringToMap(String mapStr){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] mapStrs = mapStr.split("&");
		for(String kvstr : mapStrs){
			String[] kvs = kvstr.split("=");
			if(kvs.length!=2){
				continue;
			}
			if(kvs[0]==null||kvs[0].equals("")){
				continue;
			}
			map.put(kvs[0], kvs[1]);
		}
		return map;
	}
	
	/**
	 * 去掉小数位后无意义的0
	 * @param amount
	 * @return
	 */
	public static String subPointZero(String amount){
		if(amount.indexOf(".") > 0){
		 	  amount = amount.replaceAll("0+?$", "");//去掉后面无用的零
	          int pointIndex = amount.indexOf(".");
	           if(pointIndex == amount.length()-1){
	        	   amount = amount.substring(0, pointIndex);
	           }
	     }
		return amount;
	}
	
	/**
	 * 获取签名原串
	 * 格式a=1&b=1...
	 * @param treeMap
	 * @return
	 */
	public static String getSignSrcStr(TreeMap<String, String> treeMap){
		StringBuffer sb=new StringBuffer();
		Iterator<String> ite=treeMap.keySet().iterator();
		while(ite.hasNext()){
			String key=ite.next();
			String value=(String)treeMap.get(key);
			if(!StringUtils.isBlank(value)){
				sb.append(key+"="+value+"&");
			}
		}
		String signSrcStr=sb.toString().substring(0, sb.toString().length()-1);
		return signSrcStr;
	}
	
	/**
	 * 解析响应josn串
	 * @param resText
	 * @return
	 * @throws Exception
	 */
	public static TreeMap<String,Object> parseJosnStrToTreeMap(String jsonStr){
		// 将json字符串转成json对象  
	    JSONObject jsonObject = JSON.parseObject(jsonStr);
	    Iterator<String> iter = jsonObject.keySet().iterator();  
	    TreeMap<String,Object> treeMapp = new TreeMap<String,Object>();  
	    while (iter.hasNext()) {  
		    String key = (String) iter.next();  
		    String value = jsonObject.getString(key);  
		    treeMapp.put(key, value);  
	     }  
		return treeMapp;
	}
	
	public static void main(String[] args) {
//		System.out.println(patternValidate("[0-9]{15,18}", "12345678912345"));
//		System.out.println(patternValidate("[0-9]{15,18}", "123456789123456"));
//		System.out.println(patternValidate("[0-9]{15,18}", "123456789123456789"));
//		System.out.println(patternValidate("[0-9]{15,18}", "1234567891234567890"));
		
//		String jsonString = "{\"alipay_trade_precreate_response\":{\"code\":\"10000\",\"out_trade_no\":\"6141161365682511\",\"msg\":\"Success\"},"
//     			+ "\"sign\":\"VrgnnGgRMNApB1QlNJimiOt5ocGn4a4pbXjdoqjHtnYMWPYGX9AS0ELt8YikVAl6LPfsD7hjSyGWGjwaAYJjzH1MH7B2/T3He0kLezuWHsikao2ktCjTrX0tmUfoMUBCxKGGuDHtmasQi4yAoDk+ux7og1J5tL49yWiiwgaJoBE=\"}";
		
		String jsonString = "{\"alipay_trade_query_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"buyer_logon_id\":\"kfd***@sandbox.com\",\"buyer_pay_amount\":\"0.00\",\"buyer_user_id\":\"2088102168833392\",\"invoice_amount\":\"0.00\",\"open_id\":\"20881077225929578512946740716139\",\"out_trade_no\":\"K01201612161728530012240414\",\"point_amount\":\"0.00\",\"receipt_amount\":\"0.00\",\"send_pay_date\":\"2016-12-16 17:28:54\",\"total_amount\":\"0.01\",\"trade_no\":\"2016121621001004390200576202\",\"trade_status\":\"TRADE_CLOSED\"},\"sign\":\"lJD7ov43sGwAUFaFlGLJqFlIn9KZFHh/pCMF6saVHy2b2SRxZ6+XDgu297QowG3cfTncmQSubpbP7GG5HLUgUeoI8h2EO0kimWaXTrSTT65HPnfZC5EfIkqOMAMMlL+MvJu/nzHT/gB9bzwTr66wckj7l+7/SD2OA65UDmEfV7s=\"}";
		
//		System.out.println(parseJosnStrToTreeMap(jsonString));
//		System.out.println(parseJosnStrToMap(jsonString));
//		JSONObject jsonObj = new JSONObject(true);
//		JsonBean jsonBean = jsonObj.parseObject(jsonString, JsonBean.class);
//		System.out.println(jsonBean.getAlipay_trade_query_response());
//		JSONObject jsonObject =JSON.parseObject(jsonString,Feature.OrderedField);
//		System.out.println(JSONArray.parse(jsonString, JSONArray.DEFAULT_GENERATE_FEATURE));
//		System.out.println("jsonObject = "+jsonBean2.getAlipay_trade_query_response());
//		System.out.println(jsonString.substring(jsonString.indexOf("\"alipay_trade_query_response\":")+"\"alipay_trade_query_response\":".length(),jsonString.indexOf(",\"sign\"")));
		
		jsonString = jsonString.substring(jsonString.indexOf("\"alipay_trade_query_response_value\":")+"\"alipay_trade_query_response_value\":".length(),jsonString.indexOf(",\"sign\""));
		System.out.println(jsonString);
	}
}
