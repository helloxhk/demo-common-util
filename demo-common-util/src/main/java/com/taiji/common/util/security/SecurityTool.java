package com.taiji.common.util.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.taiji.common.utils.CommonsConfig;
import com.taiji.common.util.security.cipher.CipherHandlerImpl;

/**
 * 加解密方法
 * @author
 *
 */
public class SecurityTool {
	
	/**
	 * @param msg     待解密密文
	 * @param method  解密方法  00:控件（AES） 01：控件（AES）+RSA 02：AES 03：RSA公钥加密 04：无密码 05：控件（AES）+AES
	 * @param keyInfo 密钥文件或密钥字符串
	 * @param isFile  key是文件还是字符串(卡联密钥信息) true：文件     false：字符串
	 * @return
	 * @throws Exception
	 */
	public static String decode(String msg,String method,String keyInfo,boolean isFile) throws Exception{
		String result=msg;
		
		if(isFile){
			if("AES".equals(method)){
				//AES解密
				String absolutePath = CommonsConfig.getFilepathByClsdir(keyInfo);
				CipherHandlerImpl cipherHandlerImpl = new CipherHandlerImpl("AES",absolutePath, true);
				result = cipherHandlerImpl.decode(msg);
			}else if("RSA".equals(method)){
				//RSA私钥解密
				RSAPrivateKey privateKey = RSATool.getPrivateKey(keyInfo);
				byte[] resultByte=RSATool.decrypt(privateKey, Coder.hexStr2ByteArr(msg));
				result=new String(resultByte);
			}
			
		}else{
			if("AES".equals(method)){
				//AES解密
				result=CipherHandlerImpl.decryptByStr(keyInfo, msg);
			}else if("RSA".equals(method)){
				//RSA解密
				byte[]  aaa=Coder.hexStr2ByteArr(msg);
				System.out.println(aaa+"====");
				result=RSATool.decryptByPrivateKey(keyInfo, aaa);
			}
			
		}
		
		return result;
	}
	
	/**
	 * 加密
	 * @param msg
	 * @param method
	 * @param keyInfo
	 * @param isFile
	 * @return
	 * @throws Exception
	 */
	public static String encode(String msg,String method,String keyInfo,boolean isFile) throws Exception{
		String result=msg;
		if(isFile){
			//AES加密
			if("AES".equals(method)){
				String absolutePath = CommonsConfig.getFilepathByClsdir(keyInfo);
				CipherHandlerImpl cipherHandlerImpl = new CipherHandlerImpl("AES",absolutePath, true);
				result= cipherHandlerImpl.encode(msg);
			}else if("RSA".equals(method)){
				//RSA公钥加密
				RSAPublicKey publicKey = RSATool.getPublicKey(keyInfo);
				byte[] encrypt = RSATool.encrypt(publicKey, msg.getBytes());
				result=new String(encrypt);
			}
		}else{
			if("AES".equals(method)){
				//AES加密
				result = CipherHandlerImpl.encryptByStr(keyInfo, msg);
			}else if("RSA".equals(method)){
				//RSA加密
				result=RSATool.encryptByPublicKey(keyInfo, msg.getBytes());
			}
		}
		return result;
	}
	
}
