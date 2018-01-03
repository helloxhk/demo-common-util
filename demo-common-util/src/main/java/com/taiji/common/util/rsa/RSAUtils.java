package com.taiji.common.util.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 
 * @author zhouy
 *
 */
public class RSAUtils {
	// 加密算法
	private static final String ALGORITHM = "RSA";
	// 签名算法SHA
	private static final String ALGORITHM_SIGN_SHA = "SHA1WithRSA";
	// 签名算法MD5
	private static final String ALGORITHM_SIGN = "MD5withRSA";

	/**
	 * 取得密钥Key
	 * 
	 * @param priKeyStr
	 * @return
	 */
	public PrivateKey getPrivateKey(String keyPath) {
		PrivateKey privateKey = null;
		try {
			File f = new File(keyPath);

			byte[] kb = new byte[(int) f.length()];
			FileInputStream fis = new FileInputStream(f);
			fis.read(kb);
			fis.close();
			
			PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(kb);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	/**
	 * 用SHA进行签名
	 * 
	 * @param data
	 * @param priKey
	 * @return
	 */
	public byte[] signWithSHA(byte[] data, PrivateKey priKey){
		byte[] signBytes = null;
		try{
			Signature signature = Signature.getInstance(ALGORITHM_SIGN_SHA);
			signature.initSign(priKey);
			signature.update(data);

			signBytes = signature.sign();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return signBytes;
	}
}