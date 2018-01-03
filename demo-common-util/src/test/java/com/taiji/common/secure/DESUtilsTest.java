package com.taiji.common.secure;

import org.junit.Test;

import com.kalian.thirdchannel.commons.utils.HexPlus;

public class DESUtilsTest {
	
	@Test
	public void testEncrypt_3des(){
		DESUtils d = new DESUtils(DESUtils.TRANSFORMATION_3DESECBPKCS7PADDING);

		String key = "FD0762380D980D52";
		String mingtext = "88888888";
		
		try {
			byte[] miBytes = d.encrypt_3des(key.getBytes(), mingtext.getBytes());
			System.out.println(HexPlus.encode(miBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDecrypt_3des(){
		DESUtils d = new DESUtils(DESUtils.TRANSFORMATION_3DESECBPKCS7PADDING);

		String key = "FD0762380D980D52";
		String mitext = "b72172aabf7fdd8b00d6094f6cb97423";
		
		try {
			byte[] mingBytes = d.decrypt_3des(key.getBytes(), HexPlus.decode(mitext));
			System.out.println(new String(mingBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
