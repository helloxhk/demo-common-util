package com.taiji.common.network.http;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.taiji.common.utils.CommonsConfig;
import com.taiji.common.exception.EStockThirdException;

/**
 * HTTP请求基类(基于httpclient4.0)
 * 
 * @author 周涌
 * 
 */
public abstract class BaseHttpClient<Entity> {
	
	protected static Logger log = Logger.getLogger(BaseHttpClient.class);
	
	private static AtomicInteger sn = new AtomicInteger(0) ;

	/**
	 * 异常:通讯超时
	 */
	public final static int HTTP_MESSAGE_TIMEOUT = 5001;
	/**
	 * 异常:通讯异常
	 */
	public final static int HTTP_MESSAGE_ERROR = 5002;
	/**
	 * 异常:响应状态码错误
	 */
	public final static int HTTP_MESSAGE_STATUSERROR = 5003;
	/**
	 * 异常:构建客户端失败
	 */
	public final static int HTTP_MESSAGE_BUILDCLIENTERROR = 5004;
	
	private boolean isTrace = true;
	private String name = null;
	
	/**
	 * 连接地址
	 */
	private String url;
	/**
	 * 超时时间
	 */
	private int timeout;
	/**
	 * 编码格式
	 */
	protected String charset;

	protected BaseHttpClient(String url, int timeout, String charset) {
		this.url = url;
		this.timeout = timeout;
		if (charset == null || "".equals(charset)) {
			charset = "UTF-8";
		}
		this.charset = charset;
		
		isTrace = CommonsConfig.getBoolean("http.isTrace", true);
		int _sn = sn.addAndGet(1) ;
		if (_sn == 999) sn.set(0);
		name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new DecimalFormat("000").format(_sn);
	}

	protected String sendByGet(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		
		// 设置请求地址		
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".get.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpGet httpGet = new HttpGet(this.url + "?" + dataStr);
		httpGet.addHeader("Content-Type","application/x-www-form-urlencoded; charset=" + this.charset);// 设置请求的编码方式
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpGet.setHeader("Connection","close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".get.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + dataStr + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}

	protected String sendByPost(Map<String, String> paraMap) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		
		//组装POST参数、
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		if(null!=paraMap&&paraMap.size()>0){
			Iterator<String> it=paraMap.keySet().iterator();
			while(it.hasNext()){
				String key=it.next();
				String value=paraMap.get(key);
				list.add(new BasicNameValuePair(key, value));
			}
		}

		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ nameValuePairListToString(list) +"】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTP PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	protected String sendByPost(List<NameValuePair> list) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ nameValuePairListToString(list) +"】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTP PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendByPost(Map<String, String> headerMap,List<NameValuePair> list) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ nameValuePairListToString(list) +"】, heads = 【" + headerMap + "】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			
			if(headerMap!=null && !headerMap.isEmpty()){
				Iterator<String> headerKeySet = headerMap.keySet().iterator();
				while(headerKeySet.hasNext()){
					String headerKey = headerKeySet.next();
					httpPost.setHeader(headerKey, headerMap.get(headerKey));
				}
			}
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTP PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}

	protected String sendByPostWrite(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".postWrite.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new StringEntity(dataStr, this.charset));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".postWrite.resp:"+resp);
					}
					return resp;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendByPostWrite(String dataStr,String parameterFormat ) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".postWrite.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			StringEntity entity=new StringEntity(dataStr, this.charset);
			entity.setContentType(parameterFormat);
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".postWrite.resp:"+resp);
					}
					return resp;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected byte[] sendByWriteByte(byte[] bytes) throws EStockThirdException {
		String dataStr = new String(bytes);
		CloseableHttpClient httpClient = createHttpClient(false);
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".writeBytes.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new ByteArrayEntity(bytes));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toByteArray(httpEntity);
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected byte[] sendByWriteByte(Map<String, String> headerMap,byte[] bytes) throws EStockThirdException {
		String dataStr = new String(bytes);
		CloseableHttpClient httpClient = createHttpClient(false);
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".writeBytes.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new ByteArrayEntity(bytes));
			
			if(headerMap!=null && !headerMap.isEmpty()){
				Iterator<String> headerKeySet = headerMap.keySet().iterator();
				while(headerKeySet.hasNext()){
					String headerKey = headerKeySet.next();
					httpPost.setHeader(headerKey, headerMap.get(headerKey));
				}
			}
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toByteArray(httpEntity);
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected byte[] sendByWrite(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(false);
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".write.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new StringEntity(dataStr,this.charset));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toByteArray(httpEntity);
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendHttpsByPost(List<NameValuePair> list) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ nameValuePairListToString(list) +"】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected byte[] sendHttpsByGetResponsBytes(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpGet httpGet = new HttpGet(url + "?" + dataStr);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpGet.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					byte[] respBytes = EntityUtils.toByteArray(httpEntity);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:" + new String(respBytes));
					}
					return respBytes;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + url + "?" + dataStr + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + url + "?" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + url + "?" + dataStr + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendHttpsByGet(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpGet httpGet = new HttpGet(url + "?" + dataStr);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpGet.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + url + "?" + dataStr + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + url + "?" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + url + "?" + dataStr + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendHttpsByPostWriter(String dataStr) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,dataStr = 【"+ dataStr +"】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new StringEntity(dataStr, this.charset));
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + url + "?" + dataStr + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + url + "?" + dataStr + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + url + "?" + dataStr + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendHttpsWithMultipartByPost(Map<String, String> paramMap, File file, String fileKeyName) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		String filePath = file==null?null:file.getAbsolutePath();
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ paramMap +"】,file = 【" + filePath + "】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		
		FileBody bin = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		// 设置HttpPost数据
		try {
			multipartEntityBuilder.addPart(fileKeyName, bin);
			
			if(paramMap != null){
				Iterator<String> it = paramMap.keySet().iterator();
				while(it.hasNext()){
					String name = it.next();
					multipartEntityBuilder.addTextBody(name, paramMap.get(name));
				}
			}
			
			httpPost.setEntity(multipartEntityBuilder.build());
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + paramMap + "】,file = 【" + filePath + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + paramMap + "】,file = 【" + filePath + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + paramMap + "】,file = 【" + filePath + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
					httpClient.close();
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	protected String sendHttpsByGet(Map<String, String> paramMap, File file) throws EStockThirdException {
		CloseableHttpClient httpClient = createHttpClient(true);
		
		String filePath = file==null?null:file.getAbsolutePath();
		
		// 设置请求地址
		if (isTrace && log.isDebugEnabled()){
			log.debug(name+".post.req:url =【"+ this.url+"】,params = 【"+ paramMap +"】,file = 【" + filePath + "】");
		}
		
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		
		FileBody bin = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		// 设置HttpPost数据
		try {
			multipartEntityBuilder.addPart("media", bin);
			
			if(paramMap != null){
				Iterator<String> it = paramMap.keySet().iterator();
				while(it.hasNext()){
					String name = it.next();
					multipartEntityBuilder.addTextBody(name, paramMap.get(name));
				}
			}
			
			httpPost.setEntity(multipartEntityBuilder.build());
			httpPost.setHeader("Connection","close");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resp = EntityUtils.toString(httpEntity, this.charset);
					if (isTrace && log.isDebugEnabled()){
						log.debug(name+".post.resp:"+resp);
					}
					return resp ;
				}
			}
			throw new EStockThirdException(HTTP_MESSAGE_STATUSERROR, "执行【" + paramMap + "】,file = 【" + filePath + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new EStockThirdException(HTTP_MESSAGE_TIMEOUT, "执行【" + paramMap + "】,file = 【" + filePath + "】时,连接超时");
		} catch(EStockThirdException e){
			throw e;
		}catch (Exception e) {
			throw new EStockThirdException(HTTP_MESSAGE_ERROR, "执行HTTPS PSOT请求【" + paramMap + "】,file = 【" + filePath + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
					httpClient.close();
				} catch (IOException e) {
					log.error("httpEntity.consume error" ,e);
				}
			}
		}
	}
	
	private String nameValuePairListToString(List<NameValuePair> list){
		String str = "";
		for(NameValuePair n : list){
			str += n.getName()+"="+n.getValue()+"&";
		}
		if(str.length()!=0){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	
	private CloseableHttpClient createHttpClient(boolean ishttps) throws EStockThirdException{
		RequestConfig.Builder rcBuilder = RequestConfig.custom();
		rcBuilder.setSocketTimeout(this.timeout);
		rcBuilder.setConnectTimeout(this.timeout);
		rcBuilder.setConnectionRequestTimeout(this.timeout);
		rcBuilder.setExpectContinueEnabled(false);
		
		HttpClientBuilder hcBuilder = HttpClientBuilder.create();
		
		if(ishttps){
			SSLConnectionSocketFactory sslsf = null;
			try{
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
				sslsf = new org.apache.http.conn.ssl.SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				
				hcBuilder.setSSLSocketFactory(sslsf);
			}catch(Exception e){
				log.error("构建HTTPS客户端失败", e);
				throw new EStockThirdException(HTTP_MESSAGE_BUILDCLIENTERROR, "构建HTTPS客户端失败, 未发送下单请求");
			}
		}
		hcBuilder.setDefaultRequestConfig(rcBuilder.build());
		
		return hcBuilder.build();
	}
	
	public abstract Entity sendData(Map<String, String> paramMap);
}