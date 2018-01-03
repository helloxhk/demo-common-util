package com.taiji.common.util.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	/**
	 * 解析json
	 * @param resText
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,Object> parseJosn(String resText) throws Exception{
		// 将json字符串转成json对象  
	    JSONObject  jsonObject = JSONObject.parseObject(resText);
	    Iterator iter = jsonObject.keySet().iterator();  
	    Map<String,Object> map = new HashMap<String,Object>();  
	    while (iter.hasNext()) {  
		    String key = (String) iter.next();  
		    String value = jsonObject.getString(key);  
			map.put(key, value);  
	     }  
		return map;
	}
}
