package com.taiji.common.util.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title 类常量标签
 * @description
 * @usage
 * @copyright Copyright 2009 Estock Corporation. All rights reserved.
 * @company Estock Corporation.
 * @author feixuerong <DongCheng.Lin@estock.com.cn>
 * @version $Id: ConstantTag.java,v 1.1 2009/07/14 02:21:33 lindc Exp $
 * @create 2009-4-3 下午05:05:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ConstantTag {

	/**
	 * 常量域的名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 常量的类型
	 * 
	 * @return
	 */
	String type();

}