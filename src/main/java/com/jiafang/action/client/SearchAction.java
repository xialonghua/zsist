package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.model.Company;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.Page;
import com.jiafang.service.ProductService;

import java.util.concurrent.atomic.AtomicInteger;

@ParentPackage("basePackage")
@Namespace("/search")
public class SearchAction extends JSONAction {

	@Autowired
	private ProductService productService;
	
	private Page page;
	private Integer categoryId;
	private Integer companyId;
	private String name;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	private Company company;
	
	@Action(value = "seachProducts")
	public String seachProducts() {
		if (isPublic()){
			setData(productService.searchProducts(page, name, PUBLIC));
		}else{
			setData(productService.searchProducts(page, name, PRIVATE));
		}

		return RETURN_JSON;
	}

	@Action(value = "seachProductsByCategory")
	public String seachProductsByCategory() {
		if (isPublic()){
			setData(productService.searchProductsByCategory(page, name, categoryId, PUBLIC));
		}else{
			setData(productService.searchProductsByCategory(page, name, categoryId, company.getId(), PRIVATE));
		}

		return RETURN_JSON;
	}

	@Action(value = "seachProductsByCompany")
	public String seachProductsByCompany() {
		if (isPublic()){
			setData(productService.searchProductsByCompany(page, name, companyId, PUBLIC));
		}else{
			setData(productService.searchProductsByCompany(page, name, companyId, PRIVATE));
		}
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


	public static void main(String[] a){
        final AtomicInteger ii = new AtomicInteger(0);
		for (int i = 122;i < 337;i++){
            final int iii = i;
			new Thread(new Runnable() {
				@Override
				public void run() {

					String url = "http://media.livevip.com.cn/live-share/c7250d7fb77000017eb4c0f01e54155d_" + iii + ".jpg";
					ApiCall.get(url, null);

                    ii.incrementAndGet();
                    System.out.println("==" + ii.get());
				}
			}).start();

		}


	}
	
}
