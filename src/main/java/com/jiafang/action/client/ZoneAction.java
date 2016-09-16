package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.model.Company;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;

@ParentPackage("basePackage")
@Namespace("/zone")
public class ZoneAction extends JSONAction {

	@Autowired
	private CategoryService categoryService;
	
	private Page page;
	
	private Integer count;

	private Company company;
	
	@Action(value = "getZones")
	public String getZones() {
		if (company == null && isPublic()){
			setData(categoryService.getZones(count));
		}else {
			setData(categoryService.getZones(count, company.getId()));
		}

		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
