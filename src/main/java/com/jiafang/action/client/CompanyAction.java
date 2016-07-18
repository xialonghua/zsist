package com.jiafang.action.client;

import java.util.Map;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.Company;
import com.jiafang.model.Pic;
import com.jiafang.model.User;
import com.jiafang.service.CompanyService;
import com.jiafang.service.Page;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("basePackage")
@Namespace("/company")
public class CompanyAction extends JSONAction {

	@Autowired
	private CompanyService companyService;
	
	private Page page;
	private Integer companyId;
	
	private Company company;
	private Pic pic;

	@Action(value = "getCompanies")
	public String getCompanies() {
		setData(companyService.getCompaniesByPage(page));
		return RETURN_JSON;
	}
	
	@Action(value = "getCompany")
	public String getCompanyById() {
		setData(companyService.getCompanyById(companyId));
		return RETURN_JSON;
	}

	@Action(value = "getCompanyByUserId")
	public String getCompanyByUserId() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		User user = (User) session.get("user");
		setData(companyService.getCompanyByUserId(user.getId()));
		return RETURN_JSON;
	}

	@Action(value = "getSelfCompany")
	public String getSelfCompany() {
		return getCompanyByUserId();
	}
	
	@Action(value = "registerCompany")
	public String registerCompany() {
		setData(companyService.updateCompany(company));
		return RETURN_JSON;
	}

	@Action(value = "modifyCompany", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyCompany() {
		setData(companyService.updateCompany(company));
		return RETURN_JSON;
	}

	@Action(value = "modifyCompanyWechatAlipayAccount", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyCompanyWechatAlipayAccount() {
		setData(companyService.updateCompanyWechatAlipayAccount(company));
		return RETURN_JSON;
	}
	
	@Action(value = "addCompanyPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addCompanyPic() {
		setData(companyService.addPic(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "modifyCompanyPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyCompanyPic() {
		setData(companyService.updatePic(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "deleteCompanyPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteCompanyPic() {
		setData(companyService.deletePic(pic));
		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}
	
}
