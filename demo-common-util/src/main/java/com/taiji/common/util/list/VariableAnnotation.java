package com.taiji.common.util.list;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于Get方法,标记是否需要加入FastSearchList快速检索
 * @author waking
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VariableAnnotation {
	
	public String variablename();
}
