package com.jiafang.action.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jiafang.action.JSONAction;
import com.jiafang.common.Constants;
import com.jiafang.model.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.service.Page;
import com.jiafang.service.ProductService;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("basePackage")
@Namespace("/product")
public class ProductAction extends JSONAction {

	@Autowired
	private ProductService productService;
	
	private Page page;
	private Integer categoryId;
	private Integer companyId;
	
	
	private Integer productId;
	private String productName;
	private Product product;
	
	private Integer paramId;
	
	private Param param;
	private Pic pic;
	private SubProduct subProduct;

	private Company company;
	
	private List<Pic> pics;
	
	private Integer picType;

	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}

	private ProductSize productSize;
	
	@Action(value = "getProducts")
	public String getAllProduct() {
		setData(productService.getProducts(page));
		return RETURN_JSON;
	}
	
	@Action(value = "getProductsByCategory")
	public String getProducts() {
		if (company == null && isPublic()){
			setData(productService.getProductsByCategory(page, categoryId, PUBLIC));
		}else {
			setData(productService.getProductsByCategory(page, categoryId, company.getId(), PRIVATE));
		}

		return RETURN_JSON;
	}
	
	@Action(value = "getProductsByCompany")
	public String getProductsByCompany() {
        if (company == null && isPublic()){
            setData(productService.getProductsByCompanyId(page, companyId, PUBLIC));
        }else {
            setData(productService.getProductsByCompanyId(page, companyId, PRIVATE));
        }
		return RETURN_JSON;
	}
	
	@Action(value = "getProductsByCompanyUserId")
	public String getProductsByCompanyUserId() {
	    Map<String, Object> session = getSession();
	    User user = (User) session.get("user");
		setData(productService.getProductsByCompanyUserId(page, user.getId(), ALL_PLAT));
		return RETURN_JSON;
	}
	
	@Action(value = "getProductById")
	public String getProductById() {
		setData(productService.getProductById(productId));
		return RETURN_JSON;
	}
	
	@Action(value = "getSubProductById")
	public String getSubProductById() {
		setData(productService.getSubProduct(subProduct.getId()));
		return RETURN_JSON;
	}
	
	@Action(value = "addProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addProduct() {
		//product.setName(StringUtil.encode(productName));
        Company company = (Company) getSession().get("user_level_1_company");
        product.setCompanyId(company.getId());
		setData(productService.addProduct(product));
		return RETURN_JSON;
	}
	
	@Action(value = "addSubProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addSubProduct() {
		//product.setName(StringUtil.encode(productName));
		setData(productService.saveSubProduct(subProduct));
		return RETURN_JSON;
	}
	
	@Action(value = "modifyProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyProduct() {
		//product.setName(StringUtil.encode(productName));
        Company company = (Company) getSession().get("user_level_1_company");
        product.setCompanyId(company.getId());
		setData(productService.modifyProduct(product));
		return RETURN_JSON;
	}
	
	@Action(value = "modifySubProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifySubProduct() {
		//product.setName(StringUtil.encode(productName));
		setData(productService.updateSubProduct(subProduct));
		return RETURN_JSON;
	}
	
	@Action(value = "deleteProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteProduct() {
		//product.setName(StringUtil.encode(productName));
		setData(productService.deleteProduct(product));
		return RETURN_JSON;
	}
	
	@Action(value = "deleteSubProduct", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteSubProduct() {
		//product.setName(StringUtil.encode(productName));
		setData(productService.deleteSubProduct(subProduct));
		return RETURN_JSON;
	}
	
	@Action(value = "addProductPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addProductPic() {
		setData(productService.addPic(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "addProductPics", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addProductPics() {
		HttpServletRequest request = ServletActionContext.getRequest();
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        Integer type = Integer.parseInt(request.getParameter("type"));
        String urls = request.getParameter("urls");
        String[] urlArr = urls.split(",");
        List<Pic> picList = new ArrayList<Pic>();
        for(int i=0;i<urlArr.length;i++){
        	Pic obj = new Pic();
        	obj.setProductId(productId);
        	obj.setType(type);
        	obj.setUrl(urlArr[i]);
        	picList.add(obj);
        }
		setData(productService.addPics(picList));
		return RETURN_JSON;
	}
	
	@Action(value = "modifyProductPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyProductPic() {
		setData(productService.updatePic(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "modifyProductPics", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyProductPics() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        String titles = request.getParameter("titles");
        String[] titleArr = titles.split(",");
       
        for(int i=0;i<idArr.length;i++){
        	Pic obj = new Pic();
        	obj.setId(Integer.valueOf(idArr[i]));
        	obj.setName(titleArr[i]);
        	productService.updatePic(obj);
        }
        BaseResp result = new BaseResp();
        result.setCode(SUCCESS);
        setData(result);
		return RETURN_JSON;
	}
	
	@Action(value = "modifySubProductNames", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifySubProductNames() {
		HttpServletRequest request = ServletActionContext.getRequest();
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        String titles = request.getParameter("titles");
        String[] titleArr = titles.split(",");
       
        for(int i=0;i<idArr.length;i++){
        	SubProduct obj = new SubProduct();
        	obj.setId(Integer.valueOf(idArr[i]));
        	obj.setName(titleArr[i]);
        	productService.updateSubProduct(obj);
        }
        BaseResp result = new BaseResp();
        result.setCode(SUCCESS);
        setData(result);
		return RETURN_JSON;
	}
	
	@Action(value = "deleteProductPic", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteProductPic() {
		setData(productService.deletePic(pic));
		return RETURN_JSON;
	}
	
	@Action(value = "getProductPics")
	public String getProductPics() {
		setData(productService.getProductPics(productId,picType));
		return RETURN_JSON;
	}

	@Action(value = "addProductParam", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addProductParam() {
		setData(productService.addProductParam(param));
		return RETURN_JSON;
	}

	@Action(value = "modifyProductParam", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyProductParam() {
		setData(productService.updateProductParam(param));
		return RETURN_JSON;
	}

	@Action(value = "deleteProductParam", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteProductParam() {
		setData(productService.deleteProductParam(param));
		return RETURN_JSON;
	}

	@Action(value = "addProductSize", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String addProductSize() {
		setData(productService.addProductSize(productSize));
		return RETURN_JSON;
	}

	@Action(value = "modifyProductSize", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String modifyProductSize() {
		setData(productService.updateProductSize(productSize));
		return RETURN_JSON;
	}

	@Action(value = "modifyProductPlatform", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String modifyProductPlatform() {
		setData(productService.updateProductPlatform(product));
		return RETURN_JSON;
	}

	@Action(value = "deleteProductSize", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String deleteProductSize() {
		setData(productService.deleteProductSize(productSize));
		return RETURN_JSON;
	}
	
	@Action(value = "getProductParamById")
	public String getProductParamById() {
		setData(productService.getProductParamById(paramId));
		return RETURN_JSON;
	}
	
	@Action(value = "checkProductNum", interceptorRefs={@InterceptorRef(COMPANY_INTERCEPTOR)})
	public String checkProductNum() {
		setData(productService.getProdunctNumberCount(product.getNum()));
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public List<Pic> getPics() {
		return pics;
	}

	public void setPics(List<Pic> pics) {
		this.pics = pics;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public SubProduct getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(SubProduct subProduct) {
		this.subProduct = subProduct;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
