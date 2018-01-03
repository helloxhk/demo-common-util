package com.taiji.common.network.http;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.kalian.thirdchannel.commons.exception.EStockThirdException;

/**
 * 默认的httpclient发送器
 * @author waking
 *
 */
public class DefaultHttpClient extends BaseHttpClient<String> {

	public DefaultHttpClient(String url, int timeout, String charset) {
		super(url, timeout, charset);
	}

	@Override
	@Deprecated
	public String sendData(Map<String, String> paramMap) {
		try {
			return super.sendByPost(paramMap);
		} catch (EStockThirdException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String sendHttpsByPost(List<NameValuePair> list) throws EStockThirdException{
		return super.sendHttpsByPost(list);
	}
	
	public String sendHttpByPost(Map<String, String> paraMap) throws EStockThirdException{
		return super.sendByPost(paraMap);
	}
	
	public byte[] sendHttpsByGetResponsFile(String dataStr) throws EStockThirdException{
		return super.sendHttpsByGetResponsBytes(dataStr);
	}
	
	public String sendDateWithMutlipart(Map<String, String> paramMap, File file, String fileKeyName) throws EStockThirdException{
		return super.sendHttpsWithMultipartByPost(paramMap, file, fileKeyName);
	}

}
