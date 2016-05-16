package com.jiafang.action.resp;

import com.jiafang.common.Constants;

public class BaseResp implements Constants{

	private Integer code;
	private Object data;
	private String description;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
		description = covertCode(code);
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	private String covertCode(int code){
		switch (code) {
		case SUCCESS:
			return "成功"; 
		case USER_NOT_FOUND:
			return "用户不存在";
		case WRONG_PASSWORD:
			return "密码错误";
		case USER_EXSITS:
			return "用户已存在";
		case INVALIDAT_REQUEST:
			return "非法请求";
		case PRODUCT_NUM_EXSITS:
			return "产品货号已经存在";
		case USER_NOT_LOGIN:
			return "用户未登录";
		default:
			break;
		}
		return "";
	}
}
