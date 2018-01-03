package com.taiji.common.util.security;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.taiji.common.util.exception.SystemException;

/**
 * @title 消息资源句柄默认实现
 * @description
 * @author Lincoln
 */
public class PropertiesFileHandlerImpl implements PropertiesFileHandler {

	/**
	 * 存储资源的属性对象;
	 */
	ResourceBundle resourceBundle;

	/**
	 * 构造消息资源句柄
	 * 
	 * @param resourceInClasspath
	 */
	public PropertiesFileHandlerImpl(String resourceInClasspath) {
		resourceBundle = ResourceBundle.getBundle(resourceInClasspath);
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getStringCanNull(java.lang.String, java.lang.Object[])
	 */
	public String getStringCanNull(String key, Object... args) {
		String resource;
		try {
			resource = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}

		return MessageFormat.format(resource, args);
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.base.application.messages.MessageResourceHandler#getMessage(java.lang.String, java.lang.Object[])
	 */
	public String getString(String key, Object... args) {
		String stringCanNull = getStringCanNull(key, args);
		if (stringCanNull == null) {
			throw new SystemException("未能找到[" + key + "]对应的消息");
		} else {
			return stringCanNull;
		}

	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getBoolean(java.lang.String, java.lang.Object[])
	 */
	public Boolean getBoolean(String key, Object... args) {
		return Boolean.parseBoolean(getString(key, args));
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getDouble(java.lang.String, java.lang.Object[])
	 */
	public Double getDouble(String key, Object... args) {
		return Double.parseDouble(getString(key, args));
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getFloat(java.lang.String, java.lang.Object[])
	 */
	public Float getFloat(String key, Object... args) {
		return Float.parseFloat(getString(key, args));
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getInteger(java.lang.String, java.lang.Object[])
	 */
	public Integer getInteger(String key, Object... args) {
		return Integer.parseInt(getString(key, args));
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getLong(java.lang.String, java.lang.Object[])
	 */
	public Long getLong(String key, Object... args) {
		return Long.parseLong(getString(key, args));
	}

	/* (non-Javadoc)
	 * @see com.estock.commons.propertiesfile.PropertiesFileHandler#getStringList(java.lang.String, java.lang.Object[])
	 */
	public String[] getStringList(String key, Object... args) {
		return getString(key, args).split(",");
	}

	/**
	 * 测试用主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PropertiesFileHandler propertiesFileHandler = new PropertiesFileHandlerImpl("META-INF/bc_commons");
		System.out.println(propertiesFileHandler.getStringCanNull("bc.commons.lincoln.2", "aaa"));
	}
}
