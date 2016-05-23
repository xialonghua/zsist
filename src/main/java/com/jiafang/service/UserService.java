package com.jiafang.service;

import java.util.Map;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Address;
import com.jiafang.model.Company;
import com.jiafang.model.User;
import com.jiafang.model.VerifyCodeType;

public interface UserService extends Constants{

	BaseResp login(String account, String password, Map<String, Object> session);
	
	BaseResp getUserInfo(Integer userId);
	
	BaseResp modifyUserInfo(User user);

	BaseResp registCompanyUserByWeb(User user, Company company, int verifyCode, Map<String, Object> session);
	
	BaseResp registCompanyUser(User user, Company company);
	
	BaseResp registCommonUser(User user, int verifyCode, Map<String, Object> session);

	BaseResp getVerifyCodeByType(String tel, Integer verifyCodeType, Map<String, Object> session);
	
	BaseResp resetPassword(String tel, int verifyCode, String password, Map<String, Object> session);

	BaseResp modifyPassword(String tel, String password, String oldPwd, Map<String, Object> session);

	BaseResp addAddress(Address address);
	
	BaseResp delAddress(Address address);
	
	BaseResp setDefaultAddress(Address address);
	
	BaseResp modifyAddress(Address address);
	
	BaseResp getAddresses(Integer userId);
	
	BaseResp delUser(String tel);

	BaseResp uploadLocation(User user);

	BaseResp getProvinces();
}
