package com.taiji.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taiji.common.util.string.StringUtils;
import com.taiji.common.util.string.ZipStrUtil;
import com.taiji.common.utils.Base64Coder;
import com.taiji.common.utils.HexPlus;

public class BaseSerializerUtil {

	private static Logger log = Logger.getLogger(BaseSerializerUtil.class);
	
	public static byte[] listToByteArray(List<Object> value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			for (Object bd : value) {
				os.writeObject(bd);
			}
			os.writeObject(null);
			rv = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		return rv;
	}

	public static List<Object> byteArrayToList(byte[] in) {
		List<Object> list = new ArrayList<Object>();
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				while (true) {
					Object bd = is.readObject();
					if (bd == null) {
						break;
					} else {
						list.add(bd);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("byteArrayToList Exception!" ,e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		return list;
	}

	public static byte[] objectToByteArray(Object o) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(outputStream);
			oos.writeObject(o);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("objectToByteArray Exception!" ,e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}

	public static String objectToString(Object o) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(outputStream);
			oos.writeObject(o);
			return StringUtils.bytes2Hex(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("objectToByteArray Exception!" ,e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}

	public static Object byteArrayToObject(byte[] bytes) {
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("byteArrayToObject Exception!");
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}

	public static Object StringToObject(String objStr) {
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		try {
			byte[] bytes = StringUtils.hex2Bytes(objStr);
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("StringToObject Exception!");
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}

	public static String objectToBase64Str(Object obj){
		if(obj == null){
			throw new NullPointerException("Can't Convert null");
		}
		return Base64Coder.encoderBASE64(objectToByteArray(obj), true);
	}
	
	public static Object base64StrToObject(String hexStr){
		try {
			return byteArrayToObject(Base64Coder.decodeBASE64(hexStr));
		} catch (Exception e) {
			throw new RuntimeException("Convert Exception!", e);
		}
	}
	
	public static String objectToBase64AndZipStr(Object obj){
		String jsonObjStr = JSON.toJSONString(obj);
		try {
			jsonObjStr = ZipStrUtil.compress(Base64Coder.encoderBASE64(jsonObjStr.getBytes("UTF-8"), true));
		} catch (Exception e) {
			throw new RuntimeException("Zip Or Base64 Exception!", e);
		}
		
		return jsonObjStr;
	}
	
	public static JSONObject base64AndZipStrToObject(String zipStr){
		JSONObject jsonObj;
		try {
			String jsonStr = new String(Base64Coder.decodeBASE64(ZipStrUtil.unCompress(zipStr)),"UTF-8");
			jsonObj = JSON.parseObject(jsonStr);
		} catch (Exception e) {
			throw new RuntimeException("UnZip Or DecodeBase64 Exception!", e);
		}
		return jsonObj;
	}
	
	public static String objectToBase64AndZipStr_bak(Object obj){
		try {
			return ZipStrUtil.compress(objectToBase64Str(obj));
		} catch (IOException e) {
			throw new RuntimeException("Zip Or Base64 Exception!", e);
		}
	}
	
	public static Object base64AndZipStrToObject_bak(String base64Str){
		try {
			return base64StrToObject(ZipStrUtil.unCompress(base64Str));
		} catch (Exception e) {
			throw new RuntimeException("UnZip Or DecodeBase64 Exception!", e);
		}
	}
	
	public static String objectToHexStr(Object obj){
		if(obj == null){
			throw new NullPointerException("Can't Convert null");
		}
		return HexPlus.encode(objectToByteArray(obj));
	}
	
	public static Object hexStrToObject(String hexStr){
		try {
			return byteArrayToObject(HexPlus.decode(hexStr));
		} catch (Exception e) {
			throw new RuntimeException("Convert Exception!", e);
		}
	}
	
	public static void main(String[] args) {
		try {
			String zipStr = "H4sIAAAAAAAAAFVSW4+iMBT+SxR0sjyqVCyZtkFBoG+UbsaWy5gocvn1e4o7WfaJ0PZ81/N7irTKmZHuuSHmW9NkN9BgNxI96LL177Jl30W27UU2aK6jJMV+Yu9k6HclnBcZWuaKFjkwN1BNHqRFTfWecwrXr8WFfBCDHZ7ggSZ0y0zt0LnQn4foKfLIlOGxV2FTWxyWFPOCn7NOtr80N3i07ypXzSLHVsOn1UcD7NEgnuA7s6RGPIjhrPB4kE7ULNivqj2DDh98XI06kI/Me+jqFL3ECf7D6AYaXyJMAZO4PCEjDzDiSTGw4MvODzBbq4z989Ax0E9mFqQum7+29ABeu/0E2aHCO98FcFCz26xnZXd9gq9xfQYa7oULvCbegJc1BpJts8psf7FZqFN0kx27ycxHsov/zu39/DLoCryJrBlUeKwF4FoNkPXEA2I9TCI/IpGDhwy9FPQBWhwOPcHdXGaqt5lXbtNJ21sHer2oKbPYZmKztRgOdPSErm8yRD/4sCN4pAa7zOz+f3Ni/XuP6Aw8kOf7vmqPfeX+5L10OF/tvlws73mCXZltN6pt+iUb4F95X3e/ofgB/BS6qKArYncKeNItTypEg9qF/4kdIv8PEWIqFdwCAAA=";
			System.out.println(BaseSerializerUtil.base64AndZipStrToObject(zipStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
