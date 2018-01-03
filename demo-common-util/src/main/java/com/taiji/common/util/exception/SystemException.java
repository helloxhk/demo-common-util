package com.taiji.common.util.exception;

/**
 * @title 
 * @description
 * @author Lincoln
 */
public class SystemException extends RuntimeException {

	/**
	 * 
	 */
	public SystemException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public SystemException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);

	}
}
