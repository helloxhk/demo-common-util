package com.taiji.common.utils;

import org.apache.commons.codec.binary.Hex;

public class HexPlus
{
	public static String encode(byte[] data)
	{
		return new String(Hex.encodeHex(data)) ;
	}
	
	public static byte[] decode(String data) throws Exception
	{
		return Hex.decodeHex(data.toCharArray()) ;
	}
}
