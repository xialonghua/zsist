package com.jiafang.common;

public interface Constants {
	String LOGIN_INTERCEPTOR = "loginInteceptor";
	String RETURN_JSON = "json";
	int SUCCESS = 200;
	
	int INVALIDAT_REQUEST = 99;//无效请求
	int USER_NOT_FOUND = 201;//用户不存在
	int WRONG_PASSWORD = 202;//密码错误
	int USER_EXSITS = 203;//用户已存在
	int INVALIDAT_CODE = 204;//无效验证码
	int CODE_EXPIRED = 205;//验证码过期
	int SEND_MSG_FAILED = 206;//验证码过期
	int USER_NOT_LOGIN = 207;//用户未登录
	
	int PRODUCT_NUM_EXSITS = 1001;//产品货号已经存在
	
	int COMPANY_NOT_FOUND = 1002;
	int PRODUCT_NOT_FOUND = 1003;
	int SUB_PRODUCT_NOT_FOUND = 1004;


	int ADDRESS_NOT_FOUND = 2001;//地址不存在
	
	public interface Platform {
		int IOS = 0;
		int ANDROID = 1;
	}
}
