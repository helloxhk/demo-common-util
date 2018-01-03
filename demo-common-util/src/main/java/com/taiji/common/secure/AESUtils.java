package com.taiji.common.secure;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.kalian.thirdchannel.commons.utils.HexPlus;

/**
 * 
 * @author zhouy
 *
 */
public class AESUtils {
	/**
	 * 获取aes密钥并转16进制
	 * @param provider
	 * @param providerName
	 * @return
	 */
	public String getRawKeyToHex(Provider provider, String providerName) {
		try {
			Security.addProvider(provider);
			KeyGenerator kgen = KeyGenerator.getInstance("AES",providerName);
			kgen.init(128);

			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return HexPlus.encode(raw);
		} catch (Exception e) {
			return "";
		}
	}
	public byte[] getRawKey(Provider provider, String providerName) {
		try {
			Security.addProvider(provider);
			KeyGenerator kgen = KeyGenerator.getInstance("AES",providerName);
			kgen.init(128);
			
			SecretKey skey = kgen.generateKey();
			return skey.getEncoded();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * AES加密并转换成16进制的字符串形式
	 * @param mingTexts
	 * @param keyHex
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] mingTexts, String keyHex) throws Exception {
		byte[] key = HexPlus.decode(keyHex);
		
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		
		Cipher cipher = Cipher.getInstance("AES");
		
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] mitexts = cipher.doFinal(mingTexts);
		
		return mitexts;
	}
	
	/**
	 * AES加密并转换成16进制的字符串形式
	 * @param mingTexts
	 * @param keyHex
	 * @return
	 * @throws Exception
	 */
	public String encryptToHex(byte[] mingTexts, String keyHex) throws Exception {
		byte[] key = HexPlus.decode(keyHex);

		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] mitexts = cipher.doFinal(mingTexts);

		return HexPlus.encode(mitexts);
	}
	
	/**
	 * AES解密并转换成16进制的字符串形式
	 * @param miTexts
	 * @param keyHex
	 * @return
	 * @throws Exception
	 */
	public String decryptToHex(byte[] miTexts, String keyHex) throws Exception {
		byte[] key = HexPlus.decode(keyHex);

		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] mitexts = cipher.doFinal(miTexts);

		return HexPlus.encode(mitexts);
	}
	
	/**
	 * AES加密并转换成16进制的字符串形式
	 * @param mingTexts
	 * @param KeySpec
	 * @return
	 * @throws Exception
	 */
	public String encryptToHex(byte[] mingTexts, SecretKeySpec keySpec) throws Exception{
		
		Cipher cipher = Cipher.getInstance("AES");
		
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);

		byte[] mitexts = cipher.doFinal(mingTexts);

		return HexPlus.encode(mitexts);
	}
	
	/**
	 * AES加密并转换成16进制的字符串形式
	 * @param mingTexts
	 * @param keyHex
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptToHex(byte[] mingTexts, String keyHex, Provider provider, String algorithm, String providerName) throws Exception {
		byte[] key = HexPlus.decode(keyHex);
		
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Security.addProvider(provider);
		
		Cipher cipher = Cipher.getInstance(algorithm, providerName);
		
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] mitexts = cipher.doFinal(mingTexts);
		
		return mitexts;
	}
	
	/**
	 * AES解密并转换成16进制的字符串形式
	 * @param miTexts
	 * @param keyHex
	 * @return
	 * @throws Exception
	 */
	public String decryptToHex(byte[] miTexts, String keyHex, Provider provider, String algorithm, String providerName) throws Exception {
		byte[] key = HexPlus.decode(keyHex);

		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Security.addProvider(provider);
		
		Cipher cipher = Cipher.getInstance(algorithm, providerName);

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] mingtexts = cipher.doFinal(miTexts);

		return HexPlus.encode(mingtexts);
	}
}
