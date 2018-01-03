package com.taiji.common.util.security;

import java.math.BigInteger;

import org.apache.log4j.Logger;

public class Coder {

	static Logger logger = Logger.getLogger(Coder.class);

	/**
	 * 十进制大数字符串转换为16进制字符串
	 * @param strBigIngeter
	 * @return 16进制字符串
	 */
	public static String bigint2HexStr(String strBigIngeter) {
		BigInteger n = new BigInteger(strBigIngeter);
		try {
			return byteArr2HexStr(n.toByteArray(), null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	/**
	 * 二进制数转换为16进制字符串格式
	 * @param rawData 二进制数
	 * @return 16进制字符串格式
	 */
	public static String byteArr2HexStr(byte[] rawData) {
		return byteArr2HexStr(rawData, null);
	}

	/**
	 * 二进制数转换为16进制字符串格式
	 * 
	 * @param rawData
	 * @param spliter 填充字符
	 * @return 16进制字符串格式
	 */
	public static String byteArr2HexStr(byte[] rawData, String spliter) {
		if(rawData == null) {
			return null;
		}
		int iLen = rawData.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = rawData[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
			if (i < iLen - 1 && spliter != null) {
				sb.append(spliter);
			}
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转换为二进制数
	 * @param strIn 16进制字符串
	 * @return 二进制数
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		if (strIn == null) {
			return null;
		}
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			int len = 2;
			if (i + len > iLen) {
				len = 1;
			}
			String strTmp = new String(arrB, i, len);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static void main(String[] args) {
		byte b=49;
		char c = (char)b;
		System.out.println(c);
	}
}
