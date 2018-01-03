package com.taiji.common.network.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.taiji.common.exception.EStockThirdException;

public class TestDefaultHttpClient {

	String token = "mcN4oMyzXqExlPMImr-gKWLPlI8eKGGs0yNkITtbEdkaAop3R8gw-XSIaCBMERGDxB7W26U-v_gS3Qw_dqhiwP_Doli7KEpkB3uwEznorw5Xp6A3iStbrIGPmC5KhO53ZRThAHAEWY";
	
	@Test
	public void testSendHttpsWithMultipartByPost1(){
		DefaultHttpClient t = new DefaultHttpClient("https://api.weixin.qq.com/cgi-bin/media/upload", 30000, "UTF-8");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("access_token", token);
		paramMap.put("type", "image");
		File file = new File("F://144299.jpg");
		try {
			System.out.println(t.sendHttpsWithMultipartByPost(paramMap, file, "media"));
		} catch (EStockThirdException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendHttpsWithMultipartByPost2(){
		String media_id = "WcTF-r-ihdHXbz_CeuhhkWSsmxqPiVXJ5TP8ONm-T9e5p3Y-6nFX48U2ti6tZ6NB";
		DefaultHttpClient t = new DefaultHttpClient("https://api.weixin.qq.com/cgi-bin/media/get", 30000, "UTF-8");
		String dataStr = "media_id=" + media_id + "&access_token=" + token;
		
		try{
			byte[] respBytes = t.sendHttpsByGetResponsFile(dataStr);
			
			System.out.println(t.sendHttpsByGetResponsFile(dataStr));
			
			File file = new File("C:\\Users\\waking\\Desktop\\logs\\tmp.jpg");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			bos.write(respBytes);
			bos.flush();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
