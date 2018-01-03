package com.taiji.common.util.security;

/**
 * 常量类
 * @author
 *
 */
public class CommonConstants {
	
	public static int CLINENT_TIMEOUT = 30*1000;
	
	public static String pos_ip="pos.ip";
	
	public static String pos_port="pos.port";
	/**
	 * 密钥存放的路径
	 */
	public static String keyRootPath;
	/**
	 * 接口内容编码方式
	 */
	public static String CODER_NAME = "UTF-8";
	/**
	 * 登录标识
	 */
	public static String LOGIN_FLAG_F1 = "F1";//手机号
	public static String LOGIN_FLAG_F2 = "F2";//登录名
	
	/**
	 * 转账标识
	 */
	public static String TRANS_FLAG_T1="T1";//转入
	public static String TRANS_FLAG_T2="T2";//转出
	
	/**
	 * 支付渠道
	 */
	public static String PAY_CHANNEL_BAIDU="BAIDU";
	public static String PAY_CHANNEL_WEICHAT="WEICHAT";
	public static String PAY_CHANNEL_ALIPAY="ALIPAY";
	public static String PAY_CHANNEL_KALIAN="KALIAN";
	public static String PAY_CHANNEL_UNIONPAY="UNIONPAY";
	
	/**
	 * 支付方式
	 */
	public static String PAY_TYPE_QRCODE="QRCODE";
	public static String PAY_TYPE_CSWIPECARD="CSWIPECARD";
	public static String PAY_TYPE_BSWIPECARD="BSWIPECARD";
	public static String PAY_TYPE_MSWIPECARD="MSWIPECARD";
	public static String PAY_TYPE_BALANCE="BALANCE";
	
	/**
	 * 交易代码
	 */
	public static String TRANSACTION_CODE_ORDER="20010001";
	public static String TRANSACTION_CODE_ORDER_PAYMENT="20010002";
	public static String TRANSACTION_CODE_ORDER_BACK="20010003";
	public static String TRANSACTION_CODE_ORDER_QUERY="20010004";
	public static String TRANSACTION_CODE_ORDER_CANCEL="20010005";
	public static String TRANSACTION_CODE_ORDER_SEND="20010006";
	public static String TRANSACTION_CODE_BUSSI_FEE_QUERY="20010007";
	public static String TRANSACTION_CODE_BUSSI_OPEN="20010011";
	public static String TRANSACTION_CODE_ORDER_PAYMENT_NOTIFY="20010012";
	
	/**
	 * 接入渠道
	 */
	public static String JOIN_CHANNEL_CWEB="CWEB";
	public static String JOIN_CHANNEL_MWEB="MWEB";
	public static String JOIN_CHANNEL_ANDROID="ANDROID";
	public static String JOIN_CHANNEL_IPHONE="IPHONE";
	public static String JOIN_CHANNEL_IPAD="IPAD";
	public static String JOIN_CHANNEL_CPOS="CPOS";
	public static String JOIN_CHANNEL_BPOS="BPOS";
	public static String JOIN_CHANNEL_SPOS ="SPOS";
	
	/**
	 * 密码加密方式
	 */
	public static String ENCODE_TYPE_00="00";  //控件
	public static String ENCODE_TYPE_01="01";  //控件+RSA
	public static String ENCODE_TYPE_02="02";  //AES
	public static String ENCODE_TYPE_03="03";  //RSA公钥加密
	public static String ENCODE_TYPE_04="04";  //无密码
	public static String ENCODE_TYPE_05="05";  //控件+AES
	
	/**
	 * 业务类型
	 */
	public static String SWIP_CARD_TYPE_DHB="1";  //订货宝
	public static String SWIP_CARD_TYPE_MPOS="2"; //MPOS
	public static String SWIP_CARD_TYPE_SJSKQ="3";//手机刷卡器
	public static String SWIP_CARD_TYPE_POS="4";  //pos
	public static String SWIP_CARD_TYPE_KJ="5";   //快捷支付
	
	/**
	 * 商户系统错误码
	 */
	public static String USER_ERROR_SYS="9999";
	
	/**
	 * 订单系统错误码
	 */
	public static String BUSINESS_ERROR_SYS="00000000";  //系统错误
	public static String BUSINESS_SUCCESS="0000";  //成功
	public static String BUSINESS_FAIL="0001";	 //失败
	
	/**
	 * 商户系统请求类型
	 */
	public static String MERCHANT_TYPE_M040 ="M040";
	/**
	 * 商户系统请求类型
	 */
	public static String MERCHANT_TYPE_M030 ="M030";
	/**
	 * 商户系统请求类型
	 */
	public static String MERCHANT_TYPE_M031 ="M031";
	
	/**
	 * 商户系统请求类型
	 */
	public static String MERCHANT_TYPE_M033 ="M033";
	
	
	/**
	 * 易淘客商户平台
	 */
	//请求报文代码：登录请求
	public static String MESSAGE_CODE_1000= "1000";
	//响应报文代码：登录响应
	public static String RET_MESSAGE_CODE_1001= "1001";
	//请求报文代码：登录密码修改请求
	public static String MESSAGE_CODE_1002= "1002";
	//响应报文代码：登录密码修改响应
	public static String RET_MESSAGE_CODE_1003= "1003";
	//请求报文代码：支付密码修改请求
	public static String MESSAGE_CODE_1004= "1004";
	//响应报文代码：支付密码修改响应
	public static String RET_MESSAGE_CODE_1005= "1005";
	//请求报文代码：我的账户请求
	public static String MESSAGE_CODE_1006= "1006";
	//响应报文代码：我的账户响应
	public static String RET_MESSAGE_CODE_1007 = "1007";
	//请求报文代码：交易查询请求
	public static String MESSAGE_CODE_1008= "1008";
	//响应报文代码：交易查询响应
	public static String RET_MESSAGE_CODE_1009 = "1009";
	//请求报文代码：账务查询请求
	public static String MESSAGE_CODE_1010= "1010";
	//响应报文代码：账务查询响应
	public static String RET_MESSAGE_CODE_1011 = "1011";
	//请求报文代码：收单交易查询请求
	public static String MESSAGE_CODE_1012= "1012";
	//响应报文代码：收单交易查询响应
	public static String RET_MESSAGE_CODE_1013 = "1013";
	//请求报文代码：登录退出请求
	public static String MESSAGE_CODE_1014= "1014";
	//响应报文代码：登录退出响应
	public static String RET_MESSAGE_CODE_1015 = "1015";
	//请求报文代码：版本检查请求
	public static String MESSAGE_CODE_1016= "1016";
	//响应报文代码：版本检查响应
	public static String RET_MESSAGE_CODE_1017 = "1017";
	
	//请求码：理财账户信息显示
	public static String MESSAGE_CODE_1024= "1024";
	//响应吗：理财账户信息显示
	public static String MESSAGE_CODE_1025= "1025";
	//请求码：理财账户转入转出
	public static String MESSAGE_CODE_1026= "1026";
	//响应码：理财账户转入转出
	public static String MESSAGE_CODE_1027= "1027";
	//请求码：理财账户账务交易明细
	public static String MESSAGE_CODE_1028= "1028";
	//响应码：理财账户账务交易明细
	public static String MESSAGE_CODE_1029= "1029";
	
	//请求码：我的财富-个人所有理财记录详细请求
	public static String MESSAGE_CODE_1038= "1038";
	//响应吗：我的财富-个人所有理财记录详细响应
	public static String RET_MESSAGE_CODE_1039= "1039";
	//请求码：我的财富-固收理财详细请求
	public static String MESSAGE_CODE_1036= "1036";
	//响应吗：我的财富-固收理财详细响应
	public static String RET_MESSAGE_CODE_1037= "1039";
	//请求码：我的财富-我的固收财富记录请求
	public static String MESSAGE_CODE_1030= "1030";
	//响应吗：我的财富-我的固收财富记录响应
	public static String RET_MESSAGE_CODE_1031= "1031";
	//请求码：我的财富-我的活期财富记录请求
	public static String MESSAGE_CODE_1032= "1032";
	//响应吗：我的财富-我的活期财富记录响应
	public static String RET_MESSAGE_CODE_1033= "1033";
	//请求码：我的财富-到期理财赎回请求
	public static String MESSAGE_CODE_1034= "1034";
	//响应吗：我的财富-到期理财赎回响应
	public static String RET_MESSAGE_CODE_1035= "1035";
	//请求码：理财首页-固收产品列表请求
	public static String MESSAGE_CODE_1018= "1018";
	//响应吗：理财首页-固收产品列表响应
	public static String RET_MESSAGE_CODE_1019= "1019";
	//请求码：理财首页-活期产品信息请求
	public static String MESSAGE_CODE_1020= "1020";
	//响应吗：理财首页-活期产品信息响应
	public static String RET_MESSAGE_CODE_1021= "1021";
	//请求码：理财首页-固收理财产品购买请求
	public static String MESSAGE_CODE_1022= "1022";
	//响应吗：理财首页-固收理财产品购买列表响应
	public static String RET_MESSAGE_CODE_1023= "1023";
	//请求码：理财首页-活期宝转入请求
	public static String MESSAGE_CODE_1040= "1040";
	//响应吗：理财首页-活期宝转入响应
	public static String RET_MESSAGE_CODE_1041= "1041";
	
	//请求码：手机app机型设备验证请求
	public static String MESSAGE_CODE_1042= "1042";
	//响应吗：手机app机型设备验证响应
	public static String RET_MESSAGE_CODE_1043= "1043";
	//请求码：手机app功能权限控制请求
	public static String MESSAGE_CODE_1044= "1044";
	//响应吗：手机app功能权限控制响应
	public static String RET_MESSAGE_CODE_1045= "1045";
	
	//请求码：提现
	public static String MESSAGE_CODE_m10004="m10004";
	//请求码：蓝牙POS终端绑定
	public static String MESSAGE_CODE_m10005="m10005";
	//请求吗：蓝牙POS终端换绑
	public static String MESSAGE_CODE_m10006="m10006";
	//请求码：蓝牙POS刷卡消费
	public static String MESSAGE_CODE_m10007="m10007";
	//请求码：蓝牙POS刷卡撤销
	public static String MESSAGE_CODE_m10008="m10008";
	//请求吗：蓝牙POS刷卡冲正
	public static String MESSAGE_CODE_m10009="m10009";
	//请求吗：蓝牙POS刷卡充值
	public static String MESSAGE_CODE_m10010="m10010";
	//请求吗：蓝牙POS刷卡签到
	public static String MESSAGE_CODE_m10011="m10011";
	//请求吗：蓝牙POSIC卡参数下载
	public static String MESSAGE_CODE_m10012="m10012";
	//请求吗：终端绑定验证
	public static String MESSAGE_CODE_m10013="m10013";
	//请求吗：蓝牙POS电子签购单
	public static String MESSAGE_CODE_m10015="m10015";
	
	/**
	 * 系统错误-文件路径错误
	 */
	public static String SYS_PATH_ERROR = "90";
	
	/**
	 * 返回码 : 成功
	 */
	public static String SUCCESS = "00";
	/**
	 * 返回码 : 失败
	 */
	public static String FAILURE = "01";
	/**
	 * 返回码(接口错误) : 签名错误
	 */
	public static String SIGN_ERROR = "02";
	/**
	 * 返回码(接口错误) : 参数错误
	 */
	public static String ARG_ERROR = "03";
	/**
	 * 返回码(接口错误) : 请求报文类型不存在
	 */
	public static String REQCODE_NONE = "04";
	/**
	 * 返回码(接口错误) : 用户不存在
	 */
	public static String USER_NONE = "05";
	/**
	 * 返回码：登录密码错误
	 */
	public static String LOGINPWD_ERROR = "06";
	/**
	 * 返回码：支付密码错误
	 */
	public static String PAYPASSWORD_ERROR = "07";
	/**
	 * 返回码(接口错误) : 登录标识错误
	 */
	public static String LOGINFLAG_ERROR = "08";
	/**
	 * 返回码(接口错误) : 登录账号错误
	 */
	public static String LOGINNO_ERROR = "09";
	/**
	 * 返回码(接口错误) : 系统异常
	 */
	public static String SYSTEM_ERROR = "10";
	/**
	 * 返回码(接口错误) : 系统繁忙
	 */
	public static String SYSTEM_BUSY = "11";
	/**
	 * 返回码(接口错误) : 系统维护
	 */
	public static String SYSTEM_MAINT = "12";
	/**
	 * 返回码:token失效
	 */
	public static String LOGIN_TOKEN_TIMEOUT = "13";
	/**
	 * 返回码:旧登录密码错误
	 */
	public static String LOGINOLDPWD_ERROR = "14";
	/**
	 * 返回码:旧支付密码错误
	 */
	public static String PAYOLDPASSWORD_ERROR = "15";
	/**
	 * 返回码:用户类型不被允许
	 */
	public static String LOGIN_USERTYPE_ERROR = "16";
	/**
	 * 返回码:账务信息不存在
	 */
	public static String ACCOUNT_INFO_NONE = "17";
	/**
	 * 返回码:交易信息查询异常
	 */
	public static String TRANS_INFO_ERROR= "18";
	/**
	 * 返回码:账务信息查询异常
	 */
	public static String TRANSACCOUNT_INFO_ERROR= "19";
	/**
	 * 返回码:登录手机号有重复
	 */
	public static String LOGINO_REPEAT_ERROR= "20";
	/**
	 * 返回码：金额不正确
	 */
	public static String AMOUNT_INFO_ERROR="21";
	/**
	 * 返回码：个人理财账户不存在
	 */
	public static String ACCOUNT_PERSON_LC_ERROR="22";
	/**
	 * 返回码：主账户余额不足
	 */
	public static String ACCOUNT_BALANCE_ERROR="23";
	/**
	 * 返回码：个人理财账户余额不足
	 */
	public static String ACCOUNT_PERSON_LC_BALANCE_ERROR="24";
	
	/**
	 * 返回码：账户信息不存在
	 */
	public static String ACCOUNT_BASE_81_ERROR="25";
	
	/**
	 * 返回码：转账标识错误
	 */
	public static String ACCOUNT_TRANS_FLAG_ERROR="26";
	
	/**
	 * 返回码：数据已加载完毕
	 */
	public static String ACCOUNT_TRANS_LOAD_OVER="27";
	/**
	 * 返回码：个人固收账户不存在
	 */
	public static String ACCOUNT_PERSON_FIX_ERROR="28";
	/**
	 * 返回码：个人活期账户不存在
	 */
	public static String ACCOUNT_PERSON_CURRENT_ERROR="29";
	/**
	 * 返回码：个人活期账户余额不足
	 */
	public static String ACCOUNT_PERSON_CURRENT_BALANCE_ERROR="30";
	/**
	 * 返回码：个人理财账户状态不合法
	 */
	public static String ACCOUNT_STATUS_ERROR="31";
	
	/**
	 * 返回码：消息流水号错误
	 */
	public static String ACCOUNT_SEQ_NUM_ERROR="32";
}
