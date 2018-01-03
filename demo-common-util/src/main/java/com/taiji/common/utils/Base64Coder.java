package com.taiji.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author zhouy
 *
 */
public class Base64Coder {
	/**
	 * 进行BASE64解码
	 * @param content
	 * @param charsetName
	 * @return
	 */
	public static byte[] decodeBASE64(String content){
		Base64 base64 = new Base64();
		byte[] decodeBytes = null;
		try{
			decodeBytes = base64.decode(content);
		}catch(Exception e){
			e.printStackTrace();
		}
		return decodeBytes;
	}
	
	/**
	 * 进行BASE64编码
	 * @param bytes
	 * @return
	 */
	public static String encoderBASE64(byte[] bytes ,boolean isOneLine){
		String str = "";
		Base64 base64 = new Base64();
		byte[] decodeBytes = null;
		try{
			decodeBytes = base64.encode(bytes);
			//2013.07.29 panyf 修改
			if(isOneLine){
				ByteArrayInputStream bis=new ByteArrayInputStream(decodeBytes);
				BufferedReader br=new BufferedReader(new InputStreamReader(bis));
				String temp="";
				while (null!=(temp=br.readLine())) {
					str+=temp;
				}
				bis.close();
				br.close();
			}else{
				str = new String(decodeBytes);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}

//	/**
//	 * 进行BASE64编码
//	 * @param bytes
//	 * @return
//	 */
//	public static String encoderBASE64WithFill3Length(byte[] bytes,boolean isOneLine){
//		String str = "";
//		BASE64Encoder bASE64Encoder = new BASE64Encoder();
//		int length = bytes.length;
//		if(length%3!=0){
//			int k = 3-(length%3);
//			byte[] tmp = new byte[length+k];
//			System.arraycopy(bytes, 0, tmp, 0, length);
//			int j = 0;
//			for(int i=0;i<k;i++){
//				j = length+i;
//				tmp[j] = " ".getBytes()[0];
//			}
//			str = bASE64Encoder.encode(tmp);
//		}else{
//			str = bASE64Encoder.encode(bytes);
//		}
//		if(isOneLine){
//			return str.replaceAll(System.getProperty("line.separator"), "");			
//		}
//		return str;
//	}
}
