package com.taiji.common.util.security;

/**
 * @title 业务异常
 * @description 当业务执行过程中,需要提示某些业务信息时抛出该异常
 * @author Lincoln
 */
public class BusinessException extends Exception {

	/**
	 * 业务信息编码
	 */
	private String businessCode;

	/**
	 * 业务信息参数
	 */
	private final Object[] args;

	/**
	 * 业务信息明码
	 */
	private final String businessInfo;
	
	public BusinessException(String errorCode, Throwable t)
	{
		this(errorCode ,new Object[]{t}) ;
	}

	/**
	 * @param errorCode
	 * @param args
	 */
	public BusinessException(String errorCode, Object... theArgs) {
		super(errorCode);
		this.businessCode = errorCode;
		this.args = theArgs;
		int len = 0 ;
		if (args != null) len = args.length;
		for (int i=0 ;i<len ;i++){
			if (args[i] instanceof Throwable){
				super.initCause((Throwable)args[i]);
				
				args[i] = "异常" ;
			}
		}
		this.businessInfo = BusinessExceptionUtils.getBusinessInfo(businessCode, args);
	}

	/**
	 * 获取“args”(类型：Object[])
	 */
	public Object[] getArgs() {
		return args;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		StringBuffer argsBuf = new StringBuffer();
		for (int i = 0; args != null && i < args.length; i++) {
			argsBuf.append(args[i]);
			if (i + 1 < args.length) {
				argsBuf.append(",");
			}
		}

		return "[" + businessCode + "(" + businessInfo + ")]";
	}

	/**
	 * @return the busiInfoCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param busiInfoCode the busiInfoCode to set
	 */
	public void setBusinessCode(String busiInfoCode) {
		this.businessCode = busiInfoCode;
	}

	/**
	 * @return the businessInfo
	 */
	public String getBusinessInfo() {
		return businessInfo;
	}

}
