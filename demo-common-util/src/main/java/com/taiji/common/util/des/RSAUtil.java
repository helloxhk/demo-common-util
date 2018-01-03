package com.taiji.common.util.des;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil {


	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
		RSAPublicKey publicKey = null;
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("公钥非法");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥数据为空");
		}
		return publicKey;
	}
	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥输入流为空");
		}
	}

	public RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		RSAPrivateKey privateKey = null;
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥数据为空");
		}
		
		return privateKey;
	}

	/**
	 * 加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("密文数据已损坏");
		}
	}
	
	
	public static void main(String[] args) {
		
		
		String rsaPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu+gafvGKAJ5I2VJ5UDAjFMmREdoU0IgSsCWYnNyytNdbBBX21/513FF3k1DhLB3G8EwzUd8E5Vq5WrevsaRjOSJ+YthwhHaPbDEHhbJ1hcyIQ6EffYzTO7H2DFl/yR/eeuD4T9cpa6j5q3AumB3jc8OfIw2EsExYDDdZSVA1s1QIDAQAB";
			
		String rsaPriKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK76Bp+8YoAnkjZUnlQMCMUyZER2hTQiBKwJZic3LK011sEFfbX/nXcUXeTUOEsHcbwTDNR3wTlWrlat6+xpGM5In5i2HCEdo9sMQeFsnWFzIhDoR99jNM7sfYMWX/JH9564PhP1ylrqPmrcC6YHeNzw58jDYSwTFgMN1lJUDWzVAgMBAAECgYBKJqC5/r6LarmWlqOGIJdy4hkFvmhSoHv0YANPaR1gxekdYXNVtl6HwTTNLXYxrZTwS+5q99kFsIsvJoNNa3hb9u5AJekU6xCHD71hv/0IxoTGI07cSyR7RE4ILThR/HO8G5OMq7aBe76xmPlrmrdDzDwM1dqACwJPyDPXSOyBgQJBAOcGaZXYwZr2bxaRpvIdpJdu7R6aJzskfHRcHg8Kb6t3ZPlG/rbCpZmE6GZvX2RI72Go/CYwsMPagnkUG9VGBWECQQDB5HtYQgUJ28HiMsXHzLTtDSlyV4w+b9l0G9wSSgXvtemwOF5ucZkbcgPNuvvMckMfLecdj0ICH8JfGOsPiKf1AkBoJGzeXsrPtz1WmCwAwroahaegfu8mawkrTwdB0nuKeRTshkN2UYJjFRXKb3Q5+eLnTEz2Tkaw00Szngv59UmhAkEAtklGpU08S25ts2mkhBkyqALNypAAa9rgB8cBebhaKGlQULpOHWtzVFg2tz5p6GYsMK4JMMM0faa6KInWxH3n2QJATQdf2MSCliF8NqIg67Ckkwnxqwl5RuPC/aGnRVtW1XWKkQjmxjOldpROs4CfhQja35deDDqk0lUjZU4q7Jkfjw==";
			
		String data = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4";
		
		String encyStr = "XuqhDqH29ootfi2hUxCXoLEE2JzUSb4JNyL1BkJVMUyYupn2OtnC6/ECLXN+Z9FTS7+hKMbVcmTyn8eN757qHTKWbD9TnTy7Dd/2noAcKzhRyyJ4NEUfz2WNU9O9x9273MUeAxkz1YjzXbgMlR+Al1q9hHLI15B3BKYJw8HwSzU=";
		
		RSAUtil rsaUtil = new RSAUtil();
		
		try {
//			byte[] encByte = rsaUtil.encrypt(rsaUtil.loadPublicKey(rsaPubKey), data.getBytes());
//			System.out.println("加密后："+new BASE64Encoder().encode(encByte));
			byte[] decByte = rsaUtil.decrypt(rsaUtil.loadPrivateKey(rsaPriKey),new BASE64Decoder().decodeBuffer(encyStr));
			System.out.println("解密后:"+new String(decByte));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
