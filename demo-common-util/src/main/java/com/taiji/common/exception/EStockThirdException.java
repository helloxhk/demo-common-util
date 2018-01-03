package com.taiji.common.exception;

public class EStockThirdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -440887388469248240L;
	
	/**
	 * �쳣ID
	 */
	private int exceptionID;

	public EStockThirdException(int ID) {
		this(ID, null, null);
	}

	public EStockThirdException(int ID, String message) {
		this(ID, message, null);
	}

	public EStockThirdException(int ID, Throwable cause) {
		this(ID, null, cause);
	}

	/**
	 * ֻ�����쳣�����Ĺ�������
	 * 
	 * @param message
	 *            String �쳣��ϸ��Ϣ��
	 */
	public EStockThirdException(String message) {
		this(-1, message, null);
	}

	public EStockThirdException(String message, Throwable cause) {
		this(-1, message, cause);
	}

	public EStockThirdException(Throwable cause) {
		this(-1, null, cause);
	}

	/**
	 * PSException��������
	 * 
	 * @param ID
	 *            int �쳣ID����1001��ʼ��
	 * @param message
	 *            String �쳣��ϸ��Ϣ��
	 * @param cause
	 */
	public EStockThirdException(int ID, String message, Throwable cause) {
		super((message != null ? message : "") + (ID != -1 ? "[" + ID + "]" : ""), cause);

		exceptionID = ID;
	}

	/**
	 * ȡ���쳣��ID�š�
	 * 
	 * @return int �쳣��ID�š�
	 */
	public int getExceptionID() {
		return exceptionID;
	}
}
