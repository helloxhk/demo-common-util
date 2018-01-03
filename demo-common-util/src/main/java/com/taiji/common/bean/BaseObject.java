package com.taiji.common.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * @title 所有自定义类型的基础类
 * @description 该类可以提供对象信息输出/系统日志输出等公用功能
 * @author waking
 *
 */
public class BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234501994798739500L;
	/**
	 * 生产日志输出器
	 */
	private static Logger logger = Logger.getLogger(BaseObject.class);

	/**
	 * 输出实例信息
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + getClass().getName() + "@" + hashCode() + "[" + getClassInfo(getClass()) + "]｝";
	}

	/**
	 * 获取指定类型信息；
	 * 
	 * @return
	 */
	private String getClassInfo(Class<?> theClass) {
		if (theClass == null) {
			return "";
		}

		StringBuffer classInfo = new StringBuffer();

		//以此取得各个属性信息
		Field[] allFields = theClass.getDeclaredFields();
		for (int i = 0; i < allFields.length; i++) {

			//取得单个域的信息,若域信息为空时(常量域),则跳过该域
			String aFieldInfo = getSingleFieldInfo(allFields[i]);
			if (aFieldInfo == null) {
				continue;
			}

			//保持域信息
			classInfo.append(aFieldInfo);
			classInfo.append(";");
		}

		//输出父类信息
		Class<?> parentClass = theClass.getSuperclass();
		classInfo.append(getClassInfo(parentClass));

		return classInfo.toString();
	}

	/**
	 * 取得单个域详情(静态常量域返回空)
	 * 
	 * @param aField
	 * @return
	 */
	private String getSingleFieldInfo(Field aField) {
		//若当前域为常量域,则返回空信息
		int modifiers = aField.getModifiers();
		if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
			return null;
		}

		//获取域属性名和属性值
		String fieldName = aField.getName();
		Object fieldValue = null;
		try {
			aField.setAccessible(true);
			fieldValue = aField.get(this);
		} catch (Exception e) {
			logger.error("无法获取域信息：[className=" + getClass().getName() + " fieldName=" + fieldName + "]", e);
			fieldValue = "取得属性信息发生异常：" + e.getMessage();
		}

		//字符串以特殊形式输出
		if (fieldValue instanceof String) {
			fieldValue = "\"" + fieldValue + "\"";
		}

		//返回属性信息
		return fieldName + "=" + fieldValue;
	}

}
