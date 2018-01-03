package com.taiji.common.util.security.cipher;

/**
 * 加密处理器
 */
public interface CipherHandler {

	/**
	 * 对信息进行加密
	 */
	String encode(String msg);

	/**
	 * 对信息进行解密
	 */
	String decode(String msg);
}