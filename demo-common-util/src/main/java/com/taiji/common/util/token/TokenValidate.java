package com.taiji.common.util.token;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.taiji.common.util.security.BusinessException;
import com.taiji.common.util.security.CommonConstants;
import com.taiji.common.util.time.DateTimeUtils;

/**
 * 验证登录token公共类
 * @author 
 *
 */
public class TokenValidate {
	
	private static Logger log=Logger.getLogger(TokenValidate.class);
	
	/**
	 * token处理
	 * @param loginno
	 * @throws BusinessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static  String getToken(ConcurrentHashMap<String, Map<String, Object>> map,String loginno,String token) throws BusinessException{
		
		//取得今天日期
		String toDay=DateTimeUtils.getCurrentDate();
		
		//根据登录账号取得token map信息
		Map<String, Object> tokenMap=(Map) map.get(loginno);
		
		//当登录token map有值时(登录接口或费登录接口验证时)
		if((token==null&&tokenMap!=null) || (token!=null&&tokenMap!=null)){
			String tokenDate = (String) tokenMap.get(loginno);
			String str[] =tokenDate.split("#");
			Long num = DateTimeUtils.dateCompare(str[1],toDay,"yyyy-MM-dd","day");//比较两个日期差
			if(num>=3){
				map.remove(loginno);
				log.error("Token失效"+loginno);
				throw new BusinessException("iphone.web.pay.pos.23", "登录已超时！请重新登录！"+"#"+CommonConstants.LOGIN_TOKEN_TIMEOUT);
			}else{
				return str[0];
			}
		}
		
		//当登录token map为空时(登录接口或费登录接口验证时)
		if((token==null&&tokenMap==null) || (token!=null&&tokenMap==null)){
			tokenMap = new HashMap<String, Object>();
			String tokenstr = TokenProcessor.generateToken(loginno);
			tokenMap.put(loginno, tokenstr+"#"+toDay);
			map.put(loginno, tokenMap);
			return tokenstr;
		}
		return null;
	}
	
	/**
	 * 验证token
	 * @param thirdToken
	 * @param localToken
	 * @return
	 */
	public static boolean checkToken(String token,String redisToken,String desc){
		if(token==null || !token.equals(redisToken)){
			log.warn(desc+"token验证失败,token="+token+",redisToken="+redisToken);
			return false;
		}
		return true;
	}
}
