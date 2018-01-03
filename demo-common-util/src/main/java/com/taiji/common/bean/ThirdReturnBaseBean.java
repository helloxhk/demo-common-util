package com.taiji.common.bean;

/**
 * 第三方返回信息实体对象基础
 * @author zhouy
 *
 */
public class ThirdReturnBaseBean extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6595561798396867762L;
	
	/**自定义返回相关*/
	private String msgType;
	private String msgInfo;
	private String stautsCode;
	/**
	 * 第三方订单号
	 */
	private String thirdOrderId;
	/**
	 * 第三方流水号
	 */
	private String thirdSeq;
	/**
	 * 第三方返回码
	 */
	private String thirdResultCode;
	/**
	 * 第三方返回信息描述
	 */
	private String thirdResultMsg;
	
	/**
	 * 成功
	 */
	public static String RESULT_CODER_TYPE_SUCCESS = "00";
	/**
	 * 处理中
	 */
	public static String RESULT_CODER_TYPE_HOUDING = "01";
	/**
	 * 失败
	 */
	public static String RESULT_CODER_TYPE_FAILED = "10";
	/**
	 * 通讯异常
	 */
	public static String RESULT_CODER_TYPE_MSGERROR = "20";
	/**
	 * 其他异常
	 */
	public static String RESULT_CODER_TYPE_ERROR = "30";
	/**
	 * 服务器响应码错误
	 */
	public static String RESULT_CODER_TYPE_STATUSERROR = "40";
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	public String getStautsCode() {
		return stautsCode;
	}
	public void setStautsCode(String stautsCode) {
		this.stautsCode = stautsCode;
	}
	public String getThirdOrderId() {
		return thirdOrderId;
	}
	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}
	public String getThirdSeq() {
		return thirdSeq;
	}
	public void setThirdSeq(String thirdSeq) {
		this.thirdSeq = thirdSeq;
	}
	public String getThirdResultCode() {
		return thirdResultCode;
	}
	public void setThirdResultCode(String thirdResultCode) {
		this.thirdResultCode = thirdResultCode;
	}
	public String getThirdResultMsg() {
		return thirdResultMsg;
	}
	public void setThirdResultMsg(String thirdResultMsg) {
		this.thirdResultMsg = thirdResultMsg;
	}
}