package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.Company;
import com.jiafang.model.User;
import com.jiafang.service.CompanyService;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;

@ParentPackage("basePackage")
@Namespace("/tmp")
public class TempAction extends JSONAction {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	private Page page;
	
	private User user;
	private Company company;
	
	@Action(value = "addCompany")
	public String addCompany() {
		setData(userService.registCompanyUser(user, company));
		return RETURN_JSON;
	}
	
	@Action(value = "getCompany")
	public String getCompanys() {
		setData(companyService.getCompaniesByPage(new Page(0, 99999)));
		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
