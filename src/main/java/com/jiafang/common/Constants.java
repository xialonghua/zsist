package com.jiafang.common;

public interface Constants {

	//showPlatform
	int PUBLIC = 0;
	int PRIVATE = 1;
	int ALL_PLAT = 2;

    //支付方式
	int PAY_ALI = 1;
	int PAY_WEIXIN = 0;

	String LOGIN_INTERCEPTOR = "loginInteceptor";
	String ADMIN_INTERCEPTOR = "adminLoginInteceptor";
	String COMPANY_INTERCEPTOR = "companyLoginInteceptor";
	String RETURN_JSON = "json";
    int SUCCESS = 200;
    int SYSTEM_ERROR = 199;
	
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

    int ORDER_NOT_FOUND = 3001;//订单不存在
//    int ORDER_CANT_CANCEL = 3002;//订单已发货不能取消
    int ORDER_ALREADY_CANCEL = 3003;//订单已经取消
    int ORDER_ALREADY_CLOSE = 3004;//订单已经关闭
    int ORDER_ALREADY_FINISHED = 3005;//订单已经完成
    int ORDER_ALREADY_DISPATCH = 3006;//订单已经发货
    int ORDER_ALREADY_PAY = 3007;//订单已经支付过
    int ORDER_ALREADY_NOT_PAY = 3008;//订单未支付

    int PAY_UNKOWN_TYPE = 4001;//无效的付款方式
    int PAY_DISCONNECT = 4002;//联系三方支付失败


    public interface Platform {
		int IOS = 0;
		int ANDROID = 1;
	}
}
