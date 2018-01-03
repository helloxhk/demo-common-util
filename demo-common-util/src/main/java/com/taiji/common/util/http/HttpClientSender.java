package com.taiji.common.util.http;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.taiji.common.util.exception.BizException;
/**
 * HTTP请求基类(基于httpclient4.0)
 * 
 * @author estock
 * 
 */
public  class HttpClientSender {
	protected static Logger log = Logger.getLogger(HttpClientSender.class);

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

	public HttpClientSender(String url, int timeout, String charset) {
		this.url = url;
		this.timeout = timeout;
		if (charset == null || "".equals(charset)) {
			charset = "UTF-8";
		}
		this.charset = charset;
	}

	public String sendByGet(String dataStr) throws BizException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
		HttpGet httpGet = new HttpGet(this.url + "?" + dataStr);
		httpGet.addHeader("Content-Type","application/x-www-form-urlencoded; charset=" + this.charset);// 设置请求的编码方式
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, this.charset);
				}else{
					return "";
				}
			}
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"
					+ dataStr + "】时,通讯状态异常,状态码【"
					+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}

	public String sendByPost(List<NameValuePair> list) throws BizException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, this.charset);
				}else{
					return "";
				}
			}
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行HTTP PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public String sendHttpsByPost(List<NameValuePair> list) throws BizException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
		HttpPost httpPost = new HttpPost(url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			if("https".equals(new URL(url).getProtocol())){
				SSLContext sslContext=getDefaultSSLContext();
				SSLSocketFactory sslSocketFactory=new SSLSocketFactory(sslContext);
				sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme scheme=new Scheme("https",sslSocketFactory,(new URL(url).getPort() == -1) ? new URL(url).getDefaultPort() : new URL(url).getPort());
				httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(list, this.charset));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, this.charset);
				}else{
					return "";
				}
			}
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ list + "】时,通讯状态异常,状态码【"	+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + list + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行HTTP PSOT请求【" + list + "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	

	public String sendByPostWrite(String dataStr) throws BizException {
		log.debug("发送的报文内容["+dataStr+"]");
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
		HttpPost httpPost = new HttpPost(this.url);
		HttpEntity httpEntity = null;
		// 设置HttpPost数据
		try {
			httpPost.setEntity(new StringEntity(dataStr, this.charset));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String s = EntityUtils.toString(httpEntity, this.charset);
					System.out.println("返回的报文内容["+s+"]");
					return s;
				}
			}
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	
	
	public byte[] sendByWriteByte(byte[] bytes) throws BizException {
		String dataStr = new String(bytes);
		log.debug("发送的报文内容["+dataStr+"]");
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
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
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public byte[] sendByWrite(String dataStr) throws BizException {
		log.debug("发送的报文内容["+dataStr+"]");
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout",this.timeout);// 设置数据等待时间
		httpClient.getParams().setIntParameter("http.connection.timeout",this.timeout);// 设置连接超时时间
		// 设置请求地址
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
			throw new BizException(HTTP_MESSAGE_STATUSERROR, "执行【"	+ dataStr + "】时,通讯状态异常,状态码【"+ httpResponse.getStatusLine().getStatusCode() + "】");
		} catch (ConnectException e) {
			throw new BizException(HTTP_MESSAGE_TIMEOUT, "执行【" + dataStr + "】时,连接超时");
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			throw new BizException(HTTP_MESSAGE_ERROR, "执行【" + dataStr	+ "】时,发生通讯异常", e);
		} finally {
			if (httpEntity != null) {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					log.error("httpEntity.consumeContent" ,e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	private SSLContext getDefaultSSLContext(){
		SSLContext sslContext=null;
		try {
			sslContext=SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new TrustManager[]{new X509TrustManager(){

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				
				}
				
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				
				}

				public X509Certificate[] getAcceptedIssuers() {
					return getX509Certificates();
				}
				
			}}, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sslContext;
	}
	
	private class DefaultTrustManager implements X509TrustManager{

		
		
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[]{};
		}
		
	}
	
	private  X509Certificate[] getX509Certificates(){
		return new X509Certificate[]{};
	}
	
	public static void main(String[] args) {
		
	}
}