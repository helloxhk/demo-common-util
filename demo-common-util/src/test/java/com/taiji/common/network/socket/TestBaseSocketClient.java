package com.taiji.common.network.socket;

import org.junit.Test;

import com.taiji.common.network.socket.BaseSocketClient;

public class TestBaseSocketClient {

	@Test
	public void test() {
		BaseSocketClient b = new BaseSocketClient("111.1.1.230", 1235, 60000);
		byte[] bytes = b.sendDataWithHeadTwoLength("K0S0006T0006Y".getBytes());
		/*
		 * 2
		 * 2
		 * 2
		 * 32
		 * 32
		 * 16
		 */
		byte[] result = new byte[2];
		System.arraycopy(bytes, 4, result, 0, result.length);
		System.out.println(new String(result));
		byte[] sekbytes = new byte[32];
		System.arraycopy(bytes, 4 + result.length, sekbytes, 0, sekbytes.length);
		System.out.println(new String(sekbytes));
		byte[] tekbytes = new byte[32];
		System.arraycopy(bytes, 4 + result.length + sekbytes.length, tekbytes, 0, tekbytes.length);
		System.out.println(new String(tekbytes));
		byte[] checkbytes = new byte[16];
		System.arraycopy(bytes, 4 + result.length + sekbytes.length + tekbytes.length, checkbytes, 0, checkbytes.length);
		System.out.println(new String(checkbytes));
		
	}
}
