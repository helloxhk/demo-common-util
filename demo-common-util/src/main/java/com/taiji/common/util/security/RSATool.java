package com.taiji.common.util.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

/**
 * 新的RSA加密机制
 * @author 杨阳
 *
 */
public class RSATool{

	/** 加密算法 */
	private static final String ALGORITHM = "RSA";
	/** CIPHER算法名称 */
	private static final String CIPHER_NAME = "RSA/ECB/PKCS1Padding";

    /**
	 * 生成密钥对
	 * @param keySize
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateKeyPair(int keySize)
			throws NoSuchAlgorithmException {
		KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(ALGORITHM);
		kpGenerator.initialize(keySize);
		KeyPair kp = kpGenerator.generateKeyPair();
		return kp;
	}
    
	/**
	 * 生成公钥，私钥文件
	 * @param keySize
	 */
    public void createKeyFiles(int keySize,String keyPath_pri,String keyPath_pub){
	    try{
	    	KeyPair keyPair=generateKeyPair(keySize);
			RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
			
	    	//生成私钥文件
	    	byte[] privateEncode = privateKey.getEncoded();
	        createFiles(keyPath_pri, privateEncode);
	        
	       //生成公钥文件
	        byte[] publicEncode = publicKey.getEncoded();
	    	createFiles(keyPath_pub,publicEncode);
	    }catch (Exception e){
	        e.printStackTrace();
	    } 
    }

    /**
	 * 创建密钥文件，用法参照main方法
	 * @param keyPath
	 * @param key
	 * @throws BusinessException
	 */
	public static void createFiles(String keyPath,byte[] key) throws BusinessException{
        // 判断指定路径合法与否  
        if ((keyPath.indexOf(":\\") < 0) && (keyPath.indexOf(":/") < 0)){  
        	throw new BusinessException("iphone.web.pay.pos.23", "文件路径错误"+"#"+CommonConstants.SYS_PATH_ERROR); 
        }  
         
        ObjectOutputStream out=null;
        try{  
        	 //文件不存在，则创建
            File file=new File(keyPath);
            if(!file.exists()){
            	file.createNewFile();
            }
        	//初始化IO对象，写入密钥  
            out = new ObjectOutputStream(new FileOutputStream(keyPath)); 
            out.writeObject(key);  
        }catch (IOException ex){ 
        	ex.printStackTrace();
        }finally{  
            try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
	}

	/** 
     * 得到私钥 
     * @param keyPath 私钥地址 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String keyPath){  
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyPath));
			byte[] desEncodeRead = (byte[])in.readObject();  
            in.close();  
            DerValue d = new DerValue(desEncodeRead);  
            PrivateKey p = RSAPrivateKeyImpl.parseKey(d);  
            return (RSAPrivateKey)p;  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
    }  
  
    /** 
     * 得到公钥 
     * @param keyPath 公钥文件地址 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String keyPath){  
        try {
        	ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyPath));  
        	byte[] desEncodeRead = (byte[])in.readObject();  
			in.close();
			DerValue d = new DerValue(desEncodeRead);  
		    PublicKey p = RSAPublicKeyImpl.parse(d);  
		    return (RSAPublicKey)p; 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}  
       
    }  

    public void encryptFile(RSAPublicKey publicKey, File file, File newFile)
    {
        try
        {
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(newFile);

            byte[] bytes = new byte[53];
            while (is.read(bytes) > 0)
            {
                byte[] e = this.encrypt(publicKey, bytes);
                bytes = new byte[53];
                os.write(e, 0, e.length);
            }
            os.close();
            is.close();
            System.out.println("write success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void decryptFile(RSAPrivateKey privateKey, File file, File newFile)
    {
        try
        {
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(newFile);
            byte[] bytes1 = new byte[64];
            while (is.read(bytes1) > 0)
            {
                byte[] de = this.decrypt(privateKey, bytes1);
                bytes1 = new byte[64];
                os.write(de, 0, de.length);
            }
            os.close();
            is.close();
            System.out.println("write success");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
	 * 公钥加密
	 * @param publicKey
	 * @param obj
	 * @return
	 */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] obj){  
        if (publicKey != null){  
            try {  
                Cipher cipher = Cipher.getInstance(ALGORITHM);  
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
                return cipher.doFinal(obj);  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
  
   /**
    * 私钥解密
    * @param privateKey
    * @param obj
    * @return
    */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] obj){  
        if (privateKey != null){  
	        try{  
	            Cipher cipher = Cipher.getInstance(ALGORITHM);  
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	            return cipher.doFinal(obj);  
	        }catch (Exception e){  
	            e.printStackTrace();  
	        }  
        }  
        return null;  
    } 

    /**
	 * 公钥加密
	 * @param key  公钥字符串（16进制字符串）
	 * @param text 待加密字符数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String key,byte[] text) throws Exception{
		//通过公钥字符串得到，公钥
		PublicKey pk=KeyUtil.getPublicKeyFromX509x16(key);
		//初始化加解密类
		Cipher cipher=Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		//加密
		byte[] cipherText=cipher.doFinal(text);
		return Coder.byteArr2HexStr(cipherText);
	}
	
	/**
	 * 公钥解密
	 * @param key  公钥字符串（16进制字符串）
	 * @param text 待解密字符数组
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String key,byte[] text) throws Exception{
		//通过公钥字符串得到，公钥
		PublicKey pk=KeyUtil.getPublicKeyFromX509x16(key);
		//初始化加解密类
		Cipher cipher=Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, pk);
		//解密
		byte[] plainText=cipher.doFinal(text);
		return new String(plainText);
	}
	
	/**
	 * 私钥加密
	 * @param key   私钥字符串（16进制字符串）
	 * @param text  待加密字符数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String key,byte[] text) throws Exception{
		//通过私钥字符串得到，私钥
		PrivateKey pk=KeyUtil.getPrivateKeyFromPKCS8x16(key);
		//初始化加解密类
		Cipher cipher=Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		//加密
		byte[] cipherText=cipher.doFinal(text);
		return new String(cipherText);
	}
	
	/**
	 * 私钥解密
	 * @param key  私钥字符串（16进制字符串）
	 * @param text 待解密字符数组
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String key,byte[] text) throws Exception{
		//通过私钥字符串得到，私钥
		PrivateKey pk=KeyUtil.getPrivateKeyFromPKCS8x16(key);
		//初始化加解密类
		Cipher cipher=Cipher.getInstance(CIPHER_NAME);
		cipher.init(Cipher.DECRYPT_MODE, pk);
		//解密
		byte[] plainText=cipher.doFinal(text);
		return new String(plainText);
	}
    
    public static void main(String[] args){
    	try {
            RSATool encrypt = new RSATool();
            String keyPath_pub="D:\\RSAPublic.pem";
            String keyPath_pri="D:\\RSAPrivate.pem";
            
            
            //公钥加密
            RSAPublicKey publicKey = encrypt.getPublicKey(keyPath_pub);
            byte[] b0= RSATool.encrypt(publicKey,"yangyang".getBytes());
            String str=Coder.byteArr2HexStr(b0);
            System.out.println("密文："+str);
            
        
            RSAPrivateKey privateKey = encrypt.getPrivateKey(keyPath_pri);
            byte[] msg=RSATool.decrypt(privateKey, Coder.hexStr2ByteArr(str));
            System.out.println("明文："+new String(msg));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
       
    }
}
