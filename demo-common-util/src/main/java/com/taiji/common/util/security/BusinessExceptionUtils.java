/**
 * 
 */
package com.taiji.common.util.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.taiji.common.util.exception.SystemException;
import com.taiji.common.util.string.StringUtils;

/**
 * @title 业务异常工具类
 * @description 用以从资源文件获取业务信息
 * @author Lincoln
 */

public class BusinessExceptionUtils {

	/**
	 * 系统日志输出句柄
	 */
	private static Logger logger = Logger.getLogger(BusinessExceptionUtils.class);

	private static String[] bcFilePathArr;

	private static List<PropertiesFileHandler> messageResourceHandlerList;
	
	static {
		//取得保护业务代码文件的路径信息
		PropertiesFileHandler localConfigHandler = new PropertiesFileHandlerImpl("META-INF/lc_commons");
		bcFilePathArr = localConfigHandler.getStringList("lc.commons.bcFilePathArr");

		//初始化各个业务代码表
		messageResourceHandlerList = new ArrayList<PropertiesFileHandler>();
		for (int i = 0; i < bcFilePathArr.length; i++) {
			try {
				messageResourceHandlerList.add(new PropertiesFileHandlerImpl(bcFilePathArr[i]));
				System.out.println("...已绑定资源文件["+bcFilePathArr[i]+"]");
			} catch (Throwable t) {
				logger.error("绑定资源文件[" + bcFilePathArr[i] + "]时发生错误[" + t.getMessage() + "],现已跳过该文件!");
			}
		}
	}

	/**
	 * 获取业务异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getBusinessInfo(BusinessException e) {
		return getBusinessInfo(e.getBusinessCode(), e.getArgs());
	}

	/**
	 * 获取业务异常信息
	 * 
	 * @param businessCode 业务信息代码
	 * @param businessArgs 业务信息参数
	 * @return
	 */
	public static String getBusinessInfo(String businessCode, Object[] businessArgs) {
		for (PropertiesFileHandler messageResourceHandler : messageResourceHandlerList) {
			String message = messageResourceHandler.getStringCanNull(businessCode, businessArgs);
			if (message != null) {
				return message;
			}
		}

		throw new SystemException("未能从资源[" + StringUtils.toString(bcFilePathArr) + "]中找到业务异常[" + businessCode + "]对应的业务信息");
	}

	public static void main(String[] args) {
		System.out.println(getBusinessInfo(new BusinessException("bc.commons.lincoln.1", 1)));
	}
}
