package com.taiji.common.util.maptobean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.taiji.common.util.string.StringUtils;

/**
 * @author 
 * @version 
 * @variable 
 * @describe map到bean
 */
public class ZJ_BeanToMapUtils {
	private static Logger log = Logger.getLogger(ZJ_BeanToMapUtils.class);
	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * @param <T>
	 * @param clazz 要转化的类型
	 * @param map 包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException 如果分析类属性失败
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败
	 * @throws InstantiationException 如果实例化 JavaBean 失败
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */
	public static <T> T toBean(Class<T> clazz, Map<Object, Object> map) {
		T obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			obj = clazz.newInstance(); // 创建 JavaBean 对象

			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				Class<?> propertyType = descriptor.getPropertyType();
				log.debug("propertyType=="+propertyType);
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					log.debug("获取到map中的值："+propertyName+"="+value);
					if (StringUtils.isBlank(value + "")) {
						value = null;
					}else{
						if(propertyType == Integer.class){
							value = new Integer(value+"");
						}else if(propertyType == boolean.class){
							value = new Boolean(value+"");
						}else if(propertyType == Long.class){
							value = new Long(value+"");
						}
					}
					
					Object[] args = new Object[1];
					args[0] = value;
					try {
						descriptor.getWriteMethod().invoke(obj, args);
					} catch (InvocationTargetException e) {
						log.debug("字段映射失败");
					}
				}
			}
		} catch (IllegalAccessException e) {
			log.error("实例化 JavaBean 失败",e);
		} catch (IntrospectionException e) {
			log.error("分析类属性失败",e);
		} catch (IllegalArgumentException e) {
			log.error("映射错误",e);
		} catch (InstantiationException e) {
			log.error("实例化 JavaBean 失败",e);
		}
		log.debug("返回对象："+obj);
		return (T) obj;
	}
	
	public static Map<Object, Object> toMap(Object bean) {
		Class<? extends Object> clazz = bean.getClass();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = null;
					result = readMethod.invoke(bean, new Object[0]);
					if (null != propertyName) {
						propertyName = propertyName.toString();
					}
					if (null != result) {
						result = result.toString();
					}
					returnMap.put(propertyName, result);
				}
			}
		} catch (IntrospectionException e) {
			log.error("分析类属性失败",e);
		} catch (IllegalAccessException e) {
			log.error("实例化 JavaBean 失败",e);
		} catch (IllegalArgumentException e) {
			log.error("映射错误",e);
		} catch (InvocationTargetException e) {
			log.error("调用属性的 setter 方法失败",e);
		}
		return returnMap;
	}
	
}
