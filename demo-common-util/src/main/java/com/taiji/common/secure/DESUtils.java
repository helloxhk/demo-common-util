package com.taiji.common.secure;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.kalian.thirdchannel.commons.utils.Base64Coder;

/**
 * 
 * @author zhouy
 *
 */
public class DESUtils {

	public static final String TRANSFORMATION_3DESECBNOPADDING = "DESede/ECB/NoPadding";
	
	public static final String TRANSFORMATION_3DESECBPKCS7PADDING = "DESede/ECB/PKCS7Padding";
	
	public static final String TRANSFORMATION_DESCBCPKCS5PADDING = "DES/CBC/PKCS5Padding";
	
	public static final String TRANSFORMATION_DESECBPKCS5Padding = "DES/ECB/PKCS5Padding";
	
	private String transformation;
	
	public DESUtils(String transformation){
		// 添加PKCS7Padding支持
		Security.addProvider(new BouncyCastleProvider());
		this.transformation = transformation;
	}
	
	public byte[] encrypt_3des(byte[] key,byte[] mingtext) throws Exception{
		byte[] mitext = null;
		
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		
		mitext = cipher.doFinal(mingtext);
		
		return mitext;
	}
	
	public byte[] decrypt_3des(byte[] key,byte[] mitext) throws Exception{
		byte[] mingtext = null;
		
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		
		mingtext = cipher.doFinal(mitext);
		
		return mingtext;
	}
	
	public byte[] decrypt(byte[] key,byte[] mitext, byte[] iv) throws Exception{
		byte[] mingtext = null;
		
		SecretKey deskey = new SecretKeySpec(key, "DES");
		
		Cipher cipher = Cipher.getInstance(transformation);
		
		cipher.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(iv));
			
		mingtext = cipher.doFinal(mitext);
		
		return mingtext;
	}
	
	public byte[] encrypt(byte[] key,byte[] mingtext, byte[] iv) throws Exception{
		byte[] mitext = null;
		
		SecretKey deskey = new SecretKeySpec(key, "DES");
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(iv));
		
		mitext = cipher.doFinal(mingtext);
		
		return mitext;
	}
	
	public byte[] encrypt(byte[] key,byte[] mingtext) throws Exception{
		byte[] mitext = null;
		
		SecretKey deskey = new SecretKeySpec(key, "DES");
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		
		mitext = cipher.doFinal(mingtext);
		
		return mitext;
	}
	
	public static void main(String[] args) {
		try {
//			DESUtils d = new DESUtils(DESUtils.TRANSFORMATION_3DESECBNOPADDING);
//			
//			byte[] keybyte = { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x23, 0x45, 0x67,
//					(byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x45, 0x67, (byte) 0x89, (byte) 0xAB,
//					(byte) 0xCD, (byte) 0xEF, 0x01, 0x23 };
//			
//			File file = new File("C:/Users/waking/Desktop/ZhaoLing2/keyfile/10020001.id");
//			
//			InputStream io =new FileInputStream(file);
//			
//			int length = io.available();
//			byte[] mitext = new byte[length];
//			io.read(mitext);
//			
//			byte[] mingtext = d.decrypt_3des(keybyte, mitext);
//			
//			byte[] data = new byte[128];
//			System.arraycopy(mingtext, 360, data, 0, 128);
//			io.close();
			
			DESUtils d = new DESUtils(DESUtils.TRANSFORMATION_DESCBCPKCS5PADDING);
			System.out.println(new String(d.decrypt("01139952".getBytes(), Base64Coder.decodeBASE64(Base64Coder.encoderBASE64(d.encrypt("01139952".getBytes(), "12346".getBytes(), "12345678".getBytes()), true)), "12345678".getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
