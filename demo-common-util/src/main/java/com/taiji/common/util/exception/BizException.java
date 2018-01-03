package com.taiji.common.util.exception;

public class BizException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private long exceptionId = -1 ;

	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}
	
	public BizException(long exceptionId) {
		super("BizException[exId="+exceptionId+"]");
		
		this.exceptionId = exceptionId ;
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(long exceptionId, String message) {
		super(message);
		this.exceptionId = exceptionId ;
	}
	
	public BizException(long exceptionId, String message, Throwable cause) {
		super(message, cause);
		this.exceptionId = exceptionId ;
	}
	
	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BizException(long exceptionId, Throwable cause) {
		super("BizException[exId="+exceptionId+"]", cause);
	}
	
	public long getExceptionId()
	{
		return exceptionId ;
	}
}
