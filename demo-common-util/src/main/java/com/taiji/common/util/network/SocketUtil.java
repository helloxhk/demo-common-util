package com.taiji.common.util.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.lang.ArrayUtils;

public class SocketUtil {

	/**
	 * 向ip:port 发送数据sendStr
	 * 
	 * @param sendStr
	 * @param ip
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public String sendBySocket(String sendStr, String ip, String port) throws IOException {
		byte[] respPack = null;
		byte[] fullRespPack = new byte[0];
		int respLength = 0;
		Socket connection = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] respLengthByte = new byte[8];
		
		try {
			connection = new Socket(ip, Integer.parseInt(port));
			connection.setSoTimeout(60000);

			// 设置请求报文
			out = connection.getOutputStream();
			out.write(sendStr.getBytes());
			out.flush();

			// 取得响应报文
			in = connection.getInputStream();
			int tmpLength;
			int i = 0;
			while (fullRespPack.length - 8 < respLength) {
				Thread.sleep(100L);
				tmpLength = in.available() - 8;
				if(tmpLength  > 0){
					if(i == 0){
						in.read(respLengthByte, 0, 8);
						respLength = new Integer(new String(respLengthByte));
						fullRespPack = ArrayUtils.addAll(respLengthByte, null);
						
						i++;
					}
					
					respPack = new byte[tmpLength];
					in.read(respPack, 0, tmpLength);
					fullRespPack = ArrayUtils.addAll(fullRespPack, respPack);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("发送socket.sendData请求时发生异常", e);
		} finally {
			// 关闭Socket
			try {
				// 关闭流
				out.close();
				in.close();
				connection.close();
			} catch (Exception e) {
				throw new RuntimeException("关闭数据流出错", e);
			}
		}
		// 返回响应报文
		return new String(fullRespPack);
	}
	
}
