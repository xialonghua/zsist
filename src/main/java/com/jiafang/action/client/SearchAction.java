package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.Page;
import com.jiafang.service.ProductService;

@ParentPackage("basePackage")
@Namespace("/search")
public class SearchAction extends JSONAction {

	@Autowired
	private ProductService productService;
	
	private Page page;
	private Integer categoryId;
	private Integer companyId;
	private String name;
	
	@Action(value = "seachProducts")
	public String seachProducts() {
		setData(productService.searchProducts(page, name));
		return RETURN_JSON;
	}
	
	@Action(value = "seachProductsByCategory")
	public String seachProductsByCategory() {
		setData(productService.searchProductsByCategory(page, name, categoryId));
		return RETURN_JSON;
	}
	
	@Action(value = "seachProductsByCompany")
	public String seachProductsByCompany() {
		setData(productService.searchProductsByCompany(page, name, companyId));
		return RETURN_JSON;
	}
	
	@Action(value = "seachCompanies")
	public String seachCompanies() {
		setData(productService.searchCompanies(page, name));
		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	
}
