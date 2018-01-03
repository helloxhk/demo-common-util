package com.taiji.common.interfaces;

import com.taiji.common.bean.BaseObject;

/**
 * web平台日志记录接口
 * 
 * @author waking
 *
 */
public interface IWebActionLog {

	/**
	 * 记录web操作日志
	 * 
	 * @param sysCode
	 * @param operCode
	 * @return
	 */
	public boolean checkingWebActionLog(ActionLog actionLog);

	public class ActionLog extends BaseObject {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5151691576586130620L;
		
		/**
		 * 操作成功
		 */
		public final static String ACTIONRESULT_SUCCESS = "0";
		/**
		 * 操作失败
		 */
		public final static String ACTIONRESULT_FAILED = "1";
		
		private String userName;
		private String actionType;
		private String actionCode;
		private String actionName;
		private String actionDateTime;
		private String actionContext;
		private String actionResult;
		private String sysCode;
		private String operatorCode;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getActionType() {
			return actionType;
		}

		public void setActionType(String actionType) {
			this.actionType = actionType;
		}

		public String getActionCode() {
			return actionCode;
		}

		public void setActionCode(String actionCode) {
			this.actionCode = actionCode;
		}

		public String getActionName() {
			return actionName;
		}

		public void setActionName(String actionName) {
			this.actionName = actionName;
		}

		public String getActionDateTime() {
			return actionDateTime;
		}

		public void setActionDateTime(String actionDateTime) {
			this.actionDateTime = actionDateTime;
		}

		public String getActionContext() {
			return actionContext;
		}

		public void setActionContext(String actionContext) {
			this.actionContext = actionContext;
		}

		public String getActionResult() {
			return actionResult;
		}

		public void setActionResult(String actionResult) {
			this.actionResult = actionResult;
		}

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

		public String getOperatorCode() {
			return operatorCode;
		}

		public void setOperatorCode(String operatorCode) {
			this.operatorCode = operatorCode;
		}
	}
}
