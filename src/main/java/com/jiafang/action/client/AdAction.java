package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;

@ParentPackage("basePackage")
@Namespace("/ad")
public class AdAction extends JSONAction {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	
	private Page page;
	private Integer type;
	
	private String tel;
	
	@Action(value = "getAds")
	public String getCategories() {
		if(type == 0){
			setData(categoryService.getAdsProduct());
		}else if(type == 1){
			setData(categoryService.getAdsCompany());
		}
		
		return RETURN_JSON;
	}
	
	@Action(value = "delUser")
	public String delUser() {
		setData(userService.delUser(tel));
		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
