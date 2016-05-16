package com.jiafang.action.client;

import java.util.Map;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.model.Address;
import com.jiafang.model.Company;
import com.jiafang.model.User;
import com.jiafang.model.UserLevel;
import com.jiafang.model.VerifyCodeType;
import com.jiafang.service.CompanyService;
import com.jiafang.service.UserService;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("basePackage")
@Namespace("/user")
public class UserAction extends JSONAction {

	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	
	private String username;
	private String password;
    private Integer userType;
	
	private String tel;
	private Integer verifyCodeType;
	private Integer verifyCode;
	
	private String oldPwd;
	
	private Integer userId;
	
	private User user;
	private Address address = new Address();
	
	@Action(value = "login")
	public String login() {
	    Map<String, Object> session = getSession();
	    BaseResp resp = userService.login(username, password, session);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "getVerifyCode")
	public String getVerifyCodeAction() {
	    Map<String, Object> session = getSession();
	    BaseResp resp = userService.getVerifyCodeByType(tel, verifyCodeType, session);

	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "regist")
	public String regist() {
	    Map<String, Object> session = getSession();
	    
	    User user = new User();
	    user.setLevel(UserLevel.COMMON.ordinal());
	    user.setPassword(password);
	    user.setUsername(null);
	    user.setTel(username);
	    
	    BaseResp resp = userService.registCommonUser(user, verifyCode, session);
	    setData(resp);
		
	    return RETURN_JSON;
	}
	
	
	@Action(value = "resetPwd")
	public String resetPwd() {
	    Map<String, Object> session = getSession();
	    
	    BaseResp resp = userService.resetPassword(tel, verifyCode, password, session);
	    setData(resp);
		
	    return RETURN_JSON;
	}

	@Action(value = "registCompanyUser")
	public String registCompanyUser() {
	    Map<String, Object> session = getSession();
	    
	    User user = new User();
	    user.setLevel(UserLevel.COMPANY.ordinal());
	    user.setPassword(password);
	    user.setUsername(username);
	    user.setTel(tel);
	    
	    Company company = new Company();
	    BaseResp resp = userService.registCompanyUserByWeb(user,company, verifyCode, session);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "modifyPwd", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String modifyPwd() {
	    Map<String, Object> session = getSession();
	   
	    BaseResp resp = userService.modifyPassword(tel,password,oldPwd,session);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "getUserInfoById")
	public String getUserInfo() {
	    BaseResp resp = userService.getUserInfo(userId);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "modifyUserInfo", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String modifyUserInfo() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
	    User newUser = getUser();
	    
	    newUser.setId(user.getId());
	    newUser.setLevel(user.getLevel());
	    newUser.setPassword(user.getPassword());
	    newUser.setTel(user.getTel());
	    newUser.setUsername(user.getUsername());
	    
	    BaseResp resp = userService.modifyUserInfo(newUser);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "addAddress", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String addAddress() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
	    address.setUserId(user.getId());
	    BaseResp resp = userService.addAddress(address);
	    setData(resp);
	    return RETURN_JSON;
	}
	@Action(value = "modifyAddress", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String modifyAddress() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
	    address.setUserId(user.getId());
	    BaseResp resp = userService.modifyAddress(address);
	    setData(resp);
	    return RETURN_JSON;
	}
	@Action(value = "delAddress", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String delAddress() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");

	    address.setUserId(user.getId());
	    BaseResp resp = userService.delAddress(address);
	    setData(resp);
	    return RETURN_JSON;
	}
	
	@Action(value = "setDefaultAddress", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String setDefaultAddress() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");

	    address.setUserId(user.getId());
	    BaseResp resp = userService.setDefaultAddress(address);
	    setData(resp);
	    return RETURN_JSON;
	}

	@Action(value = "getAddresses", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String getAddresses() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");

		BaseResp resp = userService.getAddresses(user.getId());
		setData(resp);
		return RETURN_JSON;
	}

	@Action(value = "uploadLocation", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String uploadLocation(){
		User loginUser = getLoginUser();
		user.setId(loginUser.getId());
		BaseResp resp = userService.uploadLocation(user);
		setData(resp);
		return RETURN_JSON;
//		SELECT id, ( 3959 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(-122) ) + sin( radians(37) ) * sin( radians( lat ) ) ) ) AS distance FROM markers HAVING distance < 25 ORDER BY distance LIMIT 0 , 20;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVerifyCodeType() {
		return verifyCodeType;
	}

	public void setVerifyCodeType(Integer verifyCodeType) {
		this.verifyCodeType = verifyCodeType;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setVerifyCode(Integer verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getVerifyCode() {
		return verifyCode;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
