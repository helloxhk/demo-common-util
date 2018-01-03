/**
 *
 *  Create On 2015年12月30日 下午9:24:19
 *  auther: huoshan
 *  
 */
package com.taiji.common.util.validate;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author huoshan
 * Hibernate Validate校验工具类
 */
public class BeanValidateUtil {
	
	/**
	 * 校验对象
	 * @param obj  要校验的对象
	 * @return
	 */
	public static String validateModel(Object obj) {//验证某一个对象
			
			StringBuffer buffer = new StringBuffer();//用于存储验证后的错误信息
			
			Validator validator = Validation.buildDefaultValidatorFactory()
					.getValidator();
	
			Set<ConstraintViolation<Object>> constraintViolations = validator
					.validate(obj);//验证某个对象,，其实也可以只验证其中的某一个属性的
	
			Iterator<ConstraintViolation<Object>> iter = constraintViolations
					.iterator();
			while (iter.hasNext()) {
				String message = iter.next().getMessage();
				buffer.append(message);
			}
			return buffer.toString();
		}
	
	/**
	 * 校验属性
	 * @param obj		属性所属于的对象
	 * @param propName 要校验的属性名字符串
	 * @return
	 */
	public static String validateProperty(Object obj , String propName){
		
		StringBuffer buffer = new StringBuffer();//用于存储验证后的错误信息
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(obj,propName);
		
		Iterator<ConstraintViolation<Object>> iter = constraintViolations
				.iterator();
		while (iter.hasNext()) {
			String message = iter.next().getMessage();
			buffer.append(message);
		}
		return buffer.toString();
	}
	
}



