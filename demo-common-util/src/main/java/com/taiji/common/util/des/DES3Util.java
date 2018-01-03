package com.taiji.common.util.des;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import it.sauronsoftware.base64.Base64;

public class DES3Util {

	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText, String secretKey, String iv) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			byte[] bb = Base64.encode(encryptData);
			return new String(bb, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText, String secretKey, String iv) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

//			byte[] decryptData = cipher.doFinal(Des3Base64.decode(encryptText));
			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText.getBytes(encoding)));
			return new String(decryptData, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptText;
	}
	

	public static void main(String[] args) throws Exception {
		
		//1、
		String pinkey = "C0408C62B674DAE798FB435A5638F91E";
		String mainKey = "46A3CA3E441C7D1559014DE9955270B6";
//		log.info(decode3DES(pinkey, mainKey));
		
		//2、
		String s1 = "明文数据";
		String iv = "87654321";//初始向量
		
		try {
			String s2 = encode(s1, mainKey, iv);
			System.out.println("密文数据:" + s2);
			String s3 = decode(s2, mainKey, iv);
			System.out.println("解密数据:" + s3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
