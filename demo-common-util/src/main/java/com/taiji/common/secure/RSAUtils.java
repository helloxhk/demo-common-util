package com.taiji.common.secure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.kalian.thirdchannel.commons.utils.Base64Coder;
import com.kalian.thirdchannel.commons.utils.HexPlus;

/**
 * 
 * @author zhouy
 *
 */
public class RSAUtils {
	
	public String[] TRANSFORMATIONS = {"RSA/ECB/PKCS1Padding"};
	
	//public static final String TRANSFORMATION_RSAECBPKCS1PADDING = "RSA/ECB/PKCS1Padding";
	
	//加密算法
	private static final String ALGORITHM = "RSA";
	//CIPHER算法
	private static final String CIPHER_NAME = "RSA/ECB/PKCS1Padding";
	//RSA加密长度
	private static final int encryptLen = 100;
	//RSA解密长度
	private static final int decryptLen = 128;
	
	/**
	 * 生成公钥
	 * @param N
	 * @param E
	 * @return
	 * @throws Exception
	 */
	public RSAPublicKey getPublicKey(BigInteger N,BigInteger E) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(N, E);
		
		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		
		return pubKey;
	}
	
	/**
	 * 生成私钥
	 * @param N
	 * @param E
	 * @param D
	 * @param P
	 * @param Q
	 * @param DP
	 * @param DQ
	 * @param QP
	 * @return
	 * @throws Exception
	 */
	public RSAPrivateKey getPrivateKey(BigInteger N,BigInteger E,BigInteger D,BigInteger P,BigInteger Q,BigInteger DP,BigInteger DQ,BigInteger QP) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		RSAPrivateCrtKeySpec privKeySpec = new RSAPrivateCrtKeySpec(N, E, D, P, Q, DP, DQ, QP);
		
		RSAPrivateKey priKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
		
		return priKey;
	}
	
	/**
	 * 从字节数组得到公钥
	 * 
	 * @param pubKeyBytes
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromX509(byte[] pubKeyBytes) throws Exception {
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(pubKeyBytes);
		KeyFactory keyFac = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFac.generatePublic(pubX509);
		return pubKey;
	}
	
	/**
	 * 解密并转换成16进制的字符串形式（公私钥不能同时为空，当私钥为空时，用公钥解密）
	 * 
	 * @param mitexts
	 * @param rsapriKey
	 * @param rsapubKey
	 * @return
	 * @throws Exception
	 */
	public String decryptToHex(byte[] mitexts,RSAPrivateKey rsapriKey,RSAPublicKey rsapubKey) throws Exception{
		String mingtextHex = null;
		
		Cipher cipher = Cipher.getInstance(CIPHER_NAME);
		
		cipher.init(Cipher.DECRYPT_MODE, rsapriKey!=null?rsapriKey:rsapubKey);
		byte[] mingtexts = cipher.doFinal(mitexts);
		
		mingtextHex = HexPlus.encode(mingtexts);
		
		return mingtextHex;
	}
	
	/**
	 * 加密并转换成16进制的字符串形式（公私钥不能同时为空，当公钥为空时，用私钥加密）
	 * 
	 * @param mitexts
	 * @param rsapriKey
	 * @param rsapubKey
	 * @return
	 * @throws Exception
	 */
	public String encryptToHex(byte[] mingtexts,RSAPublicKey rsapubKey,RSAPrivateKey rsapriKey) throws Exception{
		String mitextHex = null;
		
		Cipher cipher = Cipher.getInstance(CIPHER_NAME);
		
		cipher.init(Cipher.ENCRYPT_MODE, rsapubKey!=null?rsapubKey:rsapriKey);
		byte[] mitexts = cipher.doFinal(mingtexts);
		
		mitextHex = HexPlus.encode(mitexts);
		
		return mitextHex;
	}
	
	/**
	 * 根据公钥文件路径获取公钥
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKeyPair(String path)throws Exception{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		RSAPublicKey rsaPublicKey = (RSAPublicKey)ois.readObject();
		ois.close();
		fis.close();
		return rsaPublicKey;
	}
	
	/**
	 * 取得密钥字符串
	 * @param keyPath
	 * @return
	 */
	public String getKey(String keyPath){
		String keyStr = null;
		BufferedReader bufferedReader = null;
		try{
			FileReader fileReader = new FileReader(keyPath);
			bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			while(true){
				String tmp = bufferedReader.readLine();
				if(tmp==null || tmp==""){
					break;
				}
				if(tmp.charAt(0)!='-'){
					stringBuffer.append(tmp);
				}
			}
			byte[] bytes = Base64Coder.decodeBASE64(stringBuffer.toString());
			keyStr = HexPlus.encode(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return keyStr;
	}
	
	/**
	 * 获取公钥Key
	 * @param pubKeyStr
	 * @return
	 */
	public PublicKey getPublicKey(String pubKeyStr){
		PublicKey publicKey = null;
		try{
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(HexPlus.decode(pubKeyStr));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		}catch(Exception e){
			e.printStackTrace();
		}
		return publicKey;
	}
	
	/**
	 * 根据公钥文件路径获取公钥
	 * @param keyPath
	 * @return
	 */
	public PublicKey getPublicKeyByPEM(String keyPath){
		return getPublicKey(getKey(keyPath));
	}
	
	/**
	 * 取得密钥Key
	 * @param priKeyStr
	 * @return
	 */
	public PrivateKey getPrivateKey(String priKeyStr){
		PrivateKey privateKey = null;
		try{
			PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(HexPlus.decode(priKeyStr));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
		}catch(Exception e){
			e.printStackTrace();
		}
		return privateKey;
	}
	
	/**
	 * 根据公钥文件路径获取公钥
	 * @param keyPath
	 * @return
	 */
	public PrivateKey getPrivateKeyByPEM(String keyPath){
		return getPrivateKey(getKey(keyPath));
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 */
	public String encrypt(byte[] data,Key key)throws Exception{
		String returnStr="";
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			int dataLen = data.length;
			for(int i=0;i<=dataLen/encryptLen;i++){
				int pos = i*encryptLen;
				if (pos == dataLen) {
					break;
				}
				int length = encryptLen;
				if(pos + encryptLen>dataLen){
					length = dataLen - pos;
				}
				byte[] inBytes = new byte[length];
				System.arraycopy(data, pos, inBytes, 0, length);
				byte[] outBytes = cipher.doFinal(inBytes);
				returnStr += HexPlus.encode(outBytes);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("加密出错",e);
		}
		return returnStr;
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 */
	public String decrypt(String data,Key key,String coderName)throws Exception{
		String returnStr="";
		try {
			byte[] bytes = HexPlus.decode(data);
			returnStr = "";
			Cipher cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			int dataLen = bytes.length;
			for(int i=0;i<=dataLen/decryptLen;i++){
				int pos = i*decryptLen;
				if (pos == dataLen) {
					break;
				}
				int length = decryptLen;
				if(pos + decryptLen>dataLen){
					length = dataLen - pos;
				}
				byte[] inbytes = new byte[length];
				System.arraycopy(bytes, pos, inbytes, 0, length);
				byte[] outBytes = cipher.doFinal(inbytes);
				returnStr += HexPlus.encode(outBytes);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("解密出错",e);
		}
		return new String(HexPlus.decode(returnStr),coderName);
	}
	
}
