package com.taiji.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.taiji.common.utils.HexPlus;

/**
 * 
 * @author zhouy
 *
 */
public class CodingUtils {
	
	private static Logger log = Logger.getLogger(CodingUtils.class);
	
	/**
	 * BCD码转为bytes
	 * 
	 * @param bcdBytes
	 * @return
	 */
	public static synchronized byte[] bcd2bytes(byte[] bcdBytes){
		byte[] bytes = new byte[bcdBytes.length*2];
		int start = 0;
		for (int i = 0; i < 0 + bcdBytes.length; ++i) {
			bytes[(start++)] = (byte) (((bcdBytes[i] & 0xF0) >> 4) + 48);
			bytes[(start++)] = (byte) ((bcdBytes[i] & 0xF) + 48);
		}
		return bytes;
	}
	
	/**
	 * bytes转为BCD码
	 * @param bytes
	 * @return
	 */
	public static synchronized byte[] bytes2LeftBcd(byte[] bytes) {
		int charpos = 0;
        int bufpos = 0;
        
        byte[] bcdBytes = new byte[bytes.length / 2 + bytes.length % 2];
		byte[] tmp_bytes = new byte[bytes.length % 2 == 0 ? bytes.length : bytes.length + 1];
		if(bytes.length % 2 != 0){
			System.arraycopy(bytes, 0, tmp_bytes, 0, bytes.length);
			tmp_bytes[bytes.length] = 48;
		}else{
			System.arraycopy(bytes, 0, tmp_bytes, 0, bytes.length);
		}
		
        while(charpos < bytes.length){
        	bcdBytes[bufpos] = (byte)((tmp_bytes[charpos] - 48 << 4) | (tmp_bytes[charpos + 1] - 48));
        	
        	bufpos++ ;
        	charpos += 2;
        }
        
        return bcdBytes;
    }
	
	/**
	 * bytes转为BCD码
	 * @param bytes
	 * @return
	 */
	public static synchronized byte[] bytes2LeftBcd(byte[] bytes, int bcd_length) {
		int charpos = 0;
        int bufpos = 0;
        
		byte[] tmp_bytes = new byte[0];
		
		int fill_bcd_length = bcd_length * 2 - bytes.length;
		fill_bcd_length = fill_bcd_length < 0 ? 0 : fill_bcd_length;
		
		tmp_bytes = new byte[bytes.length + fill_bcd_length];
		int tmp_bytes_length = tmp_bytes.length;
		while(fill_bcd_length-- > 0){
			tmp_bytes[--tmp_bytes_length] = 48;
		}
		System.arraycopy(bytes, 0, tmp_bytes, 0, bytes.length);
		
		byte[] bcdBytes = new byte[tmp_bytes.length / 2];
		
        while(charpos < tmp_bytes.length){
        	bcdBytes[bufpos] = (byte)((tmp_bytes[charpos] - 48 << 4) | (tmp_bytes[charpos + 1] - 48));
        	
        	bufpos++ ;
        	charpos += 2;
        }
        
        return bcdBytes;
    }
	
	/**
	 * bytes转为BCD码
	 * @param bytes
	 * @return
	 */
	public static synchronized byte[] bytes2RightBcd(byte[] bytes) {
		int charpos = 0;
        int bufpos = 0;
        
		byte[] bcdBytes = new byte[bytes.length / 2 + bytes.length % 2];
		if(bytes.length % 2 != 0){
			bcdBytes[0] = (byte)(bytes[0] - 48);
            charpos = 1;
            bufpos = 1;
		}
		
        while(charpos < bytes.length){
        	bcdBytes[bufpos] = (byte)((bytes[charpos] - 48 << 4) | (bytes[charpos + 1] - 48));
        	
        	bufpos++ ;
        	charpos += 2;
        }
        
        return bcdBytes;
    }
	
	/**
	 * bytes转为BCD码
	 * @param bytes
	 * @return
	 */
	public static synchronized byte[] bytes2RightBcd(byte[] bytes, int bcd_length) {
		int charpos = 0;
        int bufpos = 0;
        
		byte[] bcdBytes = new byte[bytes.length / 2 + bytes.length % 2];
		if(bytes.length % 2 != 0){
			bcdBytes[0] = (byte)(bytes[0] - 48);
            charpos = 1;
            bufpos = 1;
		}
		
        while(charpos < bytes.length){
        	bcdBytes[bufpos] = (byte)((bytes[charpos] - 48 << 4) | (bytes[charpos + 1] - 48));
        	
        	bufpos++ ;
        	charpos += 2;
        }
        
        byte[] tmp_bcdBytes = new byte[bcd_length<bytes.length / 2 ? bytes.length / 2 : bcd_length];
        int i = 0;
        while(bcd_length - bcdBytes.length - i > 0){
        	tmp_bcdBytes[i++] = 0;
        }
        System.arraycopy(bcdBytes, 0, tmp_bcdBytes, i, bcdBytes.length);
        return tmp_bcdBytes;
    }
	
	/**
	 * 字符串转为BCD码
	 * 
	 * @param str
	 * @return
	 */
	/**
	public static synchronized byte[] str2Bcd(String str) {
        int charpos = 0; //char where we start
        int bufpos = 0;
        
        byte[] bcdBytes = new byte[str.length()%2==0?str.length()/2:str.length()/2+1];
        if (str.length() % 2 == 1) {
            //for odd lengths we encode just the first digit in the first byte
        	bcdBytes[0] = (byte)(str.charAt(0) - 48);
            charpos = 1;
            bufpos = 1;
        }
        //encode the rest of the string
        while (charpos < str.length()) {
        	bcdBytes[bufpos] = (byte)(((str.charAt(charpos) - 48) << 4)
                | (str.charAt(charpos + 1) - 48));
            charpos += 2;
            bufpos++;
        }
        
        return bcdBytes;
    }
    */
	
	/**
	 * 字符串转为BCD码
	 * 
	 * @param str
	 * @return
	 */
	/**
	public static synchronized byte[] str2LeftBcd(String str) {
        int charpos = 0; //char where we start
        int bufpos = 0;
        
        if(str.length() % 2!=0){
        	str = str + 0;
        }
        byte[] bcdBytes = new byte[str.length()%2==0?str.length()/2:str.length()/2+1];
        if (str.length() % 2 == 1) {
            //for odd lengths we encode just the first digit in the first byte
        	bcdBytes[0] = (byte)(str.charAt(0) - 48);
            charpos = 1;
            bufpos = 1;
        }
        //encode the rest of the string
        while (charpos < str.length()) {
        	bcdBytes[bufpos] = (byte)(((str.charAt(charpos) - 48) << 4)
                | (str.charAt(charpos + 1) - 48));
            charpos += 2;
            bufpos++;
        }
        
        return bcdBytes;
    }
    */
	
	/**
	 * BCD码转为字符串
	 * 
	 * @param bcdBytes
	 * @return
	 */
	/**
	public static synchronized String bcd2Str(byte[] bcdBytes){
		char[] digits = new char[bcdBytes.length*2];
		int start = 0;
		for (int i = 0; i < 0 + bcdBytes.length; ++i) {
			digits[(start++)] = (char) (((bcdBytes[i] & 0xF0) >> 4) + 48);
			digits[(start++)] = (char) ((bcdBytes[i] & 0xF) + 48);
		}
		return new String(digits);
	}
	*/
	
	/**
	 * 将字节数组进行md5签名
	 * 
	 * @param source
	 * @param isUpper
	 * @return
	 */
	public static synchronized String md5(byte[] source,boolean isUpper){
		if(source==null){
			return null;
		}
		String md5Str = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(source);
			byte[] sources = messageDigest.digest();
			
			md5Str = HexPlus.encode(sources);
		} catch (NoSuchAlgorithmException e) {
			log.error("将字节数组进行md5签名发生异常[source="+new String(source)+"]", e);
		}
		return isUpper?md5Str.toUpperCase():md5Str.toLowerCase();
	}
	
	/**
	 * 将字符串进行md5签名
	 * 
	 * @param source
	 * @param isUpper
	 * @return
	 */
	public static synchronized String md5(String source,String enCodeName,boolean isUpper){
		String md5Str = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(source.getBytes(enCodeName));
			byte[] sources = messageDigest.digest();
			
			md5Str = HexPlus.encode(sources);
		} catch (Exception e) {
			log.error("将字符串进行md5签名发生异常[source="+source+",enCodeName="+enCodeName+"]", e);
		}
		return isUpper?md5Str.toUpperCase():md5Str.toLowerCase();
	}
	
	/**
	 * 将字符串进行SHA-256签名
	 * @param source
	 * @param enCodeName
	 * @param isUpper
	 * @return
	 */
	public static String sha_256(String source,String enCodeName,boolean isUpper){
		return sha(source, enCodeName, isUpper, "SHA-256");
	}
	
	/**
	 * 将字符串进行SHA-1签名
	 * @param source
	 * @param enCodeName
	 * @param isUpper
	 * @return
	 */
	public static synchronized String sha(String source,String enCodeName,boolean isUpper,String algorithm){
		String shaStr = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(source.getBytes(enCodeName));
			byte[] sources = messageDigest.digest();
			
			shaStr = HexPlus.encode(sources);
		} catch (Exception e) {
			log.error("将字符串进行" + algorithm + "签名发生异常[source="+source+",enCodeName="+enCodeName+"]", e);
		}
		return isUpper?shaStr.toUpperCase():shaStr.toLowerCase();
	}
	
	public static void main(String[] args) {
//		System.out.println(CodingUtils.md5("123","UTF-8",false));
//		System.out.println(CodingUtils.md5("123".getBytes(), true));
//		System.out.println(HexPlus.encode(CodingUtils.str2LeftBcd("62263")));
//		System.out.println(CodingUtils.bcd2Str(CodingUtils.bytes2RightBcd("99=99".getBytes())));
//		System.out.println(CodingUtils.bcd2Str(CodingUtils.bytes2LeftBcd("99=99".getBytes())));
		System.out.println(CodingUtils.sha_256("123", "UTF-8", true));
		System.out.println(CodingUtils.sha("123", "UTF-8", true,"SHA-1"));
		
//		System.out.println(new String(CodingUtils.bcd2bytes(CodingUtils.bytes2RightBcd("9999".getBytes()))));
//		System.out.println(new String(CodingUtils.bcd2bytes(CodingUtils.bytes2RightBcd("9999".getBytes(),4))));
//		System.out.println(new String(CodingUtils.bcd2bytes(CodingUtils.bytes2LeftBcd("9".getBytes()))));
//		System.out.println(new String(CodingUtils.bcd2bytes(CodingUtils.bytes2LeftBcd("9999".getBytes(),8))));
	}
}
