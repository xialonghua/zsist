package com.jiafang.service;

import java.util.List;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Param;
import com.jiafang.model.Pic;
import com.jiafang.model.Product;
import com.jiafang.model.SubProduct;

public interface ProductService extends Constants{

	BaseResp getProductsByCategory(Page page, Integer categoryId);
	BaseResp getProductsByCategory(Page page, Integer categoryId, Integer companyId);
	
	BaseResp getProductsByCompanyId(Page page, Integer companyId);
	
	BaseResp searchProducts(Page page, String name);
	
	BaseResp searchCompanies(Page page, String name);
	
	BaseResp searchProductsByCategory(Page page, String name, Integer categoryId);
	
	BaseResp searchProductsByCompany(Page page, String name, Integer companyId);
	
	BaseResp getProductById(Integer productId);
	
	BaseResp getProductsByCompanyUserId(Page page, Integer userId);
	
	BaseResp addProduct(Product product);
	
	BaseResp modifyProduct(Product product);
	
	BaseResp deleteProduct(Product product);
	
	BaseResp addProductParam(Param param);
	
	BaseResp updateProductParam(Param param);
	
	BaseResp deleteProductParam(Param param);
	
	BaseResp addPic(Pic pic);
	
	BaseResp addPics(List<Pic> pics);
	
	BaseResp updatePic(Pic pic);
	
	BaseResp deletePic(Pic pic);
	
	BaseResp getSubProduct(Integer subId);
	
	BaseResp saveSubProduct(SubProduct sub);
	
	BaseResp updateSubProduct(SubProduct sub);
	
	BaseResp deleteSubProduct(SubProduct sub);
	
	BaseResp getProductPics(Integer productId, Integer type);
	
	BaseResp getProductParamById(Integer paramId);
	
	BaseResp getProducts(Page page);
	
	BaseResp getProdunctNumberCount(String num);
}
