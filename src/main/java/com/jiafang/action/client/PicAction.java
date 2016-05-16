package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.Pic;
import com.jiafang.service.PicService;

@ParentPackage("basePackage")
@Namespace("/pic")
public class PicAction  extends JSONAction {
	
	@Autowired
	private PicService picService;
	
	private Pic pic;
	
	@Action(value = "getCompanyPics")
	public String getCompanyPics() {
		setData(picService.getCompanyPics(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "getProductPics")
	public String getProductPics() {
		setData(picService.getProductPics(pic));
		return RETURN_JSON;
	}
	
	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}
}
