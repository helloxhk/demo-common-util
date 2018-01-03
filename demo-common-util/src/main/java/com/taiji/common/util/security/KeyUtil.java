package com.taiji.common.util.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.taiji.common.util.string.StringUtils;

public class KeyUtil {
	/** 加密算法 */
	private static final String ALGORITHM = "RSA";

	/**
	 * 从十六进制字符串得到公钥
	 * 
	 * @param pubKeyString
	 *            公钥的十六进制字符串表示
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKeyFromX509x16(String pubKeyString)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return getPublicKeyFromX509(StringUtils.hex2Bytes(pubKeyString));
	}

	/**
	 * 从base64编码字符串得到公钥
	 * 
	 * @param pubKeyString
	 *            公钥的base64编码字符串表示
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKeyFromX509x64(String pubKeyString)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			UnsupportedEncodingException {
		return getPublicKeyFromX509(StringUtils.decodeBASE64(pubKeyString, null));
	}

	/**
	 * 从字节数组得到公钥
	 * 
	 * @param pubKeyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKeyFromX509(byte[] pubKeyBytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(pubKeyBytes);
		KeyFactory keyFac = KeyFactory.getInstance(ALGORITHM);

		PublicKey pubKey = keyFac.generatePublic(pubX509);
		return pubKey;
	}

	/**
	 * 从十六进制字符串得到私钥
	 * 
	 * @param pubKeyString
	 *            私钥的十六进制字符串表示
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKeyFromPKCS8x16(String priKeyString)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException {
		return getPrivateKeyFromPKCS8(StringUtils.hex2Bytes(priKeyString));
	}

	/**
	 * 从base64编码字符串得到公钥
	 * 
	 * @param pubKeyString
	 *            公钥的base64编码字符串表示
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKeyFromPKCS8x64(String priKeyString)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			UnsupportedEncodingException, InvalidKeyException {
		return getPrivateKeyFromPKCS8(StringUtils.decodeBASE64(priKeyString,
				null));
	}

	/**
	 * 从字节数组得到私钥
	 * 
	 * @param priKeyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKeyFromPKCS8(byte[] priKeyBytes)
			throws NoSuchAlgorithmException, InvalidKeyException,
			InvalidKeySpecException {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(priKeyBytes);
		KeyFactory keyFac = KeyFactory.getInstance(ALGORITHM);
		PrivateKey priKey = keyFac.generatePrivate(priPKCS8);

		return priKey;
	}

	/**
	 * 从指定文件获取密钥对
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static KeyPair getKeyPair(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		KeyPair kp = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			kp = (KeyPair) ois.readObject();
			ois.close();
			fis.close();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}

		return kp;
	}

	/**
	 * 将密钥对保存到指定文件
	 * 
	 * @param kp
	 * @param fileName
	 * @throws Exception
	 */
	public static void saveKeyPair(KeyPair kp, String fileName)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(kp);
			oos.close();
			fos.close();
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (oos != null) {
				oos.close();
			}
		}
	}
}
