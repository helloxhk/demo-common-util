package com.taiji.common.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.kalian.thirdchannel.commons.exception.BizException;
import com.kalian.thirdchannel.commons.utils.MyXMLConfigParser;

public class CommonsConfig
{
	public final static String REF_FILE = "/commons.xml" ;
	
	public final static String ENCODING_GBK = "GBK" ;
	
	public final static String ENCODING_UTF8 = "UTF-8" ;
	
	public final static String CRLF = System.getProperty("line.separator") ;
	
	protected static Logger log = Logger.getLogger(CommonsConfig.class) ;
	
	private static String clsdir = null ;
	static
	{
		try{
			URL url = CommonsConfig.class.getResource(REF_FILE) ;
			clsdir = new File(url.getPath()).getParentFile().getCanonicalPath();
			System.out.println("系统参考目录:"+clsdir);
		}catch(Exception e){
			throw new BizException("获取classes路径失败" ,e) ;
		}
	}
	
	protected static Properties conf = null ;
	static
	{
		System.out.println("加载配置["+REF_FILE+"]...");
		conf = MyXMLConfigParser.parse(REF_FILE) ;
		System.out.println("成功加载配置["+REF_FILE+"]");
	}
	
	protected CommonsConfig(){
	}
	
	public static File getFileByClsdir(String filepath)
	{
		if (filepath == null || filepath.trim().length() == 0)
		{
			System.out.println("filepath["+filepath+"] invalid");
			return null ;
		}
		
		return new File(clsdir ,filepath) ;
	}
	
	public static String getFilepathByClsdir(String filepath)
	{
		return getFilepathByClsdir(filepath ,false) ;
	}
	
	public static String getFilepathByClsdir(String filepath ,boolean create)
	{
		File f = getFileByClsdir(filepath) ;
		if (f == null){
			return null ;
		}
		if (!f.exists()){
			if (create)
			{
				try{
					f.createNewFile();
				}catch(Exception e){
					log.error("新建文件["+f+"]失败");
					return null ;
				}
			}
			else
			{
				System.out.println("filepath["+f+"] !exists");
				return null ;
			}
		}
		
		return f.getAbsolutePath() ;
	}
	
	/**
	 * e.g. clsFile=/xx.xml<br/>
	 * e.g. clsFile=/xx.properties
	 */
	public static InputStream loadClsFileToStream(String clsFile)
	{
		InputStream in = CommonsConfig.class.getResourceAsStream(clsFile) ;
		if (in == null)
		{
			throw new BizException("没有发现配置文件"+clsFile);
		}
		
		return in ;
	}
	
	/**
	 * e.g. clsFile=/xx.xml<br/>
	 * e.g. clsFile=/xx.properties
	 */
	public static Properties loadClsFile(String clsFile)
	{
		Properties cfg = new Properties() ;
		
		InputStream in = loadClsFileToStream(clsFile) ;
		try{
			cfg.load(in) ;
			in.close() ;
		}catch(Exception e){
			throw new BizException("加载配置文件"+clsFile+"失败");
		}
		
		return cfg ;
	}
	
	public static void put(String key ,Object value)
	{
		if (value != null)
		{
			conf.setProperty(key, String.valueOf(value)) ;
		}
	}
	
	public static String get(String key)
	{
		return get(key ,null ,true) ;
	}
	
	public static String get(String key ,String def)
	{
		return get(key, def ,false) ;
	}
	
	private static String get(String key ,String def ,boolean flg)
	{
		String tmp = conf.getProperty(key) ;
		if (tmp == null)
		{
			println(key ,flg) ;
			return def ;
		}
		
		return tmp ;
	}
	
	public static boolean getBoolean(String key)
	{
		return getBoolean(key ,false ,true) ;
	}
	
	public static boolean getBoolean(String key ,boolean def)
	{
		return getBoolean(key ,def ,false) ;
	}
	
	private static boolean getBoolean(String key ,boolean def ,boolean flg)
	{
		String tmp = conf.getProperty(key) ;
		if (tmp == null)
		{
			println(key ,flg) ;
			return def ;
		}
		
		tmp = tmp.toLowerCase() ;
		if (tmp.equals("1") 
			|| tmp.equals("true") 
			|| tmp.equals("y"))
		{
			return true ;
		}
		
		return false ;
	}
	
	public static int getInt(String key)
	{
		return getInt(key ,0 ,true) ;
	}
	
	public static int getInt(String key ,int def)
	{
		return getInt(key ,def ,false) ;
	}
	
	private static int getInt(String key ,int def ,boolean flg)
	{
		String tmp = conf.getProperty(key) ;
		if (tmp == null)
		{
			println(key ,flg) ;
			return def ;
		}
		
		try{
			return Integer.parseInt(tmp) ;
		}catch(Exception e){
			log.warn("配置参数[Integer("+key+")]无效");
			return def ;
		}
	}
	
	public static long getLong(String key)
	{
		return getLong(key ,0 ,true) ;
	}
	
	public static long getLong(String key ,long def)
	{
		return getLong(key ,def ,false) ;
	}
	
	private static long getLong(String key ,long def ,boolean flg)
	{
		String tmp = conf.getProperty(key) ;
		if (tmp == null)
		{
			println(key ,flg) ;
			return def ;
		}
		
		try{
			return Long.parseLong(tmp) ;
		}catch(Exception e){
			log.warn("配置参数[Long("+key+")]无效");
			return def ;
		}
	}
	
	public static double getDouble(String key)
	{
		return getDouble(key ,0.00d ,true) ;
	}
	
	public static double getDouble(String key ,double def)
	{
		return getDouble(key ,def ,false) ;
	}
	
	private static double getDouble(String key ,double def ,boolean flg)
	{
		String tmp = conf.getProperty(key) ;
		if (tmp == null)
		{
			println(key ,flg) ;
			return def ;
		}
		
		try{
			return Double.parseDouble(tmp) ;
		}catch(Exception e){
			log.warn("配置参数[Double("+key+")]无效");
			return def ;
		}
	}
	
	private static void println(String key ,boolean flg)
	{
		if (flg){
			System.out.println("没有发现参数["+key+"]");
		}
	}
	
	public static String getResponse(String responseCode)
	{
		return conf.getProperty("rc.key."+responseCode ,responseCode) ;
	}
	
	public static String getResponseResult(String responseCode)
	{
		return conf.getProperty("rc."+responseCode ,responseCode) ;
	}
}
