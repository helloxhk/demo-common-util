package com.taiji.common.util.exception;



/**
 * 系统自定义业务异常
 * 
 * @author waking.zy
 *
 */
public class MException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8043085774371355126L;

	/**
	 * 异常ID
	 */
	private int exceptionID;

	public MException(int ID) {
		this(ID, null, null);
	}

	public MException(int ID, String message) {
		this(ID, message, null);
	}

	public MException(int ID, Throwable cause) {
		this(ID, null, cause);
	}

	/**
	 * PSException构造器。
	 * 
	 * @param ID
	 *            int 异常ID，从1001开始。
	 * @param message
	 *            String 异常详细信息。
	 * @param cause
	 */
	public MException(int ID, String message, Throwable cause) {
		super((message != null ? message : "") + (ID != -1 ? "[" + ID + "]" : ""), cause);

		exceptionID = ID;
	}

	/**
	 * 取得异常的ID号。
	 * 
	 * @return int 异常的ID号。
	 */
	public int getExceptionID() {
		return exceptionID;
	}
	
	public MException(String message) {
		this(message, null);
	}
	
	public MException(String message,Throwable cause){
		super(message != null ? message : "", cause);
	}
	
}
