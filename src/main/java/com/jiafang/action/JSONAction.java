package com.jiafang.action;

import com.jiafang.model.User;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;

import java.util.Map;

@ParentPackage("basePackage")
@Namespace("/")
@Results({ @Result(name = "json", type = "json", params = { "root", "data" }) })
public class JSONAction implements Constants{
	private Object data;
	private User loginUser;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void setData(BaseResp data) {
		this.data = data;
	}

	protected Map<String, Object> getSession(){
		ActionContext actionContext = ActionContext.getContext();
		return actionContext.getSession();
	}

	public boolean isPublic(){
		String head = ServletActionContext.getRequest().getHeader("CALL_FROM");
		if (head == null || head.equals("PUBLIC")){
			return true;
		}
		return false;
	}

	public Integer getHeadCompanyId(){
		return 0;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
