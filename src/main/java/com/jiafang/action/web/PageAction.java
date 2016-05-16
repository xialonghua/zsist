package com.jiafang.action.web;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Version;
import com.jiafang.service.VersionService;
import com.jiafang.util.Config;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@ParentPackage("basePackage")
@Namespace("/page")
public class PageAction implements Constants{
	
	@Autowired
	private VersionService versionService;

	
	@Action(value = "login", results = { @Result(name = "SUCCESS", location = "/login.jsp") })
	public String login() {
		 return "SUCCESS";
	}
	
	@Action(value = "register", results = { @Result(name = "SUCCESS", location = "/register.jsp") })
	public String regist() {
		 return "SUCCESS";
	}
	
	@Action(value = "resetPwd", results = { @Result(name = "SUCCESS", location = "/resetPwd.jsp") })
	public String resetPwd() {
		 return "SUCCESS";
	}
	
	@Action(value = "categorylist", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/category/list.jsp") })
	public String categoryList() {
		 return "SUCCESS";
	}
	
	@Action(value = "categoryadd", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/category/add.jsp") })
	public String categoryAdd() {
		 return "SUCCESS";
	}
	
	
	@Action(value = "categorymodify", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/category/modify.jsp") })
	public String categoryModify() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
		 return "SUCCESS";
	}
	
	
	@Action(value = "adlist", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/ad/list.jsp") })
	public String adList() {
		 return "SUCCESS";
	}
	
	@Action(value = "adadd", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/ad/add.jsp") })
	public String adAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
		 return "SUCCESS";
	}
	@Action(value = "adSetWeight", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/ad/setWeight.jsp") })
	public String adSetWeight() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String shipId = request.getParameter("shipId");
        request.setAttribute("shipId", shipId);
		 return "SUCCESS";
	}
	
	@Action(value = "zonelist", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/zone/list.jsp") })
	public String zoneList() {
		 return "SUCCESS";
	}
	
	@Action(value = "zoneadd", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/zone/add.jsp") })
	public String zoneAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
		 return "SUCCESS";
	}
	
	@Action(value = "zoneSetWeight", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/zone/setWeight.jsp") })
	public String zoneSetWeight() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String shipId = request.getParameter("shipId");
        request.setAttribute("shipId", shipId);
		 return "SUCCESS";
	}
	
	@Action(value = "companylist", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/list.jsp") })
	public String companyList() {
		 return "SUCCESS";
	}
	
	@Action(value = "companyinfo", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/info.jsp") })
	public String companyInfo() {
		 return "SUCCESS";
	}
	
	@Action(value = "companyregister", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/register.jsp") })
	public String companyRegister() {
		 return "SUCCESS";
	}
	
	@Action(value = "companymodify", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/modify.jsp") })
	public String companyModify() {
		 return "SUCCESS";
	}
	
	@Action(value = "productlist", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/list.jsp") })
	public String productList() {
		 return "SUCCESS";
	}
	
	@Action(value = "productlist_admin", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/list_admin.jsp") })
	public String productList_admin() {
		 return "SUCCESS";
	}
	
	@Action(value = "productinfo", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/param.jsp") })
	public String productInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String productId = request.getParameter("productId");
        request.setAttribute("productId", productId);
		 return "SUCCESS";
	}
	
	@Action(value = "productmodify", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/modify.jsp") })
	public String productModify() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String productId = request.getParameter("productId");
        request.setAttribute("productId", productId);
		return "SUCCESS";
	}
	
	@Action(value = "productadd", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/add.jsp") })
	public String productAdd() {
		
		 return "SUCCESS";
	}
	
	@Action(value = "productaddparam", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/addParam.jsp") })
	public String productAddParam() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String productId = request.getParameter("productId");
        request.setAttribute("productId", productId);
		return "SUCCESS";
	}
	
	@Action(value = "productmodifyparam", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/modifyParam.jsp") })
	public String productMofidyParam() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String paramId = request.getParameter("paramId");
        request.setAttribute("paramId", paramId);
		return "SUCCESS";
	}
	
	@Action(value = "productconfig", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/categoryConfig.jsp") })
	public String productCategoryConfig() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String productId = request.getParameter("productId");
        request.setAttribute("productId", productId);
		return "SUCCESS";
	}
	
	@Action(value = "logout", results = { @Result(name = "SUCCESS", location = "/login.jsp") })
	public String logout() {
		ActionContext actionContext = ActionContext.getContext();
	    Map<String, Object> session = actionContext.getSession();
        session.clear();
		return "SUCCESS";
	}
	
	@Action(value = "modifypwd", results = { @Result(name = "SUCCESS", location = "/modifyPwd.jsp") })
	public String modifyPwd() {
		
		return "SUCCESS";
	}
	
	
	@Action(value = "video", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/video/video.jsp") })
	public String video() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String videoURL = request.getParameter("videoURL");
        request.setAttribute("videoURL", videoURL);
		return "SUCCESS";
	}
	
	@Action(value = "download", results = { @Result(name = "SUCCESS", location = "/down2.jsp") })
	public String download() {
		 
		BaseResp resp = versionService.checkUpdate("1", Platform.ANDROID);
		
		String androidUrl = ((Version)resp.getData()).getUrl();
		
		resp = versionService.checkUpdate("1", Platform.IOS);
		
		String iosUrl = ((Version)resp.getData()).getUrl();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("iosUrl", iosUrl);
		request.setAttribute("androidUrl", androidUrl);
		String appName = Config.getAppName();
		request.setAttribute("appName", appName);
		request.setAttribute("shortName", Config.getShortName());
		
		return "SUCCESS";
	}
	
	@Action(value = "pkgupload", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/pkg/pkgUpload.jsp") })
	public String pkgUpload() {
		return "SUCCESS";
	}
	
	@Action(value = "storePics", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/StorePics.jsp") })
	public String storePics() {
		return "SUCCESS";
	}
	
	@Action(value = "companyPics", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/company/CompanyPics.jsp") })
	public String companyPics() {
		return "SUCCESS";
	}
	
	@Action(value = "subProduct", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/subProduct.jsp") })
	public String subProduct() {
		HttpServletRequest request = ServletActionContext.getRequest();
        Integer productId = Integer.valueOf(request.getParameter("productId"));
        request.setAttribute("productId", productId);
		return "SUCCESS";
	}
	
	
	@Action(value = "productPics", results = { @Result(name = "SUCCESS", location = "/WEB-INF/pages/product/productPics.jsp") })
	public String productPics() {
		HttpServletRequest request = ServletActionContext.getRequest();
        Integer productId = Integer.valueOf(request.getParameter("productId"));
        request.setAttribute("productId", productId);
		return "SUCCESS";
	}
}
