package com.taiji.common.util.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * Turns array of bytes into string
	 * 
	 * @param buf
	 *            Array of bytes to convert to hex string
	 * @return Generated hex string
	 */

	private final static String algorithm = "AES";

	private static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	private static byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}

	public static String getRawKey() {

		try {
			// Get the KeyGenerator
			KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
			kgen.init(128); // 192 and 256 bits may not be available

			// Generate the secret key specs.
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return asHex(raw);
		} catch (Exception e) {
			// App.log.info("AES", "获取密钥出错," + e.getMessage());
			return "";
		}

	}

	public static String getEncrypt(String message, String rawKey) {
		byte[] key = asBin(rawKey);
		// Instantiate the cipher
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);

			Cipher cipher = Cipher.getInstance(algorithm);

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(message.getBytes());

			return asHex(encrypted);
		} catch (Exception e) {
			// App.log.info("AES", "获取加密串出错," + e.getMessage());
			return "";
		}

	}

	public static String getDecrypt(String encrypted, String rawKey) {
		byte[] tmp = asBin(encrypted);
		byte[] key = asBin(rawKey);

		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);

			Cipher cipher = Cipher.getInstance(algorithm);

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] decrypted = cipher.doFinal(tmp);

			return new String(decrypted, "GBK");
		} catch (Exception e) {
			// App.log.info("AES", "获取解密串出错," + e.getMessage());
			return "";
		}

	}

	public static void main(String[] args) throws Exception {

		String message = "3;2010002;0.00;494774.00;S3;3;1;1;9;1;2;3;1;3;8;3;1;直    选;0;285;1000.00;2;组选三;0;0;320.00;3;组选六;0;835;160.00;1;100;2135063.00;494774.00;";
		String temps = "3E21165C02E4DCC4BD9B715D9E9AC60CF7A78951B70D475905C8B1290E01720C9BC48E82995C7D4ED6BA6D360ED84F0971B214C412EF3CF32F085C2D000E4648D19708B1E5B49D397B942A9BC829FE21950585BAD78F4D150875EBB566034D234E5C40E0DABCDC7D609B325A55705D386F8E80C2E32E92F84164C5650E5E2A7E2B44C3EA10BFD8087BB512AD8CBEB8D8A55AA2DBC65C01EC584E9EAE53DFC129";
		String rawKey = "d408dc1d731b4874b6c87d9762b3705f";
		// String rawKey = getRawKey(); //得到钥匙
		/*
		 * System.out.println("Key = " + rawKey);
		 * 
		 * String encrypted = getEncrypt(message,rawKey);
		 * System.out.println("org text = " + message);//原数据
		 * System.out.println("encrypted = " + encrypted);//加密以后
		 */
		String decrypted = getDecrypt(temps, "d408dc1d731b4874b6c87d9762b3705f");// 解密串
		System.out.println("decrypted = " + decrypted);
		System.out.println("decrypted汉字 = "
				+ new String(decrypted.getBytes(), "GBK"));

		// System.getProperties().list(System.out);

	}
}
