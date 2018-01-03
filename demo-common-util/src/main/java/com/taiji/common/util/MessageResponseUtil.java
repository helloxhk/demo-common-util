/**
 *
 *  Create On 2015年12月31日 下午1:19:53
 *  auther: huoshan
 *  
 */
package com.taiji.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huoshan
 *
 */
public class MessageResponseUtil<T> {
	
	private Long code = -1L;
	private String message = "失败";
	private List<T> data = new ArrayList<T>();
	
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
