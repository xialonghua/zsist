package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Company;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Company company;

	@Action(value = "getAds")
	public String getCategories() {
		if(type == 0){
			if (company == null && isPublic()){
				setData(categoryService.getAdsProduct(PUBLIC));
			}else {
				setData(categoryService.getAdsProduct(company.getId(), PRIVATE));
			}

		}
		return RETURN_JSON;
	}

	@Action(value = "getSplashAd")
	public String getSplashAd() {
		BaseResp resp = new BaseResp();
		List<Map<String, Object>> dd = new ArrayList<>();
		Map<String, Object> object = new HashMap();
		object.put("pic", "抗日家纺活动2.jpg");
		object.put("type", "0");
		object.put("companyId", "234");
		dd.add(object);
		resp.setCode(Constants.SUCCESS);
		resp.setData(dd);
		setData(resp);
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


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
