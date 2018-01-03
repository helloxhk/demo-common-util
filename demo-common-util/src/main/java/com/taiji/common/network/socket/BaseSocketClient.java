package com.taiji.common.network.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.kalian.thirdchannel.commons.utils.CommonsConfig;
import com.kalian.thirdchannel.commons.utils.HexPlus;
import com.kalian.thirdchannel.commons.utils.StringUtils;

/**
 * Socket请求基类
 * 
 * @author zhouy
 *
 */
public class BaseSocketClient {

	protected String ip;
	
	protected int port;
	
	protected int timeout;
	
	private static AtomicInteger sn = new AtomicInteger(0) ;
	private boolean isTrace = true;
	private String name = null;
	
	private static Logger log = Logger.getLogger(BaseSocketClient.class);
	
	public BaseSocketClient(String ip, int port, int timeout){
		this.ip = ip;
		this.port = port;
		this.timeout = timeout;
		
		isTrace = CommonsConfig.getBoolean("http.isTrace", true);
		int _sn = sn.addAndGet(1) ;
		if (_sn == 999) sn.set(0);
		name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new DecimalFormat("000").format(_sn);
	}
	
	/**
	 * 
	 * @param msgbytes
	 * @return
	 */
	public byte[] sendDataWithHeadDecimalLength(byte[] msgbytes, int HeadDecimalLength, boolean includeLengthBytes){
		byte[] respPack = null;
		byte[] allRespPack = null;
		byte[] len_bs = new byte[HeadDecimalLength];
		Socket connection = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			connection = new Socket(ip, port);
			connection.setSoTimeout(timeout);
			
			String msgLength = StringUtils.leftFillZero(msgbytes.length + "", HeadDecimalLength);
			
			msgbytes = ArrayUtils.addAll(msgLength.getBytes(), msgbytes);
			
			if (isTrace && log.isDebugEnabled()){
				log.debug(name+".req:address =【"+ this.ip + "：" + this.port +"】,data = 【"+ HexPlus.encode(msgbytes) +"】");
			}
			
			//设置请求报文
			out = connection.getOutputStream();
			out.write(msgbytes);
			out.flush();
			
			//取得响应报文
			in = connection.getInputStream();
			
            while (in.available() == 0) {
            	Thread.sleep(1L);
            }
			
			//取得报文长度
            in.read(len_bs);
            String resplengthStr = new String(len_bs);
            int lenValue = new Integer(resplengthStr);
			//取得报文内容
			respPack = new byte[lenValue];
			in.read(respPack, 0, lenValue);
			allRespPack = ArrayUtils.addAll(len_bs, respPack);
			
			if(includeLengthBytes){
				respPack = allRespPack;
			}
			
			if (isTrace && log.isDebugEnabled()){
				log.debug(name+".resp:" + HexPlus.encode(allRespPack));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("发送socket.sendDataWithHead4DecimalLength请求时发生异常", e);
		} finally {
			//关闭Socket
			try {
				//关闭流
				out.close();
				in.close();
				connection.close();
			} catch (Exception e) {
				throw new RuntimeException("关闭数据流出错", e);
			}
		}
		//返回响应报文
		return respPack;
	}
	
	/**
	 * 
	 * @param msgbytes
	 * @return
	 */
	public byte[] sendDataWithHeadTwoLength(byte[] msgbytes){
		byte[] respPack = null;
        byte[] len_bs = new byte[2];
        Socket connection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            connection = new Socket(ip, port);
            connection.setSoTimeout(timeout);

            int message_length = msgbytes.length;
            len_bs[0] = (byte) (message_length / 256);
            len_bs[1] = (byte) (message_length & 0xFF);
            
            msgbytes = ArrayUtils.addAll(len_bs, msgbytes);
            
			if (isTrace && log.isDebugEnabled()){
				log.debug(name+".req:address =【"+ this.ip + "：" + this.port +"】,data = 【"+ HexPlus.encode(msgbytes) +"】");
			}
            
            //设置请求报文
            out = connection.getOutputStream();
            out.write(msgbytes);
            out.flush();

            //取得响应报文
            in = connection.getInputStream();
            //取得报文长度
            in.read(len_bs, 0, 2);
            //System.out.println("返回的长度：" + new String(len_bs));
            int high = len_bs[0] & 0xFF;
            int low = len_bs[1] & 0xFF;
            int lenValue = high * (int) 256 + low;
            //取得报文内容
            respPack = new byte[lenValue];
            in.read(respPack, 0, lenValue);
            
            respPack = ArrayUtils.addAll(len_bs, respPack);
            
            if (isTrace && log.isDebugEnabled()){
				log.debug(name+".resp:" + HexPlus.encode(respPack));
			}
            
        } catch (Exception e) {
        	throw new RuntimeException("发送socket.sendDataWithHeadTwoLength请求时发生异常", e);
        } finally {
            //关闭Socket
            try {
            	//关闭流
            	out.close();
            	in.close();
                connection.close();
            } catch (Exception e) {
            	throw new RuntimeException("关闭数据流出错", e);
            }
        }
        //返回响应报文
        return respPack;
	}
	
	/**
	 * 
	 * @param msgbytes
	 * @return
	 */
	public byte[] sendDataWithResponsHeadTwoLength(byte[] msgbytes){
		byte[] respPack = null;
        byte[] len_bs = null;
        Socket connection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            connection = new Socket(ip, port);
            connection.setSoTimeout(timeout);

            if (isTrace && log.isDebugEnabled()){
				log.debug(name+".req:address =【"+ this.ip + "：" + this.port +"】,data = 【"+ HexPlus.encode(msgbytes) +"】");
			}
            
            //设置请求报文
            out = connection.getOutputStream();
            out.write(msgbytes);
            out.flush();

            //取得响应报文
            in = connection.getInputStream();
            //取得报文长度
            len_bs = new byte[2];
            in.read(len_bs, 0, 2);
            //System.out.println("返回的长度：" + new String(len_bs));
            int high = len_bs[0] & 0xFF;
            int low = len_bs[1] & 0xFF;
            int lenValue = high * (int) 256 + low;
            //取得报文内容
            respPack = new byte[lenValue];
            in.read(respPack, 0, lenValue);
            respPack = ArrayUtils.addAll(len_bs, respPack);
            
            if (isTrace && log.isDebugEnabled()){
				log.debug(name+".resp:" + HexPlus.encode(respPack));
			}
        } catch (Exception e) {
        	throw new RuntimeException("发送socket.sendDataWithResponsHeadTwoLength请求时发生异常", e);
        } finally {
            //关闭Socket
            try {
            	//关闭流
            	out.close();
            	in.close();
                connection.close();
            } catch (Exception e) {
            	throw new RuntimeException("关闭数据流出错", e);
            }
        }
        //返回响应报文
        return respPack;
	}
	
	/**
	 * 
	 * @param msgbytes
	 * @return
	 */
	public byte[] sendData(byte[] msgbytes){
		byte[] respPack = null;
        Socket connection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            connection = new Socket(ip, port);
            connection.setSoTimeout(timeout);

            if (isTrace && log.isDebugEnabled()){
				log.debug(name+".req:address =【"+ this.ip + "：" + this.port +"】,data = 【"+ HexPlus.encode(msgbytes) +"】");
			}
            //设置请求报文
            out = connection.getOutputStream();
            out.write(msgbytes);
            out.flush();

            //取得响应报文
            in = connection.getInputStream();
            int dataLength = in.available();
            while (dataLength == 0) {
            	Thread.sleep(1L);
            	dataLength = in.available();
            }
            respPack = new byte[dataLength];
            in.read(respPack, 0, dataLength);
            
            if (isTrace && log.isDebugEnabled()){
				log.debug(name+".resp:" + HexPlus.encode(respPack));
			}
        } catch (Exception e) {
        	throw new RuntimeException("发送socket.sendData请求时发生异常", e);
        } finally {
            //关闭Socket
            try {
            	//关闭流
            	out.close();
            	in.close();
                connection.close();
            } catch (Exception e) {
            	throw new RuntimeException("关闭数据流出错", e);
            }
        }
        //返回响应报文
        return respPack;
	}
}
